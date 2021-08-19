package main.gui.functions;

import main.boot.TerminalMain;
import main.fields.InputField;
import main.gui.setting.Account;
import main.util.Out;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RemoveDialog extends JDialog {
    public JLabel astips=new JLabel("As:");
    public JComboBox<String> as=new JComboBox<>();
    public InputField name=new InputField("Name:",250,25,60);
    public JButton remove=new JButton("Remove");
    public RemoveDialog(){
        super(TerminalMain.window);
        this.setLayout(null);
        this.setTitle("Remove a account");
        this.setResizable(false);
        this.setBounds(380,380,300,170);


        astips.setBounds(10,10,60,25);
        this.add(astips);

        as.setBounds(70,10,190,25);
        this.add(as);

        name.setLocation(10,as.getY()+as.getHeight()+10);
        this.add(name);

        remove.setBounds(10,name.getY()+name.getHeight()+15,80,30);
        remove.addActionListener(e->{
            if (name.getValue().equals("")){
                JOptionPane.showMessageDialog(RemoveDialog.this,"Please input user's name.");
                return;
            }

            TerminalMain.window.setEnabled(true);
            dispose();

            new Thread(()->{
                for (Account.Entry entry:TerminalMain.settings.accountPanel.entries){

                    if (entry.name.equals(as.getSelectedItem())){
                        if (entry.session.isAvailable()){
                            try {
                                entry.session.removeAccount(name.getValue(),true);
                                synchronized (TerminalMain.window.tips) {
                                    TerminalMain.window.tips.setText(entry.name+":Remove account successfully");
                                }
                                Out.sayWithTimeLn(entry.name+":Remove account successfully");
                            } catch (Exception exception) {
                                synchronized (TerminalMain.window.tips) {
                                    TerminalMain.window.tips.setText(entry.name+":Failed to remove account.");
                                }
                                Out.sayWithTimeLn(TerminalMain.getErrorInfo(exception));
                            }
                        }else {
                            synchronized (TerminalMain.window.tips) {
                                TerminalMain.window.tips.setText(entry.name+":Failed to remove account:Session unavailable.");
                            }
                            Out.sayWithTimeLn(entry.name+":Failed to remove account:Session unavailable.");
                        }
                    }


                }
            }).start();
        });
        this.add(remove);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                TerminalMain.window.setEnabled(true);
                dispose();
            }
        });
        this.setVisible(false);
    }

    public void reloadAccount(){
        as.removeAllItems();
        if (TerminalMain.settings.accountPanel.entries.size()!=0){
            for (Account.Entry entry:TerminalMain.settings.accountPanel.entries){
                if (entry.session!=null&&entry.session.can("account")&&!entry.deleted)
                    as.addItem(entry.name);
            }
            if (as.getItemCount()!=0){
                remove.setEnabled(true);
            }else {
                as.addItem("No account has this privilege.");
                remove.setEnabled(false);
            }
        }else {
            as.addItem("No account,please login in setting panel.");
            remove.setEnabled(false);
        }
    }
}

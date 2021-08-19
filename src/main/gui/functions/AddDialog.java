package main.gui.functions;

import main.boot.TerminalMain;
import main.fields.InputField;
import main.gui.setting.Account;
import main.util.Out;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddDialog extends JDialog {
    public JLabel astips=new JLabel("As:");
    public JComboBox<String> as=new JComboBox<>();
    public InputField name=new InputField("Name:",250,25,60);
    public InputField pass=new InputField("Pass:",250,25,60);
    public JLabel privTips=new JLabel("Privilege:");
    public JCheckBox accPriv=new JCheckBox("account"),pushPriv=new JCheckBox("push");
    public JButton add=new JButton("Add");
    public AddDialog(){
        super(TerminalMain.window);
        this.setLayout(null);
        this.setTitle("Add a account");
        this.setResizable(false);
        this.setBounds(380,380,300,270);


        astips.setBounds(10,10,60,25);
        this.add(astips);

        as.setBounds(70,10,190,25);
        this.add(as);

        name.setLocation(10,as.getY()+as.getHeight()+10);
        this.add(name);

        pass.setLocation(10,name.getY()+name.getHeight()+10);
        this.add(pass);

        privTips.setBounds(10,pass.getY()+pass.getHeight()+10,60,25);
        this.add(privTips);

        accPriv.setBounds(70,privTips.getY(),100,25);
        this.add(accPriv);

        pushPriv.setBounds(70,accPriv.getY()+accPriv.getHeight()+5,100,25);
        this.add(pushPriv);

        add.setBounds(10,pushPriv.getY()+pushPriv.getHeight()+15,80,30);
        add.addActionListener(e->{
            if (name.getValue().equals("")){
                JOptionPane.showMessageDialog(AddDialog.this,"Please input user's name.");
                return;
            }

            TerminalMain.window.setEnabled(true);
            dispose();

            new Thread(()->{
                for (Account.Entry entry:TerminalMain.settings.accountPanel.entries){

                    if (entry.name.equals(as.getSelectedItem())){
                        if (entry.session.isAvailable()){
                            try {
                                StringBuilder privsb=new StringBuilder();
                                privsb.append(accPriv.isSelected()?"account,":"");
                                privsb.append(pushPriv.isSelected()?"push,":"");
                                privsb.append("pull");
                                entry.session.addAccount(name.getValue(),pass.getValue(),privsb.toString());
                                synchronized (TerminalMain.window.tips) {
                                    TerminalMain.window.tips.setText(entry.name+":Add account successfully");
                                }
                                Out.sayWithTimeLn(entry.name+":Add account successfully");
                            } catch (Exception exception) {
                                synchronized (TerminalMain.window.tips) {
                                    TerminalMain.window.tips.setText(entry.name+":Failed to add account.");
                                }
                                Out.sayWithTimeLn(TerminalMain.getErrorInfo(exception));
                            }
                        }else {
                            synchronized (TerminalMain.window.tips) {
                                TerminalMain.window.tips.setText(entry.name+":Failed to add account:Session unavailable.");
                            }
                            Out.sayWithTimeLn(entry.name+":Failed to add account:Session unavailable.");
                        }
                    }


                }
            }).start();
        });
        this.add(add);

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
                add.setEnabled(true);
            }else {
                as.addItem("No account has this privilege.");
                add.setEnabled(false);
            }
        }else {
            as.addItem("No account,please login in setting panel.");
            add.setEnabled(false);
        }
    }
}
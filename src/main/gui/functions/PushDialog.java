package main.gui.functions;

import main.boot.Preference;
import main.boot.TerminalMain;
import main.fields.InputAreaField;
import main.fields.InputField;
import main.gui.setting.Account;
import main.util.Out;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PushDialog extends JDialog {
    public JLabel astips=new JLabel("As:");
    public JComboBox<String> as=new JComboBox<>();
    public InputField target=new InputField("target:",250,25,60);
    public InputField title=new InputField("title:",250,25,60);
    public InputAreaField content=new InputAreaField("content:",250,150,60);
    public JButton push=new JButton("Push");
    public PushDialog(){
        super(TerminalMain.window);
        this.setLayout(null);
        this.setTitle("Push a notification");
        this.setResizable(false);
        this.setBounds(380,380,300,360);


        astips.setBounds(10,10,60,25);
        this.add(astips);

        as.setBounds(70,10,190,25);
        this.add(as);


        target.setLocation(10,as.getY()+as.getHeight()+10);
        this.add(target);

        title.setLocation(10,target.getY()+target.getHeight()+10);
        this.add(title);

        content.setLocation(10,title.getY()+title.getHeight()+10);
        this.add(content);

        push.setBounds(10,content.getY()+content.getHeight()+10,80,30);
        push.addActionListener(e -> {
            if (target.getValue().equals("")){
                JOptionPane.showMessageDialog(PushDialog.this,"Please input target.");
                return;
            }
            TerminalMain.window.setEnabled(true);
            dispose();

            new Thread(()->{
                for (Account.Entry entry:TerminalMain.settings.accountPanel.entries){


                    if (entry.name.equals(as.getSelectedItem())){
                        if (entry.session.isAvailable()){
                            try {
                                entry.session.pushNotification(target.getValue(),title.getValue(),content.getValue());
                                synchronized (TerminalMain.window.tips) {
                                    TerminalMain.window.tips.setText(entry.name+":Push successfully");
                                }
                                Out.sayWithTimeLn(entry.name+":Push successfully");
                            } catch (Exception exception) {
                                synchronized (TerminalMain.window.tips) {
                                    TerminalMain.window.tips.setText(entry.name+":Failed to push.");
                                }
                                Out.sayWithTimeLn(TerminalMain.getErrorInfo(exception));
                            }
                        }else {
                            synchronized (TerminalMain.window.tips) {
                                TerminalMain.window.tips.setText(entry.name+":Failed to push:Session unavailable.");
                            }
                            Out.sayWithTimeLn(entry.name+":Failed to push:Session unavailable.");
                        }
                    }
                }
            }).start();
        });
        this.add(push);

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
                if (entry.session!=null&&entry.session.can("push")&&!entry.deleted)
                    as.addItem(entry.name);
            }
            if (as.getItemCount()!=0) {
                push.setEnabled(true);
            }else {
                as.addItem("No account has this privilege.");
                push.setEnabled(false);
            }
        }else {
            as.addItem("No account,please login in setting panel.");
            push.setEnabled(false);
        }
    }
}

package main.gui.functions;

import main.boot.TerminalMain;
import main.fields.InputField;
import main.gui.setting.Account;
import main.util.Out;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PullDialog extends JDialog {
    public JLabel account=new JLabel("Account:");
    public JComboBox<String> as=new JComboBox<>();
    public InputField from=new InputField("ID From:",250,25,60);
    public InputField limit=new InputField("Limit:",250,25,60);
    public JButton request=new JButton("Request");
    public PullDialog(){
        super(TerminalMain.window);
        this.setLayout(null);
        this.setTitle("Request notifications");
        this.setResizable(false);
        this.setBounds(380,380,300,200);

        account.setBounds(10,10,60,25);
        this.add(account);

        as.setBounds(70,10,190,25);
        this.add(as);

        from.setLocation(10,as.getY()+as.getHeight()+10);
        this.add(from);

        limit.setLocation(10,from.getY()+from.getHeight()+10);
        this.add(limit);

        request.setBounds(10,limit.getY()+limit.getHeight()+10,90,30);
        request.addActionListener(e->{
            int fromV,limitV;
            try {
                fromV=Integer.parseInt(from.getValue());
                limitV=Integer.parseInt(limit.getValue());
            }catch (Exception ignored){
                JOptionPane.showMessageDialog(PullDialog.this,"Please input from,limit as Integer.");
                return;
            }
            TerminalMain.window.setEnabled(true);
            dispose();

            new Thread(()->{
                for (Account.Entry entry:TerminalMain.settings.accountPanel.entries){


                    if (entry.name.equals(as.getSelectedItem())){
                        if (entry.session.isAvailable()){
                            try {
                                entry.session.requestNotifications(fromV,limitV);
                                synchronized (TerminalMain.window.tips) {
                                    TerminalMain.window.tips.setText(entry.name+":Request successfully");
                                }
                                Out.sayWithTimeLn(entry.name+":Request successfully");
                            } catch (Exception exception) {
                                synchronized (TerminalMain.window.tips) {
                                    TerminalMain.window.tips.setText(entry.name+":Failed to request.");
                                }
                                Out.sayWithTimeLn(TerminalMain.getErrorInfo(exception));
                            }
                        }else {
                            synchronized (TerminalMain.window.tips) {
                                TerminalMain.window.tips.setText(entry.name+":Failed to request:Session unavailable.");
                            }
                            Out.sayWithTimeLn(entry.name+":Failed to request:Session unavailable.");
                        }
                    }
                }
            }).start();
        });
        this.add(request);



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
                if (!entry.deleted)
                    as.addItem(entry.name);
            }
            from.input.setEnabled(true);
            limit.input.setEnabled(true);
            request.setEnabled(true);
        }else {
            as.addItem("No account,please login in setting panel.");
            request.setEnabled(false);
        }
    }
}

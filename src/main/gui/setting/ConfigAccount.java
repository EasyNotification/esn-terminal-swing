package main.gui.setting;

import main.boot.Preference;
import main.boot.TerminalMain;
import main.fields.InputField;

import javax.swing.*;
import java.util.ArrayList;

public class ConfigAccount extends JDialog {
    public InputField name=new InputField("Name:",130,25,40);
    public InputField pass=new InputField("Pass:",130,25,40);
    public JButton login=new JButton("Login");
    public JLabel tips=new JLabel("");
    public ConfigAccount(){
        super(TerminalMain.settings);
        this.setLayout(null);
        this.setResizable(false);
        this.setBounds(380,380,220,180);
        this.setTitle("Login");

        name.setLocation(10,20);
        this.add(name);

        pass.setLocation(10,name.getY()+name.getHeight()+7);
        this.add(pass);

        login.setBounds(10, pass.getY()+pass.getHeight()+20,80,20);
        login.addActionListener((e -> {
            if (name.getValue().equals("")){
                JOptionPane.showMessageDialog(this,"Please input user name");
                return;
            }
            synchronized (TerminalMain.preference.users) {
                Preference.User[] users;
                if (TerminalMain.preference.users != null) {
                    users = new Preference.User[TerminalMain.preference.users.length + 1];
                    int index = 0;
                    for (Preference.User user : TerminalMain.preference.users) {
                        users[index] = new Preference.User();
                        users[index].name = user.name;
                        users[index].pass = user.pass;
                        users[index].last=user.last;
                        index++;
                    }
                } else {
                    users = new Preference.User[1];
                }
                users[users.length - 1] = new Preference.User();
                users[users.length - 1].name = name.getValue();
                users[users.length - 1].pass = pass.getValue();
                TerminalMain.preference.users = users;
                TerminalMain.serializePreference();
            }
            TerminalMain.settings.accountPanel.addAccount(name.getValue(),pass.getValue(),0);

            this.dispose();
        }));
        this.add(login);

        tips.setBounds(login.getX()+login.getWidth()+10,login.getY(),80,20);
        this.add(tips);

        reset();
    }

    public void reset(){
        name.input.setText("");
        pass.input.setText("");
    }

}

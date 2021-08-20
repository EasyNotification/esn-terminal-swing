package main.gui.setting;

import main.boot.TerminalMain;

import javax.swing.*;

public class StartupPanel extends JPanel {
    public JCheckBox show_main_window_at_startup=new JCheckBox("Show main window at startup.");
    public JCheckBox request_history_when_login_to_a_account=new JCheckBox("Request history notifications when login to a new account.");
    public StartupPanel(){
        this.setLayout(null);

        show_main_window_at_startup.setBounds(10,10,380,25);
        show_main_window_at_startup.setSelected(TerminalMain.preference.showMainWindowAtStartup);
        show_main_window_at_startup.addActionListener(e->{
            TerminalMain.preference.showMainWindowAtStartup=show_main_window_at_startup.isSelected();
            TerminalMain.serializePreference();
        });
        this.add(show_main_window_at_startup);


        request_history_when_login_to_a_account.setBounds(10,show_main_window_at_startup.getY()+show_main_window_at_startup.getHeight()+10,380,25);
        request_history_when_login_to_a_account.setSelected(TerminalMain.preference.requestHistoryNotificationsWhenLoginToAAccount);
        request_history_when_login_to_a_account.addActionListener(e->{
            TerminalMain.preference.requestHistoryNotificationsWhenLoginToAAccount=request_history_when_login_to_a_account.isSelected();
            TerminalMain.serializePreference();
        });
        this.add(request_history_when_login_to_a_account);

    }
}

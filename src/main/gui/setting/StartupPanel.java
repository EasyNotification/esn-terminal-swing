package main.gui.setting;

import main.boot.TerminalMain;

import javax.swing.*;

public class StartupPanel extends JPanel {
    public JCheckBox show_main_window_at_startup=new JCheckBox("Show main window at startup.");
    public StartupPanel(){
        this.setLayout(null);

        show_main_window_at_startup.setBounds(10,10,380,25);
        show_main_window_at_startup.setSelected(TerminalMain.preference.showMainWindowAtStartup);
        show_main_window_at_startup.addActionListener(e->{
            TerminalMain.preference.showMainWindowAtStartup=show_main_window_at_startup.isSelected();
            TerminalMain.serializePreference();
        });
        this.add(show_main_window_at_startup);

    }
}

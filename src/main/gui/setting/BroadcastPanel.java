package main.gui.setting;

import main.boot.TerminalMain;
import main.gui.Window;

import javax.swing.*;
import java.awt.*;

public class BroadcastPanel extends JPanel {

    public JCheckBox show_broadcast_window=new JCheckBox("Show a window to broadcast new notifications.");
    public JLabel configTips=new JLabel("Broadcast bounds:");
    public JLabel tips2=new JLabel("  Enable this to pop up a window to show new notification.");
    public JButton config=new JButton("Config broadcast windows' location and width.");
    public BroadcastPanel(){
        this.setLayout(null);


        show_broadcast_window.setBounds(10,10,380,25);
        show_broadcast_window.setSelected(TerminalMain.preference.broadcast);
        show_broadcast_window.addActionListener(e->{
            TerminalMain.preference.broadcast=show_broadcast_window.isSelected();
            TerminalMain.serializePreference();
        });
        this.add(show_broadcast_window);
        tips2.setBounds(10,show_broadcast_window.getY()+show_broadcast_window.getHeight(),500,25);
        tips2.setFont(Window.tips2Font);
        this.add(tips2);

        configTips.setBounds(10,tips2.getY()+tips2.getHeight()+10,300,25);
        this.add(configTips);


        config.setBounds(10,configTips.getY()+configTips.getHeight()+8,300,25);
        config.addActionListener(e->{
            TerminalMain.settings.broadcastBoundConfig.setVisible(true);
        });
        this.add(config);
    }
}

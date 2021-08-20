package main.gui.setting;

import main.boot.Broadcast;
import main.boot.TerminalMain;
import main.gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BroadcastBoundConfig extends JFrame {
    public JLabel tips=new JLabel("Drag/Resize this window to config broadcast windows' location");
    public JLabel tips2=new JLabel("  The height of a broadcast window is determined by line amount of notifications' content.");
    public JLabel info=new JLabel();
    public JButton confirm=new JButton();
    public BroadcastBoundConfig(){
        this.setLayout(null);
        this.setBounds(TerminalMain.preference.broadcastFrameXOnScreen
                ,TerminalMain.preference.broadcastFrameYOnScreen
                , TerminalMain.preference.broadcastFrameW, 200);
        this.setTitle("Config broadcast window");


        tips.setBounds(10,10,500,25);
        this.add(tips);

        tips2.setBounds(10,tips.getY()+tips.getHeight()+5,800,25);
        tips2.setFont(Window.tips2Font);
        this.add(tips2);

        info.setBounds(10,tips2.getY()+tips2.getHeight()+5,500,25);
        this.add(info);



        confirm.setBounds(10,info.getY()+info.getHeight()+5,200,25);
        confirm.setText("Click me to save this config.");
        confirm.addActionListener(e->{
            TerminalMain.preference.broadcastFrameW=BroadcastBoundConfig.this.getWidth();
            TerminalMain.preference.broadcastFrameYOnScreen= BroadcastBoundConfig.this.getY();
            TerminalMain.preference.broadcastFrameXOnScreen=BroadcastBoundConfig.this.getX();
            TerminalMain.serializePreference();

            BroadcastBoundConfig.this.dispose();
        });
        this.add(confirm);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                info.setText("Current config: x="+BroadcastBoundConfig.this.getX()+" y="+BroadcastBoundConfig.this.getY()+" width="+BroadcastBoundConfig.this.getWidth());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                info.setText("Current config: x="+BroadcastBoundConfig.this.getX()+" y="+BroadcastBoundConfig.this.getY()+" width="+BroadcastBoundConfig.this.getWidth());
            }
        });
        this.setVisible(false);

    }
}

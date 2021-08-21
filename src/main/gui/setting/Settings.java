package main.gui.setting;

import main.boot.TerminalMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Settings extends JDialog {
    JTabbedPane tabbedPane=new JTabbedPane();
    public JScrollPane accScroll;
    public Account accountPanel;
    public LogPanel logPanel;
    public StartupPanel startupPanel;
    public BroadcastPanel broadcastPanel;
    public BroadcastBoundConfig broadcastBoundConfig;
    public AboutPanel aboutPanel;
    public Settings(){
        super(TerminalMain.window);
        this.setTitle("Settings");
        this.setResizable(false);

        this.setBounds(350,350,400,400);

        this.add(tabbedPane);

        accountPanel=new Account();


        accScroll=new JScrollPane(accountPanel);
        accScroll.setBorder(null);
        accountPanel.setBounds(0,0,360,400);
        accountPanel.setPreferredSize(new Dimension(360,400));
        this.tabbedPane.addTab("Account",accScroll);


        broadcastPanel=new BroadcastPanel();
        this.tabbedPane.addTab("Broadcast",broadcastPanel);

        broadcastBoundConfig=new BroadcastBoundConfig();


        startupPanel=new StartupPanel();
        this.tabbedPane.addTab("Startup",startupPanel);

        logPanel=new LogPanel();
        this.tabbedPane.addTab("Log",logPanel);

        aboutPanel=new AboutPanel();
        this.tabbedPane.addTab("About",aboutPanel);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                TerminalMain.window.setEnabled(true);
                dispose();

            }
        });
//        this.setVisible(true);
    }
}

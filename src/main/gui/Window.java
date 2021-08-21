package main.gui;

import main.boot.Broadcast;
import main.boot.TerminalMain;
import main.boot.TrayMgr;
import main.fields.Icon;
import main.fields.RectLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    public static Font tips2Font=new Font("",Font.ITALIC,12);
    public JScrollPane scrollPane;
    public NotificationPanel notificationPanel=new NotificationPanel();
    public Icon pull=new Icon(IconsData.PULL,2),push=new Icon(IconsData.PUSH,2)
            ,add=new Icon(IconsData.ADD,2),remove=new Icon(IconsData.REMOVE,2)
            ,setting=new Icon(IconsData.setting,1);
    public RectLabel sep=new RectLabel();
    public final JLabel tips=new JLabel("No notification received.Please click the gear to edit your account information.");
    public Window(){
        this.setLayout(null);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(300,300,TerminalMain.preference.mainWindowWidth, TerminalMain.preference.mainWindowHeight);
        this.setTitle("esn-terminal-swing");
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                TerminalMain.preference.mainWindowHeight=getHeight();
                TerminalMain.preference.mainWindowWidth=getWidth();
                TerminalMain.serializePreference();
            }
        });

        pull.setBounds(20,10,40,40);
        pull.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setEnabled(false);
                TerminalMain.pullDialog.reloadAccount();
                TerminalMain.pullDialog.setVisible(true);
            }
        });
        this.add(pull);

        push.setBounds(pull.getX()+pull.getWidth()+15,pull.getY(),40,40);
        push.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setEnabled(false);
                TerminalMain.pushDialog.reloadAccount();
                TerminalMain.pushDialog.setVisible(true);
            }
        });
        this.add(push);

        add.setBounds(push.getX()+push.getWidth()+15,push.getY(),40,40);
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setEnabled(false);
                TerminalMain.addDialog.reloadAccount();
                TerminalMain.addDialog.setVisible(true);
            }
        });
        this.add(add);

        remove.setBounds(add.getX()+add.getWidth()+15,add.getY(),40,40);
        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setEnabled(false);
                TerminalMain.removeDialog.reloadAccount();
                TerminalMain.removeDialog.setVisible(true);
            }
        });
        this.add(remove);

        sep.setBounds(remove.getX()+remove.getWidth()+13,remove.getY()+5,3,30);
        sep.setForeground(Color.lightGray);
        this.add(sep);

        setting.setBounds(sep.getX()+sep.getWidth()+13,remove.getY(),40,43);
        setting.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setEnabled(false);
                TerminalMain.settings.setVisible(true);
            }
        });
        this.add(setting);

        tips.setBounds(15,60,830,20);
        tips.setFont(Window.tips2Font);
        this.add(tips);

        scrollPane=new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(notificationPanel);
        scrollPane.setBounds(15,85,getWidth()-40,getHeight()-150);
        scrollPane.setBorder(null);
//        scrollPane.setLayout(null);

        notificationPanel.setLocation(0,0);
//        notificationPanel.setBounds(15,85,this.getWidth()-40,this.getHeight()-150);
        this.add(scrollPane);


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                scrollPane.setBounds(15,85,getWidth()-40,getHeight()-150);
                notificationPanel.setPreferredSize(new Dimension(scrollPane.getWidth()-40, scrollPane.getHeight()));
//                notificationPanel.setBounds(15,85,getWidth()-40,getHeight()-150);
                notificationPanel.resize();
            }
        });
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                super.windowGainedFocus(e);



                for (int i=0;i<Broadcast.broadcastList.size();i++){
                    Broadcast.broadcastList.get(i).dispose();
                }
                Broadcast.broadcastList.clear();

                TerminalMain.trayMgr.trayIcon.setImage(TrayMgr.defaultIcon);
            }
        });

//        this.setVisible(true);
    }
}

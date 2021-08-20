package main.boot;

import packs.PackRespNotification;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Broadcast extends JDialog {
    public static final ArrayList<Broadcast> broadcastList=new ArrayList<>();

    private static final int LINE_HEIGHT=25,LINE_SEP=-4;

    private PackRespNotification notification;
    private String receiver;

    private Broadcast(PackRespNotification notification,String receiver){
        this.notification=notification;
        this.receiver=receiver;

        this.setLayout(null);
        this.setResizable(false);
        this.setAlwaysOnTop(true);




        this.setTitle(notification.Source+"->"+receiver+" <"+notification.Title+"> "+notification.Time);

        String[] lines=notification.Content.split("\n");

        int len=lines.length,height=50;
        for (int i=0;i<len;i++){
            JLabel line=new JLabel(lines[i]);
            line.setBounds(10,i*(LINE_HEIGHT+LINE_SEP),TerminalMain.preference.broadcastFrameW,LINE_HEIGHT);
            this.add(line);
            height+=LINE_HEIGHT+LINE_SEP;
        }

        this.setSize(TerminalMain.preference.broadcastFrameW, Math.max(height, 100));


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                synchronized (broadcastList){
                    int myindex=broadcastList.indexOf(Broadcast.this);



                    for (int i=myindex;i<broadcastList.size();i++){
                        if (i>myindex){
                            broadcastList.get(i).setLocation(broadcastList.get(i).getX(),broadcastList.get(i).getY()-Broadcast.this.getHeight()-10);
                        }
                    }
                    broadcastList.remove(Broadcast.this);
                    Broadcast.this.dispose();

                    if (broadcastList.size()==0){
                        TerminalMain.trayMgr.trayIcon.setImage(TrayMgr.defaultIcon);
                    }
                }
            }
        });
    }


    public static void announce(PackRespNotification notification, String receiver){
        if (TerminalMain.window.isFocused()){
            return;
        }
        if (!TerminalMain.preference.broadcast){
            return;
        }
        Broadcast broadcast=new Broadcast(notification,receiver);
        int y=0;
        for (Broadcast broadcast1:broadcastList){
            y+=broadcast1.getHeight()+10;
        }
        broadcast.setLocation(TerminalMain.preference.broadcastFrameXOnScreen,y+TerminalMain.preference.broadcastFrameYOnScreen);
        synchronized (broadcastList){
            broadcastList.add(broadcast);
        }
        broadcast.setVisible(true);
    }
}

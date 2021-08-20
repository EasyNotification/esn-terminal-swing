package main.gui;

import main.boot.TerminalMain;
import main.boot.TrayMgr;
import packs.PackRespNotification;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class NotificationPanel extends JPanel {
    public static final ArrayList<Integer> globalNotificationIDs=new ArrayList<>();
    public class Entry extends JPanel{
        final static int LINE_HEIGHT=30;
        final Font TITLE_FONT=new Font("Serif",Font.BOLD,16);
        public PackRespNotification notification;
        private Border border;
        public Entry(PackRespNotification notification,String target){
            this.setLayout(null);
            this.border=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),notification.Source+"->"+target+"<"+notification.Title+"> "+notification.Time, TitledBorder.LEFT,TitledBorder.TOP,TITLE_FONT);
            this.setBorder(border);


            this.notification=notification;

            String[] lines=notification.Content.split("\n");
            int len=lines.length;
            for (int i=0;i<len;i++){
                JLabel label=new JLabel(lines[i]);
//                label.setFont(LINE_FONT);
                label.setBounds(15,i*(LINE_HEIGHT-15)+20,1000,LINE_HEIGHT);
                this.add(label);
            }

            this.setSize(10,len*(LINE_HEIGHT-15)+40);
        }
    }
    private ArrayList<Entry> entries=new ArrayList<>();
    public NotificationPanel(){
        this.setLayout(null);
//        this.setBorder(BorderFactory.createEtchedBorder());
    }

    public void addEntry(PackRespNotification notification,String target){
        Entry entry=new Entry(notification, target);
        entry.setBounds(5,0,this.getWidth()-10,entry.getHeight());

        //下移之前的
        synchronized (entries) {
            for (Entry e : entries) {
                e.setLocation(e.getX(), e.getY() + (entry.getHeight() + 10));
            }

            this.entries.add(entry);
        }
        this.add(entry);

        this.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()+entry.getHeight()+10));
        TerminalMain.window.tips.setText("Notification count:"+entries.size());

        if (!TerminalMain.window.isFocused()){
            TerminalMain.trayMgr.trayIcon.setImage(TrayMgr.newIcon);
        }

        this.repaint();
    }

    public void resize(){
        for (Entry entry:entries){
            entry.setSize(this.getWidth()-10,entry.getHeight());
        }
    }
}

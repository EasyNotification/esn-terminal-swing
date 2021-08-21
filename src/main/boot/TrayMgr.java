package main.boot;

import main.fields.BinaryToImg;
import main.gui.IconsData;
import main.util.Out;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrayMgr {
    public static Image defaultIcon= BinaryToImg.getImage(IconsData.TRAY_DEFAULT,new Dimension(16,16),Color.BLACK);
    public static Image newIcon=BinaryToImg.getImage(IconsData.TRAY_NEW,new Dimension(16,16),new Color(0, 116, 255, 255));
    public TrayIcon trayIcon;



    public MenuItem broadcast=new MenuItem();

    public TrayMgr()throws Exception{
        if (SystemTray.isSupported()){
            trayIcon=new TrayIcon(defaultIcon);
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getButton()==1){//left
                        TerminalMain.window.setVisible(true);
                        TerminalMain.window.requestFocus();
                    }else if (e.getButton()==3){//right

                    }
                }
            });


            PopupMenu popupMenu=new PopupMenu();

            MenuItem exit=new MenuItem("Exit");
            exit.addActionListener(e->{
                System.exit(0);
            });

            MenuItem show=new MenuItem("Main Window");
            show.addActionListener(e->{
                TerminalMain.window.setVisible(true);
                TerminalMain.window.requestFocus();
            });

            broadcast.setLabel((TerminalMain.preference.broadcast?"Disable":"Enable")+" broadcast");
            broadcast.addActionListener(e->{
                TerminalMain.preference.broadcast=!TerminalMain.preference.broadcast;
                TerminalMain.settings.broadcastPanel.show_broadcast_window.setSelected(TerminalMain.preference.broadcast);
                broadcast.setLabel((TerminalMain.preference.broadcast?"Disable":"Enable")+" broadcast");
                TerminalMain.serializePreference();

            });

            popupMenu.add(broadcast);
            popupMenu.add(show);
            popupMenu.add(exit);

            this.trayIcon.setPopupMenu(popupMenu);

            addToSystemTray();
        }else {
            Out.sayWithTimeLn("Your system does not support System Tray.R U using Linux?");
        }
    }
    public void addToSystemTray()throws Exception{
        SystemTray systemTray=SystemTray.getSystemTray();
        systemTray.add(trayIcon);
    }
}

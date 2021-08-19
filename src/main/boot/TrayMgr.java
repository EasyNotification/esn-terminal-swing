package main.boot;

import main.fields.BinaryToImg;
import main.gui.IconsData;
import main.util.Out;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrayMgr {
    public static Image defaultIcon= BinaryToImg.getImage(IconsData.TRAY_DEFAULT,new Dimension(16,16),Color.BLACK);
    public static Image newIcon=BinaryToImg.getImage(IconsData.TRAY_NEW,new Dimension(16,16),new Color(0, 116, 255, 255));
    public TrayIcon trayIcon;
    public TrayMgr()throws Exception{
        if (SystemTray.isSupported()){
            trayIcon=new TrayIcon(defaultIcon);
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getButton()==1){//left
                        TerminalMain.window.setVisible(true);
                    }else if (e.getButton()==3){//right

                    }
                }
            });
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

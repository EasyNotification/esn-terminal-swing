package main.gui.setting;

import main.boot.TerminalMain;

import javax.swing.*;
import java.awt.*;

public class AboutPanel extends JPanel {
    public JLabel banner=new JLabel("esn-terminal-swing v"+TerminalMain.version);
    public JLabel api=new JLabel("with esn-api-java-0.7");
    public JLabel address=new JLabel("See:https://github.com/EasyNotification");
    public JLabel license=new JLabel("Open source license:Apache 2.0");
    public AboutPanel(){
        this.setLayout(null);

        banner.setBounds(10,10,400,50);
        banner.setFont(new Font("Serif", Font.BOLD,25));
        this.add(banner);

        api.setBounds(10,banner.getY()+banner.getHeight()-5,600,20);
        this.add(api);

        address.setBounds(10,api.getY()+api.getHeight()+15,600,20);
        this.add(address);


        license.setBounds(10,address.getY()+address.getHeight()+10,600,20);
        this.add(license);
    }
}

package main.fields;

import javax.swing.*;
import java.awt.*;

public class RectLabel extends JLabel {
    @Override
    public void paint(Graphics g){
        g.clearRect(0,0,this.getWidth(),this.getHeight());
        g.setColor(getForeground());
        g.fillRect(0,0,this.getWidth(),this.getHeight());
    }
}

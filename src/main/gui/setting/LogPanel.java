package main.gui.setting;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LogPanel extends JPanel {
    public JScrollPane scrollPane;
    public JTextArea logArea=new JTextArea();
    public LogPanel(){
        this.setLayout(null);

        logArea.setEditable(false);
//        this.add(logArea);
        scrollPane=new JScrollPane(logArea);
        scrollPane.setBorder(null);
        scrollPane.setBounds(2,2,getWidth()-4,getHeight()-4);
        this.add(scrollPane);


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                scrollPane.setBounds(2,2,getWidth()-4,getHeight()-4);
            }
        });
    }
    public void append(String s){
        logArea.append(s);
    }
}

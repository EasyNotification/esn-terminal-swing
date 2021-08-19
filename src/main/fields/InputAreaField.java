package main.fields;

import javax.swing.*;
import java.awt.*;

public class InputAreaField extends JPanel {
	public JLabel text=new JLabel("undefined");
	public JTextArea input=new JTextArea();
	JScrollPane scro;
	public static final Color fcl=new Color(56, 56, 56);
	public static final Color bgf=new Color(220, 220, 220);
	public static final Font font=new Font("",Font.PLAIN,15);
	public InputAreaField(String name,int width,int height,int sepx) {
		this.setLayout(null);
		this.setSize(width, height);
		
		text.setText(name);
		text.setSize(sepx, 30);
		text.setLocation(0, 0);
		text.setForeground(fcl);
		this.add(text);
		
		scro=new JScrollPane(input); 
		
		scro.setSize(width-sepx, height);
		scro.setLocation(sepx, 0);
		scro.setBorder(null);
		input.setForeground(fcl);
		input.setBackground(bgf);
		input.setCaretColor(fcl);
//		input.setFont(font);
		input.setLineWrap(true);
		
		this.add(scro);
		//this.add(input);
		
	}
	public String getValue() {
		return input.getText();
	}
	public void setValue(String s) {
		this.input.setText(s);
	}
	public void updateCom() {
		text.setBackground(getBackground());
	}
}

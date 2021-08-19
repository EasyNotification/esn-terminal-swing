package main.fields;

import javax.swing.*;
import java.awt.*;

public class ComboBoxField extends JPanel {
	public JLabel text=new JLabel("undefined");
	public JComboBox input=new JComboBox();
	public static final Color fcl=new Color(240,240,240);
	public static final Color bgf=new Color(60,60,60);
	public ComboBoxField(String name,int width,int height,int sepx) {
		this.setLayout(null);
		this.setSize(width, height);
		
		text.setText(name);
		text.setSize(sepx, height);
		text.setLocation(0, 0);
		text.setForeground(fcl);
		this.add(text);
		
		input.setSize(width-sepx, height-4);
		input.setLocation(sepx, 2);
		input.setForeground(fcl);
		input.setBackground(bgf);
		this.add(input);
	}
	public String getSelectedEle() {
		return (String) input.getSelectedItem();
	}
	public void setSelectEle(String s) {
		input.setSelectedItem(s);
	}
	public void setSelectEle(int index) {
		input.setSelectedIndex(index);
	}
	public void updateCom() {
		text.setBackground(getBackground());
	}
}

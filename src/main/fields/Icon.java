package main.fields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Icon extends JPanel {
	String iconData="";
	public Color one=new Color(130,130,130),zero;
	public Color entered=new Color(180,180,180),exited=new Color(150,150,150),pressed=new Color(90,90,90);
	public Color shadow=new Color(100,100,100,70);
	public int scale=1;
	public void paint(Graphics g) {
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g.clearRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(zero);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		int len=iconData.length();
		char[] ic=iconData.toCharArray();
		//shadow
		int index=0;
		g.setColor(shadow);
		label:for(int i=0;i<this.getHeight();i++) {
			l2:for(int j=0;j<this.getWidth();j++) {
				if(ic[index]=='2') {
					break label;
				}else if(ic[index]=='3') {
					index++;
					break;
				}
				//System.out.println(ic[index]);
				if (ic[index]=='0') {
					index++;
					continue;
				}
//				g.drawLine(j, i, j, i);
				g.fillRect(j*scale+2,i*scale+2,scale,scale);
				index++;
			}
		}

		index=0;
		label:for(int i=0;i<this.getHeight();i++) {
			l2:for(int j=0;j<this.getWidth();j++) {
				if(ic[index]=='2') {
					break label;
				}else if(ic[index]=='3') {
					index++;
					break;
				}
				//System.out.println(ic[index]);
				if (ic[index]=='0') {
					index++;
					continue;
				}
				g.setColor(one);
//				g.drawLine(j, i, j, i);
				g.fillRect(j*scale,i*scale,scale,scale);
				index++;
			}
		}
	}
	public Icon() {
		
	}
	public Icon(String data,int scale) {
		this.iconData=data;
//		zero=new Color(43,43,43);
		this.setBackground(new Color(0,0,0,20));
		this.scale=scale;
		this.exited=new Color(this.one.getRGB());

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				setColor(entered);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				setColor(exited);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				setColor(pressed);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				setColor(entered);
			}
		});
	}
	public void setBackground(Color bg) {
		zero=bg;
		super.setBackground(bg);
		this.repaint();
	}
	public void set(String code) {
		this.iconData=code;
	}
	public void setColor(Color front) {
		this.one=front;
		this.repaint();
	}
}

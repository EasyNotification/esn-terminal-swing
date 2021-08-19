package main.fields;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BinaryToImg {
    public static  BufferedImage getImage(String s, Dimension size,Color color){
        BufferedImage bufferedImage=new BufferedImage(size.width,size.height,BufferedImage.TYPE_INT_ARGB);
        char[] ic=s.toCharArray();
        int index=0;
        label:for(int i=0;i<bufferedImage.getHeight();i++) {
            l2:for(int j=0;j<bufferedImage.getWidth();j++) {
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
                bufferedImage.setRGB(j,i,color.getRGB());
                index++;
            }
        }
        return bufferedImage;
    }
}

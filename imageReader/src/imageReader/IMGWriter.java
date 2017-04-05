package imageReader;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class IMGWriter {
	private BufferedImage img;
	private int curWidth = 0;
	private int curHeight= 0;
	private Color curColor = null;
	private int curColorByte=0;
	private int r;
	private int g;
	private int b;
	int intToAnd;
	
	public IMGWriter(){
		
	}
	
	public IMGWriter(BufferedImage img,int bits){
		this.img = img;
		this.curColor = new Color(img.getRGB(curWidth, curHeight));
		curColorByte=0;
		this.intToAnd = 256- (int)Math.pow(2, bits);
//		System.out.println("INTTOAND: "+intToAnd);
	}
	
	public void insertByte(int intToOr){
		if (curColorByte==3){
			Color newc = new Color(r,g,b,curColor.getAlpha());
			img.setRGB(curWidth, curHeight, newc.getRGB());
			//increment pixel
			curWidth +=1;
			if(curWidth==img.getWidth()){
				curWidth=0;
				curHeight+=1;
			}
			curColor = new Color(img.getRGB(curWidth, curHeight));
			curColorByte=0;
		}
		
		if (curColorByte==0){
			r = curColor.getRed();
//			System.out.println("Red: "+r);
			r=r&this.intToAnd;
//			System.out.println("Red after and: "+r);
			r=r|intToOr;
//			System.out.println("Red after or: "+r);
		}else if(curColorByte==1){
			g = curColor.getGreen();
//			System.out.println("Green: "+g);
			g=g&this.intToAnd;
//			System.out.println("Green after and: "+g);
			g=g|intToOr;
//			System.out.println("Green after or: "+g);
		}else if(curColorByte==2){
			b = curColor.getBlue();
//			System.out.println("Blue: "+b);
			b=b&this.intToAnd;
//			System.out.println("Blue after and: "+b);
			b=b|intToOr;
//			System.out.println("Blue after or: "+b);
		}
		//increment pixel component
		curColorByte++;
	}
	
	public BufferedImage getIMG(){
		return img;
	}
}

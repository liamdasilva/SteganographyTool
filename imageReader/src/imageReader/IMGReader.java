package imageReader;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class IMGReader {
	private BufferedImage img;
	private int curWidth = 0;
	private int curHeight= 0;
	private Color curColor = null;
	private int curColorByte=0;
	private int r;
	private int g;
	private int b;
	private int bits;
	int intToAnd;
	
	public IMGReader(){
		
	}
	
	public IMGReader(BufferedImage img,int bits){
		this.img = img;
		this.curColor = new Color(img.getRGB(curWidth, curHeight));
		curColorByte=0;
		this.intToAnd = (int)Math.pow(2, bits)-1;
		this.bits = bits;
		System.out.println("INTTOAND: "+intToAnd);
	}

	public String readBits(){
		String bitString="";
		if (curColorByte==3){
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
			bitString+=String.format("%"+bits+"s", Integer.toBinaryString(r)).replace(' ', '0');
		}else if(curColorByte==1){
			g = curColor.getGreen();
//			System.out.println("Green: "+g);
			g=g&this.intToAnd;
//			System.out.println("Green after and: "+g);
			bitString+=String.format("%"+bits+"s", Integer.toBinaryString(g)).replace(' ', '0');
		}else if(curColorByte==2){
			b = curColor.getBlue();
//			System.out.println("Blue: "+b);
			b=b&this.intToAnd;
//			System.out.println("Blue after and: "+b);
			bitString+=String.format("%"+bits+"s", Integer.toBinaryString(b)).replace(' ', '0');
		}
		//increment pixel component
		curColorByte++;
		return bitString;
	}
	
	public BufferedImage getIMG(){
		return img;
	}
}

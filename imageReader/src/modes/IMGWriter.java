package modes;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * This class is used to read bits from an image. It keeps track of the current pixel and color byte being written to automatically.
 * @author liamdasilva
 *
 */
public class IMGWriter {
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
	
	/**
	 * Default constructor
	 */
	public IMGWriter(){
		
	}
	
	/**
	 * Constructor to create new IMGWriter
	 * @param img (BufferedImage) the image to be encoded into
	 * @param bits (int) the bit encoding to be used
	 */
	public IMGWriter(BufferedImage img,int bits){
		this.img = img;
		this.curColor = new Color(img.getRGB(curWidth, curHeight));
		curColorByte=0;
		this.intToAnd = 256- (int)Math.pow(2, bits);
		this.bits = bits;
//		System.out.println("INTTOAND: "+intToAnd);
	}
	
	/**
	 * Inserts x bits into next byte of the image, where x is the number of bits the object was initialized with.
	 * @param nextBits
	 * @return (boolean) whether successful or not
	 */
	public boolean insertBits(String nextBits){
		if (nextBits.length()==this.bits){
			String blockToOr=String.format("%8s", nextBits).replace(' ', '0');
			int intToOr=Integer.parseInt(blockToOr, 2);
			insertByte(intToOr);
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Inserts a byte into the image. The byte given should be padded with zeroes.
	 * @param intToOr
	 */
	private void insertByte(int intToOr){
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
	
	/**
	 * Returns the (BufferedImage) image object
	 * @return the (BufferedImage) image object
	 */
	public BufferedImage getIMG(){
		return img;
	}
}

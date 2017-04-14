package modes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author liamdasilva, daxs3130@mylaurier.ca
 *
 */
public class Utilities {
	//the number of channels per pixel to encode (3 being RGB, 4 being RGBA)
	private static final int NUM_CHANNELS = 3;
	//the number of bytes an integer takes up (used to encode file length at start)
	private static final int INT_BYTES = 32;
	
	/**
	 * Creates a BufferedImage object from an image filename/path
	 * @param fileName the image filename
	 * @return the (BufferedImage) image handle
	 */
	private static BufferedImage getBufferedImageFromFile(String fileName){
		File origImage = new File(fileName);
		BufferedImage img = null;
		try {
			img = ImageIO.read(origImage);
			//BufferedImage newImage = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
			//System.out.println("Height: "+img.getHeight()+" Width: "+img.getWidth());
			return img;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Determines whether a file will fit into the image using the specified number of bit encoding
	 * @param img the image to encode
	 * @param file the file to be encoded into the image
	 * @param bits the number of bits to use
	 * @return (boolean) true/false
	 */
	private static boolean fileWillFit(BufferedImage img, File file, int bits){
		int l =(int) file.length();
		int bytes = getMaxBytes(img,bits);
		return bytes>l;
	}
	
	/**
	 * Returns the maximum number of bytes that can be encoded in the image using the specified number of bits to encode
	 * @param img the image to encode
	 * @param bits the number of bits used to encode
	 * @return the number of bytes that can be encoded in the image
	 */
	private static int getMaxBytes(BufferedImage img,int bits){
		return (NUM_CHANNELS*img.getHeight()*img.getWidth()*bits)/8;
	}
	
	/**
	 * Returns the maximum number of bytes that can be encoded in the image using the specified number of bits to encode
	 * @param img the path/name of image to encode
	 * @param bits the number of bits used to encode
	 * @return the number of bytes that can be encoded in the image
	 */
	public static int getMaxBytes(String imageName,int bits){
		BufferedImage img = getBufferedImageFromFile(imageName);
		return (NUM_CHANNELS*img.getHeight()*img.getWidth()*bits)/8;
	}
	
	
	/**
	 * Encodes a file into an image with the lowest possible number of 'bits per byte' encoding
	 * @param imageName the name/path of the image
	 * @param fileName the name/path of the file to be encoded into the image
	 * @param outFile the name/path of the encoded image that will be written out
	 * @return (int) the number of bits used to encode the file. -1 if encoding failed
	 */
	public static int insertFileOptimally(String imageName, String fileName, String outFile){
		BufferedImage img = getBufferedImageFromFile(imageName);
		File file = new File(fileName);
		int i = 1;
		while(i<8 && !fileWillFit(img,file,i)){
			i++;
		}
		if(!(insertFileIntoImage(imageName,fileName,i,outFile)==-1)){
			return i;
		}else{
			return -1;
		}
	}
	
	
	/**
	 * Encodes a file into an image given the number of 'bits per byte' to use for encoding
	 * @param imageName the name/path of the image
	 * @param fileName the name/path of the file to be encoded into the image
	 * @param bits the number of 'bits per byte' to use for encoding
	 * @param outFile the name/path of the encoded image that will be written out
	 * @return (int) number of bytes successfully inserted. -1 if failure
	 * 
	 */
	public static int insertFileIntoImage(String imageName, String fileName, int bits, String outFile){
		if(bits>8||bits<0){
			return -1;
		}
		BufferedImage img = getBufferedImageFromFile(imageName);
		File file = new File(fileName);
		boolean itFits = fileWillFit(img,file,bits);
		//System.out.println("Will the file fit in the image?: "+itFits);
		try {
			if(itFits){
				int length = (int) file.length();//file length in bytes
				
				//System.out.println("File Length: "+length);
				int numBytes = length+(bits-length%bits);//length of file in bytes rounded up to multiple of 'bits' variable
				int numBitBlocks = numBytes/bits;//number of bitblocks to be read in
				
				IMGWriter imgTracker = new IMGWriter(img,bits);
				
				FileInputStream in = new FileInputStream(fileName);
				
				StringBuilder sb = new StringBuilder();
				
				//insert length of file into image first
				//the number of bits used is equal to 32+(bits-32%bits)
				int ls = INT_BYTES+(bits-INT_BYTES%bits);//length of file length string
				String lengthString = String.format("%"+ls+"s", Integer.toBinaryString(length)).replace(' ', '0');
				for (int i=0;i<ls/bits;i++){
					String nextBits = lengthString.substring(i*bits, i*bits+bits);
					boolean success = imgTracker.insertBits(nextBits);
					if (!success){
						return -1;
					}
				}
				
				//insert bytes of file in bit blocks of size 8*bits
				for(int i=0;i<numBitBlocks;i++){
					sb = new StringBuilder();
					for(int j=0;j<bits;j++){
						int next = in.read();
						if (next==-1){
							sb.append("00000000"); //padding if necessary once final byte is reached
						}else{
							sb.append(String.format("%8s", Integer.toBinaryString(next)).replace(' ', '0'));
						}
					}
					String bitBlock = sb.toString();
//					System.out.println(bitBlock);
					for (int k=0;k<8;k++){
						//System.out.println(block.substring(k*bits, k*bits+bits));
						String nextBits = bitBlock.substring(k*bits, k*bits+bits);
						boolean success = imgTracker.insertBits(nextBits);
						if (!success){
							return -1;
						}
					}
				}
				
				//get output file extension
				String[] array = imageName.split("\\.");
				String extension = array[array.length-1];
				ImageIO.write(imgTracker.getIMG(), extension, new File(outFile));
				return length;
				
			}else{
				return -1;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	/**
	 * Extracts an encoded file from an image given the number of bits initially used to encode it
	 * @param imageName the name/path of the image
	 * @param bits the number of 'bits per byte' that was used for encoding
	 * @param outFile the name/path of the extracted file
	 * @return (int) number of bytes successfully read. -1 if failure
	 */
	public static int readFileFromImage(String imageName, int bits, String outFile){
		BufferedImage img = getBufferedImageFromFile(imageName);
		IMGReader imgReader = new IMGReader(img,bits);
		
		int ls = INT_BYTES+(bits-INT_BYTES%bits);//number of bits used for length of file
		int i = 0; String lengthString = "";
		while(i<ls){
			lengthString+=imgReader.readBits();
			i+=bits;
		}
		int numBytes = Integer.parseInt(lengthString, 2);
//		System.out.println(numBytes);
		int maxBytes = getMaxBytes(img,bits);
		
		//validation of numBytes
		if(numBytes <0 || numBytes > maxBytes){
			System.out.println("Faulty number of bits used");
			return -1;
		}
		
		int numBits = numBytes*8;
		i = 0;
		String string="";
		try {
			FileOutputStream file = new FileOutputStream(outFile);
			while(i<numBits){
				string+=imgReader.readBits();
				//write bits out one byte at a time 
				if (string.length()>=8){
					String substr = string.substring(0, 8);
					byte towrite =(byte) Integer.parseInt(substr, 2);
					file.write(towrite);
					string = string.substring(8);
				}
				i+=bits;
			}
			return numBytes;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
}

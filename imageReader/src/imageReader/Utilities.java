package imageReader;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utilities {
	
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
	
	private static boolean fileWillFit(BufferedImage img, File file, int bits){
		int l =(int) file.length();
		int bytes = (3*img.getHeight()*img.getWidth()*bits)/8;
		return bytes>l;
	}
	
	public static boolean insertFileIntoImage(String imageName, String fileName, int bits, String outFile){
		if(bits>8||bits<0){
			return false;
		}
		BufferedImage img = getBufferedImageFromFile(imageName);
		File file = new File(fileName);
		boolean itFits = fileWillFit(img,file,bits);
		System.out.println("Will the file fit in the image?: "+itFits);
		try {
			if(itFits){
				int length = (int) file.length();//file length in bytes
				System.out.println("Max File Length: "+(3*img.getHeight()*img.getWidth()*bits)/8);
				System.out.println("File Length: "+length);
				int numBytes = length+(bits-length%bits);//length of file in bytes rounded up to multiple of 'bits' variable
				int numBitBlocks = numBytes/bits;//number of bitblocks to be read in
				
				IMGWriter imgTracker = new IMGWriter(img,bits);
				
				FileInputStream in = new FileInputStream(fileName);
				
				StringBuilder sb = new StringBuilder();
				
				//insert length of file into image first
				//the number of bits used is equal to 32+(bits-32%bits)
				int ls = 32+(bits-32%bits);//length of file length string
				String lengthString = String.format("%"+ls+"s", Integer.toBinaryString(length)).replace(' ', '0');
				for (int i=0;i<ls/bits;i++){
					String blockToOr=String.format("%8s", lengthString.substring(i*bits, i*bits+bits)).replace(' ', '0');
//					System.out.println(blockToOr);
					int intToOr=Integer.parseInt(blockToOr, 2);
					imgTracker.insertByte(intToOr);
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
						String blockToOr=String.format("%8s", bitBlock.substring(k*bits, k*bits+bits)).replace(' ', '0');
//						System.out.println(blockToOr);
						int intToOr=Integer.parseInt(blockToOr, 2);
						imgTracker.insertByte(intToOr);
					}
				}
				String[] array = imageName.split("\\.");
				String extension = array[array.length-1];
				ImageIO.write(imgTracker.getIMG(), extension, new File(outFile));
				
			}else{
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean readFileFromImage(String imageName, int bits, String outFile){
		BufferedImage img = getBufferedImageFromFile(imageName);
		IMGReader imgReader = new IMGReader(img,bits);
		
		int ls = 32+(bits-32%bits);//number of bits used for length of file
		int i = 0; String lengthString = "";
		while(i<ls){
			lengthString+=imgReader.readBits();
			i+=bits;
		}
		int numBytes = Integer.parseInt(lengthString, 2);
		System.out.println(numBytes);
		int numBits = numBytes*8;
		i = 0;
		String string="";
		try {
			FileOutputStream file = new FileOutputStream(outFile);
			while(i<numBits){
				string+=imgReader.readBits();
				if (string.length()>=8){
					String substr = string.substring(0, 8);
					byte towrite =(byte) Integer.parseInt(substr, 2);
					file.write(towrite);
					string = string.substring(8);
				}
				i+=bits;
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}

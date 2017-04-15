package modes;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Locale;

import javax.imageio.ImageIO;

public class test {

	public static void main(String[] args) {
//		try {
////			File f = new File("/Users/liamdasilva/Desktop/Hello World.txt");
//			int l = (int) new File("/Users/liamdasilva/Desktop/Hello World.txt").length();
//			FileInputStream f = new FileInputStream("/Users/liamdasilva/Desktop/Hello World.txt");
////			byte x = (byte) f.read();
//			//byte[] data = Files.readAllBytes(fileLocation);
//		    StringBuilder sb = new StringBuilder();
//		    //System.out.println(data.length);
////		    for (byte b : data) {
////		        sb.append(String.format("%02X ", b));
////		    }
////		    System.out.println(sb.toString());
//			int i=0;
//			while (i <l){
//				byte shifted = (byte) f.read();
//				byte cur = shifted;
//				System.out.println(Integer.toBinaryString(cur));
//				cur =(byte) (shifted & 0b11);
//				System.out.println(Integer.toBinaryString(cur));
//				shifted=(byte) (shifted>>2);
//				cur =(byte) (shifted & 0b00000011);
//				System.out.println(Integer.toBinaryString(cur));
//				shifted=(byte) (shifted>>2);
//				cur =(byte) (shifted & 0b00000011);
//				System.out.println(Integer.toBinaryString(cur));
//				shifted=(byte) (shifted>>2);
//				cur =(byte) (shifted & 0b00000011);
//				System.out.println(Integer.toBinaryString(cur));
//				i++;
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String imageName = "/Users/liamdasilva/Desktop/sunset/sunset.png";
		String fileName = "/Users/liamdasilva/Desktop/old forex data/USDJPY60.csv";
		String outImage = "/Users/liamdasilva/Desktop/sunset/testing.png";
		String outImageShiftPath = "/Users/liamdasilva/Desktop/sunset/shift/";
		String outFile = "/Users/liamdasilva/Desktop/sunset/output.txt";
		
		int bitsToEncode = 1;
		
		System.out.println(NumberFormat.getNumberInstance(Locale.US)
				.format(Utilities.getMaxBytes(imageName, bitsToEncode))+
				" bytes can fit in this file using "+ bitsToEncode+" bit encoding.");
		int result = Utilities.insertFileIntoImage(imageName,fileName,bitsToEncode,outImage);
		if (!(result==-1)){
			System.out.println(NumberFormat.getNumberInstance(Locale.US).format(result)+" bytes successfully encoded.");
		}
//		int result2 = Utilities.readFileFromImage(outImage, bitsToEncode, outFile);
//		if (!(result2==-1)){
//			System.out.println(NumberFormat.getNumberInstance(Locale.US).format(result2)+" bytes successfully read.");
//		}
		
//		Utilities.signImage(imageName, "Property of Liam Da Silva", outImage);
//		String signature = Utilities.getSignature(outImage);
//		System.out.println(signature);
		
//		Utilities.shiftBits(outImage, 1, outImageShiftPath+1+"bitsshifted.png");
//		for (int i=2;i<=8;i++){
//			Utilities.shiftBits(outImageShiftPath+(i-1)+"bitsshifted.png", 1, outImageShiftPath+i+"bitsshifted.png");
//		}
		
//		Utilities.getLeastSigBits(imageName, 1, "/Users/liamdasilva/Desktop/sunset/leastsigorig.png");
		Utilities.getLeastSigBits(outImage, 1, "/Users/liamdasilva/Desktop/sunset/leastsigsigned.png");
	}

}

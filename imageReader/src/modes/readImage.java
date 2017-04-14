package modes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.imageio.ImageIO;

public class readImage {

	public static void main(String imageName, String fileName, String outImage, String outFile) {
//		imageName = "/Users/liamdasilva/Desktop/sunset/splash.png";
//		fileName = "/Users/liamdasilva/Desktop/EURUSD60.csv";
//		outImage = "/Users/liamdasilva/Desktop/sunset/testing.png";
//		outFile = "/Users/liamdasilva/Desktop/sunset/EURUSD60.csv";
		int bitsToEncode = 3;
		
		System.out.println(NumberFormat.getNumberInstance(Locale.US)
				.format(Utilities.getMaxBytes(imageName, bitsToEncode))+
				" bytes can fit in this file using "+ bitsToEncode+" bit encoding.");
		int result = Utilities.insertFileIntoImage(imageName,fileName,bitsToEncode,outImage);
		if (!(result==-1)){
			System.out.println(NumberFormat.getNumberInstance(Locale.US).format(result)+" bytes successfully encoded.");
		}
		int result2 = Utilities.readFileFromImage(outImage, bitsToEncode, outFile);
		if (!(result2==-1)){
			System.out.println(NumberFormat.getNumberInstance(Locale.US).format(result2)+" bytes successfully read.");
		}
		
//		int bitsUsed = Utilities.insertFileOptimally(imageName, fileName, outImage);
//		System.out.println("Inserted file using "+bitsUsed+" bit encoding.");
//		int result3 = Utilities.readFileFromImage(outImage, bitsUsed, outFile);
//		if (!(result3==-1)){
//			System.out.println(NumberFormat.getNumberInstance(Locale.US).format(result3)+" bytes successfully read.");
//		}
				
	}
	
	public static void encodeImage(int bitsToEncode, String imageName, String fileName, String outImage, String outFile){
		int result = Utilities.insertFileIntoImage(imageName,fileName,bitsToEncode,outImage);
		if (!(result==-1)){
			System.out.println(NumberFormat.getNumberInstance(Locale.US).format(result)+" bytes successfully encoded.");
		}
	}

}

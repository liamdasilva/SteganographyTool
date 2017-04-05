package imageReader;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class readImage {

	public static void main(String[] args) {
		String image = "/Users/liamdasilva/Desktop/sunset/sunset.png";
		String fileName = "/Users/liamdasilva/Desktop/EURUSD60.csv";
		String outImage = "/Users/liamdasilva/Desktop/sunset/test3.png";
		String outFile = "/Users/liamdasilva/Desktop/sunset/EURUSD60.csv";
		int bitsToEncode = 6;
		
		Utilities.insertFileIntoImage(image,fileName,bitsToEncode,outImage);
		Utilities.readFileFromImage(outImage, bitsToEncode, outFile);
	}

}

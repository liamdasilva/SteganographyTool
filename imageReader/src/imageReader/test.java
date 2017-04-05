package imageReader;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class test {

	public static void main(String[] args) {
		try {
//			File f = new File("/Users/liamdasilva/Desktop/Hello World.txt");
			int l = (int) new File("/Users/liamdasilva/Desktop/Hello World.txt").length();
			FileInputStream f = new FileInputStream("/Users/liamdasilva/Desktop/Hello World.txt");
//			byte x = (byte) f.read();
			//byte[] data = Files.readAllBytes(fileLocation);
		    StringBuilder sb = new StringBuilder();
		    //System.out.println(data.length);
//		    for (byte b : data) {
//		        sb.append(String.format("%02X ", b));
//		    }
//		    System.out.println(sb.toString());
			int i=0;
			while (i <l){
				byte shifted = (byte) f.read();
				byte cur = shifted;
				System.out.println(Integer.toBinaryString(cur));
				cur =(byte) (shifted & 0b11);
				System.out.println(Integer.toBinaryString(cur));
				shifted=(byte) (shifted>>2);
				cur =(byte) (shifted & 0b00000011);
				System.out.println(Integer.toBinaryString(cur));
				shifted=(byte) (shifted>>2);
				cur =(byte) (shifted & 0b00000011);
				System.out.println(Integer.toBinaryString(cur));
				shifted=(byte) (shifted>>2);
				cur =(byte) (shifted & 0b00000011);
				System.out.println(Integer.toBinaryString(cur));
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

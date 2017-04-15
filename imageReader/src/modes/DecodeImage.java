package modes;

import java.text.NumberFormat;
import java.util.Locale;

public class DecodeImage {

	public static void main(String inImage, String outFile, int bitsToEncode, String keyString){

		
		int result2 = Utilities.readFileFromImage(inImage, bitsToEncode, outFile);
		if (!(result2==-1)){
			System.out.println(NumberFormat.getNumberInstance(Locale.US).format(result2)+" bytes successfully read.");
		}
		
	}	
}

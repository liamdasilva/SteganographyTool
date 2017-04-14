package modes;

import java.text.NumberFormat;
import java.util.Locale;

public class EncodeImage {

	public static void main(String inImage, String inFile, String outFolder, String outFileName, int bitsToEncode){

		String outFile = outFolder + "\\" + outFileName;
		
		System.out.println(NumberFormat.getNumberInstance(Locale.US)
				.format(Utilities.getMaxBytes(inImage, bitsToEncode))+
				" bytes can fit in this file using "+ bitsToEncode+" bit encoding.");
		int result = Utilities.insertFileIntoImage(inImage, inFile, bitsToEncode, outFile);
		if (!(result==-1)){
			System.out.println(NumberFormat.getNumberInstance(Locale.US).format(result)+" bytes successfully encoded.");
		}
	}
	
}

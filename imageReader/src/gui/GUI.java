package gui;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;

import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*;

import modes.DecodeImage;
import modes.DecryptFile;
import modes.EncodeImage;
import modes.SignFile;
import modes.readImage;

import javax.swing.JLabel.*;

/**
 * @author m-warren, warr9620@mylaurier.ca
 *
 */
		
public class GUI extends JPanel{

	public static String S_PARAM_COLOUR = "#FFCCEE";
	public static String S_PIC_COLOUR = "#EEEEFF";
	public static String S_MODE_COLOUR = "#EEEEEE";
	public static String S_RADIO_COLOUR = "#FFFFFF";
	
	public static String R_PARAM_COLOUR = "#D5EAFF";
	public static String R_PIC_COLOUR = "#BEE3FF";
	public static String R_MODE_COLOUR = "#E8EFFC";
	public static String R_RADIO_COLOUR = "#FFFFFF";
	
	public static String A_PARAM_COLOUR = "#FFBBDD";
	public static String A_PIC_COLOUR = "#FFEEEE";
	public static String A_MODE_COLOUR = "#FFEFEF";
	public static String A_RADIO_COLOUR = "#FFFFFF";
	
	public static String M_PARAM_COLOUR = "#D6FFDE";
	public static String M_PIC_COLOUR = "#EFFFEF";
	public static String M_MODE_COLOUR = "#E9FFF3";
	public static String M_RADIO_COLOUR = "#FFFFFF";
	
	public static double PIC_WIDTH_SCALE = 0.8;
	public static double PIC_HEIGHT_SCALE = 0.8;
	public static int APP_HEIGHT = 768;
	public static int APP_WIDTH = 1024;

	//this has to be initialized first prior to making the panels since the listeners inside depend on this picture panel
	public static JPanel pnlPic = new JPanel();
	public static JLabel lblNotFound = new JLabel("No preview image available");
	public static JLabel inputPic = new JLabel();
	
	public static String outputStorageLocation; //location where output files are stored 
	
    
	public static void main(String[] args) throws IOException{
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    outputStorageLocation = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Steganography"; 
	    File folder= new File(outputStorageLocation);
	    if (! folder.exists()){
	    	UtilitiesGUI.createOutputFolder();
	    }
	    
		createGUI();
	}	    

	public static void createGUI() {
		
	    JFrame frame = new JFrame("Steganografun - Alice-o-Vision");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();    
		pane.setLayout(new GridLayout(1,2));
		
		TabbedPaneCreation.addTabbedPane(frame, pane);
		
        frame.pack();
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setSize(APP_WIDTH, APP_HEIGHT);

	    frame.setVisible(true);
	    
        
    }
		
}

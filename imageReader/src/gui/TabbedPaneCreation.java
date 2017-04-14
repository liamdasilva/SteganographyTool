package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabbedPaneCreation {
	
public static void addTabbedPane(JFrame frame, Container pane){

	JTabbedPane tabbedPane = new JTabbedPane();
	
	JPanel pnlSend = PanelSender.makePnlSend(frame);
	tabbedPane.addTab("Sender", null, pnlSend,"Encode or encrypt image for sending");
	tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	
	JPanel pnlRcv= PanelReceiver.makePnlRcv(frame);
	tabbedPane.addTab("Receiver", null, pnlRcv,"Decode or decrypt image sent");
	tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
	
	JPanel pnlAtk= PanelAttacker.makePnlAtk(frame);
	tabbedPane.addTab("Attacker", null, pnlAtk,"Attempt to crack an image's secret message");
	tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
	
	JPanel pnlMisc = PanelMisc.makePnlMisc(frame);
	tabbedPane.addTab("Misc.", null, pnlMisc,"Perform other functions for testing");
	tabbedPane.setMnemonicAt(0, KeyEvent.VK_4);

    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			
	pane.add(tabbedPane);
			
    GUI.pnlPic.setBackground(Color.decode(GUI.S_PIC_COLOUR));
    GUI.pnlPic.setBorder(new TitledBorder("Picture Preview"));
    GUI.pnlPic.add(GUI.lblNotFound);
    BufferedImage img;
    GUI.pnlPic.add(GUI.inputPic);
    
    pane.add(GUI.pnlPic);
	
    tabbedPane.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent evt) {
        	int curIndex = tabbedPane.getSelectedIndex();
        	Component c = null;
        	Component d = null; //d,e,f,g will hold input parameters from the Sender - easing use for receiver and attacker
        	Component e = null;
        	Component f = null;
        	Component g = null;
        	Component h = null;
        	JPanel curPanel = new JPanel();
        	switch (curIndex) {
	        	case 0: 
	        			c = retrieveComponent(pnlSend, 1); //1 = 			txt input picture
	        			frame.setTitle("Steganografun - Alice-o-Vision");
	        			GUI.pnlPic.setBackground(Color.decode(GUI.S_PIC_COLOUR));
	        			if (c instanceof JTextField){     
	    	        		picChangeWhenTab((JTextField)c);
	    	        	}else{
	    	        		System.out.println("Error: See tabbedPane change listener - component found is not a textfield");
	    	        	}
	                    break;
	            case 1: 
	            		c = retrieveComponent(pnlRcv, 1);	//1 = 			txt input picture
	            		d = retrieveComponent(pnlSend, 3); 	//3 = sender	txt input file (needed to get file extension)
	            		e = retrieveComponent(pnlSend, 5);  //5 = sender	txt output path	
	            		f = retrieveComponent(pnlSend, 7);	//7 = sender	txt output filename
	            		g = retrieveComponent(pnlSend, 9);  //9 = sender	txt bits encoded with
	            		h = retrieveComponent(pnlSend, 11);  //11 = sender	txt key
	            		curPanel = pnlRcv;
	            		frame.setTitle("Steganografun - Bob-o-Vision");
	            		GUI.pnlPic.setBackground(Color.decode(GUI.R_PIC_COLOUR));
	    	        	
	            		if (c instanceof JTextField && d instanceof JTextField && e instanceof JTextField && f instanceof JTextField && g instanceof JTextField && h instanceof JTextField){     
	    	        		picChangeWhenTab((JTextField)c);
	    	        		if (curIndex != 0){
	    	        			transferSenderInput(curPanel, curIndex, (JTextField)d, (JTextField)e, (JTextField)f, (JTextField)g, (JTextField)h);	
	    	        		}
	    	        	}else{
	    	        		System.out.println("Error: See tabbedPane change listener - component found is not a textfield");
	    	        	}
	            		break;
	            case 2: 
	            		c = retrieveComponent(pnlAtk, 1);	//1 = txt input picture
	            		d = retrieveComponent(pnlSend, 3); 	//3 = sender	txt input file (needed to get file extension)
	            		e = retrieveComponent(pnlSend, 5);  //5 = sender	txt output path	
	            		f = retrieveComponent(pnlSend, 7);	//7 = sender	txt output filename
	            		g = retrieveComponent(pnlSend, 9);  //9 = sender	txt bits encoded with
	            		curPanel = pnlAtk;
	            		frame.setTitle("Steganografun - Eve-o-Vision");
	            		GUI.pnlPic.setBackground(Color.decode(GUI.A_PIC_COLOUR));
	    	        	
	            		if (c instanceof JTextField && d instanceof JTextField && e instanceof JTextField && f instanceof JTextField && g instanceof JTextField){     
	    	        		picChangeWhenTab((JTextField)c);
	    	        		if (curIndex != 0){
	    	        			transferSenderInput(curPanel, curIndex, (JTextField)d, (JTextField)e, (JTextField)f, (JTextField)g, (JTextField)h);	
	    	        		}
	    	        	}else{
	    	        		System.out.println("Error: See tabbedPane change listener - component found is not a textfield");
	    	        	}
	            		
            			break;
	            case 3: 
	            		c = retrieveComponent(pnlMisc, 1);	//1 = txt input picture
	            		curPanel = pnlMisc;
	            		frame.setTitle("Steganografun - Testing-for-Fun");
	            		GUI.pnlPic.setBackground(Color.decode(GUI.M_PIC_COLOUR));
	    	        	
	            		if (c instanceof JTextField){     
	    	        		picChangeWhenTab((JTextField)c);
	    	        	}else{
	    	        		System.out.println("Error: See tabbedPane change listener - component found is not a textfield");
	    	        	}
	            		
		            	break;
        	}
        }
    });
}
	
public static void picChangeWhenTab(JTextField txtInput){
    try {
		BufferedImage img = ImageIO.read(new File(txtInput.getText()));
		Image scaledImg = img.getScaledInstance((int)(GUI.pnlPic.getWidth()*GUI.PIC_WIDTH_SCALE+0.5), (int)(GUI.pnlPic.getHeight()*GUI.PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
		ImageIcon imgIcon = new ImageIcon(scaledImg);
		GUI.inputPic.setIcon(imgIcon);
		GUI.lblNotFound.setText("");
	} catch (IOException err) {
		// TODO Auto-generated catch block
		GUI.lblNotFound.setText("No preview image available");
		GUI.inputPic.setIcon(null);
//		err.printStackTrace();
	}
}

public static void transferSenderInput(JPanel curPanel, int curIndex, JTextField sndMsgPath, JTextField sndOutputPath, JTextField sndOutputFile, JTextField sndBits, JTextField sndKey){
    
	Component c = retrieveComponent(curPanel, 1); //input file 
	Component d = retrieveComponent(curPanel, 3); //output folder
	Component e = retrieveComponent(curPanel, 5); //output file
	JTextField curInputPath = null;
	JTextField curOutputPath = null;
	JTextField curOutputFile = null;
	
	if (c instanceof JTextField && d instanceof JTextField && e instanceof JTextField){
		curInputPath = (JTextField)c;
		curOutputPath = (JTextField)d;
		curOutputFile = (JTextField)e;

		String sndOutputFileCompletePath = sndOutputPath.getText() + "\\" + sndOutputFile.getText();
		String sndMsgExt = sndMsgPath.getText().substring(sndMsgPath.getText().lastIndexOf(".")+1);
		String curOutputFileNoExt = curOutputFile.getText().substring(0, curOutputFile.getText().lastIndexOf("."));
		
		curInputPath.setText(sndOutputFileCompletePath);
		curOutputPath.setText(sndOutputPath.getText());
		if(sndMsgExt.equals("")){
			curOutputFile.setText(curOutputFileNoExt + ".txt");
		}else{
			curOutputFile.setText(curOutputFileNoExt + "." + sndMsgExt);
		}
		
		
		Component f = null;
		Component g = null;
		JTextField curBits = null;
		JTextField curKey = null;
		switch(curIndex){
			case 1:	//rcv
					f = retrieveComponent(curPanel, 7); //bits to decode with
					g = retrieveComponent(curPanel, 9); //key
					
					if (f instanceof JTextField && g instanceof JTextField){
						curBits = (JTextField)f;
						curKey = (JTextField)g;
						
						curBits.setText(sndBits.getText());
						curKey.setText(sndKey.getText());
					}
					
			case 2: //atk
					f = retrieveComponent(curPanel, 7); //bits to shift
					if (f instanceof JTextField){
						curBits = (JTextField)f;
						curBits.setText(sndBits.getText());
					}
					
				
		}
		
	}else{
		System.out.println("Error in transferSenderInput - components were not JTextFields");
	}
	
}

public static Component retrieveComponent(JPanel pnl, int componentNum){
	Component c = null;
	Component component = pnl.getComponent(0);
	if (component instanceof Container) {
        Container container = (Container)component;
        c = container.getComponent(componentNum);
    }
	return c;
}

}

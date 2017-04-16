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
import javax.swing.JButton;
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
		        			
		        			if (txtBoxesFilled(pnlSend) == true){
		    	        		setBtnSubmit(pnlSend, true);
		    	        	}else{
		    	        		setBtnSubmit(pnlSend, false);
		    	        	}
		                    break;
		            case 1: 
		            		c = retrieveComponent(pnlRcv, 1);	//1 = 			txt input picture
		            		d = retrieveComponent(pnlSend, 4); 	//4 = sender	txt input file (needed to get file extension)
		            		e = retrieveComponent(pnlSend, 6);  //6 = sender	txt output path	
		            		f = retrieveComponent(pnlSend, 8);	//8 = sender	txt output filename
		            		g = retrieveComponent(pnlSend, 10);  //10 = sender	txt bits encoded with
		            		h = retrieveComponent(pnlSend, 12);  //12 = sender	txt key
		            		curPanel = pnlRcv;
		            		frame.setTitle("Steganografun - Bob-o-Vision");
		            		GUI.pnlPic.setBackground(Color.decode(GUI.R_PIC_COLOUR));
		    	        	
		            		if (c instanceof JTextField && d instanceof JTextField && e instanceof JTextField && f instanceof JTextField && g instanceof JTextField && h instanceof JTextField){     
		    	        		if (curIndex != 0){
		    	        			transferSenderInput(curPanel, curIndex, (JTextField)d, (JTextField)e, (JTextField)f, (JTextField)g, (JTextField)h);	
		    	        		}
		    	        		picChangeWhenTab((JTextField)c);
		    	        	}else{
		    	        		System.out.println("Error: See tabbedPane change listener - component found is not a textfield");
		    	        	}
		            		
		            		if (txtBoxesFilled(pnlRcv) == true){
		            			setBtnSubmit(pnlRcv, true);
		    	        	}else{
		    	        		setBtnSubmit(pnlRcv, false);
		    	        	}
		            		break;
		            case 2: 
		            		c = retrieveComponent(pnlAtk, 1);	//1 = txt input picture
		            		d = retrieveComponent(pnlSend, 4); 	//4 = sender	txt input file (needed to get file extension)
		            		e = retrieveComponent(pnlSend, 6);  //6 = sender	txt output path	
		            		f = retrieveComponent(pnlSend, 8);	//8 = sender	txt output filename
		            		g = retrieveComponent(pnlSend, 10);  //10 = sender	txt bits encoded with
		            		curPanel = pnlAtk;
		            		frame.setTitle("Steganografun - Eve-o-Vision");
		            		GUI.pnlPic.setBackground(Color.decode(GUI.A_PIC_COLOUR));

		            		if (c instanceof JTextField && d instanceof JTextField && e instanceof JTextField && f instanceof JTextField && g instanceof JTextField){     
		    	        		if (curIndex != 0){
		    	        			transferSenderInput(curPanel, curIndex, (JTextField)d, (JTextField)e, (JTextField)f, (JTextField)g, (JTextField)h);	
		    	        		}
		    	        		picChangeWhenTab((JTextField)c);
		    	        	}else{
		    	        		System.out.println("Error: See tabbedPane change listener - component found is not a textfield");
		    	        	}
		            		
		    	        	if (txtBoxesFilled(pnlAtk) == true){
		    	        		setBtnSubmit(pnlAtk, true);
		    	        	}else{
		    	        		setBtnSubmit(pnlAtk, false);
		    	        	}
		            		
	            			break;
		            case 3: 
		            		c = retrieveComponent(pnlMisc, 1);	//1 = txt input picture
		            		curPanel = pnlMisc;
		            		frame.setTitle("Steganografun - Testing-for-Fun");
		            		GUI.pnlPic.setBackground(Color.decode(GUI.M_PIC_COLOUR));

		    	        	if (txtBoxesFilled(pnlMisc) == true){
		    	        		setBtnSubmit(pnlMisc, true);
		    	        	}else{
		    	        		setBtnSubmit(pnlMisc, false);
		    	        	}
		            		
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
	
			String sndOutputFileCompletePath = sndOutputPath.getText() + File.separator + sndOutputFile.getText();
			String sndMsgExt = sndMsgPath.getText().substring(sndMsgPath.getText().lastIndexOf(".")+1);
			if (sndMsgPath.getText().lastIndexOf("\\") == -1){ //signature must have been used
				sndMsgExt = "txt";
			}
			
			String curOutputFileNoExt = null;
			if (!curOutputFile.getText().isEmpty()){
				curOutputFileNoExt = curOutputFile.getText().substring(0, curOutputFile.getText().lastIndexOf("."));
			}
			
			curInputPath.setText(sndOutputFileCompletePath);
			curOutputPath.setText(sndOutputPath.getText());
			if(sndMsgExt.equals("") && curIndex == 1){  //for receiver, default to .txt
				curOutputFile.setText(curOutputFileNoExt + ".txt");
			}else if (curIndex == 2){                   //for attacker, set to .PNG
				curOutputFile.setText(curOutputFileNoExt + ".PNG");
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
	
	public static boolean txtBoxesFilled(JPanel outerPnl){
		boolean allFilled = true;

		Component cParamPnl = outerPnl.getComponent(0);
//		cParamPnl.setBackground(Color.blue);
		Container paramPnl = null;
		int componentCount = 0;
		if (cParamPnl instanceof Container){
			paramPnl = (Container)cParamPnl;
			componentCount = paramPnl.getComponents().length;
		}

		Component c;
		JTextField ctxt;
		for (int i = 0; i < componentCount - 1; i++){
			c = paramPnl.getComponent(i);
			if (c instanceof JTextField){
				ctxt = (JTextField)c;
//				System.out.println("i:" + i + " || " + ctxt.getText());
				if(ctxt.getText().isEmpty()){
					allFilled = false;
				}
			}
		}
		
		return allFilled;
	}
	
	public static void setBtnSubmit(JPanel pnl, boolean boolEnabled){
		Component cMode = pnl.getComponent(1);
		Component cPnlSubmit = null;
		Component cBtnSubmit = null;
		JButton btnSubmit = null;
		
		if (cMode instanceof Container) {
	        Container modeContainer = (Container)cMode;
	        cPnlSubmit = modeContainer.getComponent(modeContainer.getComponents().length-1);
//	        cPnlSubmit.setBackground(Color.black);
	        if(cPnlSubmit instanceof Container){
	        	Container submitContainer = (Container)cPnlSubmit;
//	        	cPnlSubmit.setBackground(Color.blue);
	        	cBtnSubmit = submitContainer.getComponent(0);
//	        	cBtnSubmit.setBackground(Color.red);
	        	if(cBtnSubmit instanceof JButton){
	        		btnSubmit = (JButton)cBtnSubmit;
	        		btnSubmit.setEnabled(boolEnabled);
	        	}
	        }
	    }
	}

}

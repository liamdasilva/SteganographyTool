package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import modes.DecodeImage;
import modes.DecryptFile;
import modes.Utilities;

public class PanelReceiver {
	public static JPanel makePnlRcv(JFrame frame){
		JPanel pnlRcv = new JPanel();
		pnlRcv.setLayout(new GridLayout(2,1));
		
		JPanel pnlParams = new JPanel();
		pnlParams.setLayout(new GridBagLayout());
		pnlParams.setBackground(Color.decode(GUI.R_PARAM_COLOUR));
	    pnlParams.setBorder(new TitledBorder("Parameters"));
	    
	    GridBagConstraints c = new GridBagConstraints();
	    c.anchor = GridBagConstraints.NORTH;
	    c.ipadx = 10; //padding reduces overlap with panel borders
	    c.ipady = 10;
	    
	    JButton btnInputPath = new JButton("Select Input .PNG");
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 0;
	    pnlParams.add(btnInputPath, c);
	 
	    JTextField txtInputPath= new JTextField();
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 0;
	    pnlParams.add(txtInputPath, c);
	    
	    JButton btnOutputPath = new JButton("Select Output Folder");
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 1;
	    pnlParams.add(btnOutputPath, c);
	 
	    JTextField txtOutputPath = new JTextField(GUI.outputStorageLocation);
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 1;
	    pnlParams.add(txtOutputPath, c);
	    	    
	    JLabel lblOutputFile = new JLabel("Output Filename");
	    lblOutputFile.setHorizontalAlignment(0);
	    lblOutputFile.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 2;
	    pnlParams.add(lblOutputFile, c);
	 
	    JTextField txtOutputFile = new JTextField("output - receiver.txt");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 2;
	    pnlParams.add(txtOutputFile, c);

	    JLabel lblBits = new JLabel("Bits to Decode");
	    lblBits.setHorizontalAlignment(0);
	    lblBits.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 4;
	    pnlParams.add(lblBits, c);
	 
	    JTextField txtBits = new JTextField("3");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 4;
	    pnlParams.add(txtBits, c);
	    
	    JLabel lblKey= new JLabel("Secret Key");
	    lblKey.setVisible(false);
	    lblKey.setHorizontalAlignment(0);
	    lblKey.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 5;
	    pnlParams.add(lblKey, c);
	 
	    JTextField txtKey= new JTextField("ThisIsAKey");
		txtKey.setVisible(false);
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 5;
	    pnlParams.add(txtKey, c);
	    
	    pnlRcv.add(pnlParams);
	    
	    //===========================================================================
        //start pnlModes section
        //will contain radio buttons for each of the modes that this panel can do
        //===========================================================================
        
        JPanel pnlModes = new JPanel();
        pnlModes.setBackground(Color.decode(GUI.R_MODE_COLOUR));
		pnlModes.setLayout(new GridLayout(4,2));
	    pnlModes.setBorder(new TitledBorder("Mode Selection"));

	    String decodeString = "Decode picture containing message";
	    String decryptString = "Decrypt then decode picture containing message using key";
	    String getSignString = "Decode picture containing a signature";
	    
        JRadioButton rbtnDecode = new JRadioButton(decodeString);
        rbtnDecode.setActionCommand(decodeString);
        rbtnDecode.setSelected(true);
        rbtnDecode.setBackground(Color.decode(GUI.R_RADIO_COLOUR));
        pnlModes.add(rbtnDecode);
        
        JRadioButton rbtnDecrypt = new JRadioButton(decryptString);
        rbtnDecrypt.setBackground(Color.decode(GUI.R_MODE_COLOUR));
        rbtnDecrypt.setActionCommand(decryptString);
        pnlModes.add(rbtnDecrypt);
                
        JRadioButton rbtnGetSign= new JRadioButton(getSignString);
        rbtnGetSign.setBackground(Color.decode(GUI.R_MODE_COLOUR));
        rbtnGetSign.setActionCommand(getSignString);
        pnlModes.add(rbtnGetSign);
        
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnDecode);
        group.add(rbtnDecrypt);
        group.add(rbtnGetSign);
	    
	    JButton btnSubmit = new JButton("Run Program");
	    btnSubmit.setEnabled(false);
	    JPanel pnlButton = new JPanel();
	    pnlButton.setBackground(Color.decode(GUI.R_MODE_COLOUR));
	    pnlButton.add(btnSubmit);
	    pnlModes.add(pnlButton);
	    
	    pnlRcv.add(pnlModes);
	    
	    //===============================================================
	    //start Listeners
	    //contains each of the listeners for buttons and textboxes
	    //===============================================================

	    rbtnDecode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnDecode.setBackground(Color.decode(GUI.R_RADIO_COLOUR));
            	rbtnDecrypt.setBackground(Color.decode(GUI.R_MODE_COLOUR));
            	rbtnGetSign.setBackground(Color.decode(GUI.R_MODE_COLOUR));
            	lblBits.setVisible(true);
            	txtBits.setVisible(true);
            	lblKey.setVisible(false);
            	txtKey.setVisible(false);
            }
        });
	    
	    rbtnDecrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnDecode.setBackground(Color.decode(GUI.R_MODE_COLOUR));
            	rbtnDecrypt.setBackground(Color.decode(GUI.R_RADIO_COLOUR));
            	rbtnGetSign.setBackground(Color.decode(GUI.R_MODE_COLOUR));
            	lblBits.setVisible(true);
            	txtBits.setVisible(true);
            	lblKey.setVisible(true);
            	txtKey.setVisible(true);
            }
        });
	    
	    rbtnGetSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnDecode.setBackground(Color.decode(GUI.R_MODE_COLOUR));
            	rbtnDecrypt.setBackground(Color.decode(GUI.R_MODE_COLOUR));
            	rbtnGetSign.setBackground(Color.decode(GUI.R_RADIO_COLOUR));
            	lblBits.setVisible(false);
            	txtBits.setVisible(false);
            	lblKey.setVisible(false);
            	txtKey.setVisible(false);
            }
        });
	    
	    btnInputPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	UtilitiesGUI.chooseImage(frame, txtInputPath);
            	UtilitiesGUI.refreshPicture(txtInputPath);
            	UtilitiesGUI.decideBtnSubmitEnabled(pnlParams, btnSubmit);
            }
        });
	    
        btnOutputPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	UtilitiesGUI.chooseFolder(frame, txtOutputPath);
            	UtilitiesGUI.decideBtnSubmitEnabled(pnlParams, btnSubmit);
            }
        });
    
        txtInputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
		        UtilitiesGUI.refreshPicture(txtInputPath);
	        	UtilitiesGUI.decideBtnSubmitEnabled(pnlParams, btnSubmit);
	        }
	    });
        
        txtOutputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	UtilitiesGUI.decideBtnSubmitEnabled(pnlParams, btnSubmit);
	        }
	    });
        
        txtOutputFile.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	UtilitiesGUI.decideBtnSubmitEnabled(pnlParams, btnSubmit);
	        }
	    });
        
        txtBits.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	UtilitiesGUI.decideBtnSubmitEnabled(pnlParams, btnSubmit);
	        }
	    });

        txtKey.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	UtilitiesGUI.decideBtnSubmitEnabled(pnlParams, btnSubmit);
	        }
	    });
        
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String outputFilePathComplete = txtOutputPath.getText() + File.separator + txtOutputFile.getText();
            	String outputFileExt = txtOutputFile.getText().substring(txtOutputFile.getText().lastIndexOf(".")+1);
            	String intermedFileExt = txtOutputPath.getText() + File.separator + "(intermediate step) - receiver - decode input." + outputFileExt;
            	
            	if (rbtnDecode.isSelected()){
        			//this string manipulation is done so that the end result is: "[outputFilePath][outputFileName].[inputFileExtension] 
            		try{
            			DecodeImage.main(txtInputPath.getText(), outputFilePathComplete, Integer.parseInt(txtBits.getText()));           			
            			JOptionPane.showMessageDialog(frame,"Image decoding successful! \nPlease view output results in: " + outputFilePathComplete,"Success!",1);
            		}catch(Exception e){
            			JOptionPane.showMessageDialog(frame,"Image decoding unsuccessful. Please try again. \nOutput can be found here: " + outputFilePathComplete,"Failure",0);
            			e.printStackTrace();
            		}            		
            	}else if(rbtnDecrypt.isSelected()){
    				try {
    					DecodeImage.main(txtInputPath.getText(), intermedFileExt, Integer.parseInt(txtBits.getText()));
						DecryptFile.main(intermedFileExt, outputFilePathComplete, txtKey.getText());
						JOptionPane.showMessageDialog(frame,"Image decrypting successful! \nPlease view output results in: " + outputFilePathComplete,"Success!",1);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frame,"Image decrypting unsuccessful. Please try again. \nOutput can be found here: " + outputFilePathComplete,"Failure",0);						
						e.printStackTrace();
					}
            	}else if(rbtnGetSign.isSelected()){
            		String s;
            		try{
            			s = Utilities.getSignature(txtInputPath.getText());
            			if (!s.equals("")){
            				JOptionPane.showMessageDialog(frame,"Success!\nSignature discovered: " + s,"Success!",1);
            			}else{
            				JOptionPane.showMessageDialog(frame,"No signature discovered.","No signature found",1);
            			}
            		}catch(Exception e){
        				JOptionPane.showMessageDialog(frame,"Error encountered while getting signature. Please try again.","Failure!",1);
        				e.printStackTrace();
            		}
            	}
            }
        });	    
	    
        return pnlRcv;
	}
	

}



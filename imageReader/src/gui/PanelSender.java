package gui;

import java.awt.Color;
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

import modes.EncodeImage;
import modes.EncryptFile;
import modes.SignFile;

public class PanelSender {
	public static JPanel makePnlSend(JFrame frame){
		JPanel pnlSend = new JPanel();
		pnlSend.setLayout(new GridLayout(2,1));
		
		JPanel pnlParams = new JPanel();
		pnlParams.setLayout(new GridBagLayout());
		pnlParams.setBackground(Color.decode(GUI.S_PARAM_COLOUR));
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
	    
	    JButton btnMsgPath = new JButton("Select File to Encode");
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 1;
	    pnlParams.add(btnMsgPath, c);
	 
	    JTextField txtMsgPath= new JTextField();
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 1;
	    pnlParams.add(txtMsgPath, c);
	    
	    JButton btnOutputPath = new JButton("Select Output Folder");
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 2;
	    pnlParams.add(btnOutputPath, c);
	 
	    JTextField txtOutputPath = new JTextField(System.getProperty("user.home") + "\\Desktop");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 2;
	    pnlParams.add(txtOutputPath, c);
	    	    
	    JLabel lblOutputFile = new JLabel("Output Picture Filename");
	    lblOutputFile.setHorizontalAlignment(0);
	    lblOutputFile.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 3;
	    pnlParams.add(lblOutputFile, c);
	 
	    JTextField txtOutputFile = new JTextField("output - sender.PNG");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 3;
	    pnlParams.add(txtOutputFile, c);

	    JLabel lblBits = new JLabel("Bits to Encode");
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
	    lblKey.setHorizontalAlignment(0);
	    lblKey.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 5;
	    pnlParams.add(lblKey, c);
	 
	    JTextField txtKey= new JTextField("ThisIsAKey");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 5;
	    pnlParams.add(txtKey, c);
	    
	    pnlSend.add(pnlParams);
	    
	    //===========================================================================
        //start pnlModes section
        //will contain radio buttons for each of the modes that this panel can do
        //===========================================================================
        
        JPanel pnlModes = new JPanel();
        pnlModes.setBackground(Color.decode(GUI.S_MODE_COLOUR));
		pnlModes.setLayout(new GridLayout(4,2));
	    pnlModes.setBorder(new TitledBorder("Mode Selection"));

	    String encodeString = "Encode picture with message";
	    String encryptString = "Encrypt and then encode picture with message using key";
	    String signString = "Encode picture with signature";
	    
	    JRadioButton rbtnEncode = new JRadioButton(encodeString);
	    rbtnEncode.setBackground(Color.decode(GUI.S_RADIO_COLOUR)); 
        rbtnEncode.setActionCommand(encodeString);
        rbtnEncode.setSelected(true);
        pnlModes.add(rbtnEncode);
        
        JRadioButton rbtnEncrypt = new JRadioButton(encryptString);
        rbtnEncrypt.setBackground(Color.decode(GUI.S_MODE_COLOUR));
        rbtnEncrypt.setActionCommand(encryptString);
        pnlModes.add(rbtnEncrypt);
                
        JRadioButton rbtnsign = new JRadioButton(signString);
        rbtnsign.setBackground(Color.decode(GUI.S_MODE_COLOUR));
        rbtnsign.setActionCommand(signString);
        pnlModes.add(rbtnsign);
	    
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnEncode);
        group.add(rbtnEncrypt);
        group.add(rbtnsign);
	    
	    JButton btnSubmit = new JButton("Run Program");
	    btnSubmit.setEnabled(false);
	    JPanel pnlButton = new JPanel();
	    pnlButton.setBackground(Color.decode(GUI.S_MODE_COLOUR));
	    pnlButton.add(btnSubmit);
	    pnlModes.add(pnlButton);
	    
	    pnlSend.add(pnlModes);
	    
	    //===============================================================
	    //start Listeners
	    //contains each of the listeners for buttons and textboxes
	    //===============================================================
	    rbtnEncode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnEncode.setBackground(Color.decode(GUI.S_RADIO_COLOUR));
            	rbtnEncrypt.setBackground(Color.decode(GUI.S_MODE_COLOUR));
            	rbtnsign.setBackground(Color.decode(GUI.S_MODE_COLOUR));
            }
        });
	    
	    rbtnEncrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnEncode.setBackground(Color.decode(GUI.S_MODE_COLOUR));
            	rbtnEncrypt.setBackground(Color.decode(GUI.S_RADIO_COLOUR));
            	rbtnsign.setBackground(Color.decode(GUI.S_MODE_COLOUR));
            }
        });
	    	    
	    rbtnsign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnEncode.setBackground(Color.decode(GUI.S_MODE_COLOUR));
            	rbtnEncrypt.setBackground(Color.decode(GUI.S_MODE_COLOUR));
            	rbtnsign.setBackground(Color.decode(GUI.S_RADIO_COLOUR));
            }
        });
	    	    
	    btnInputPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	UtilitiesGUI.chooseImage(frame, txtInputPath);
            	UtilitiesGUI.refreshPicture(txtInputPath);
            	UtilitiesGUI.decideBtnSubmitEnabled(pnlParams, btnSubmit);
            }
        });
	    
	    btnMsgPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	UtilitiesGUI.chooseFile(frame, txtMsgPath);
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
            	if (rbtnEncode.isSelected()){
            		try{
            			EncodeImage.main(txtInputPath.getText(), txtMsgPath.getText(), txtOutputPath.getText(), txtOutputFile.getText(), Integer.parseInt(txtBits.getText()));           			
            			JOptionPane.showMessageDialog(frame,"Image encoding successful! \nPlease view results in output file location: " + txtOutputPath.getText(),"Success!",1);
            		}catch(Exception e){
            			JOptionPane.showMessageDialog(frame,"Image encoding unsuccessful. Please try again." + txtOutputPath.getText(),"Failure",0);
            			e.printStackTrace();
            		}
            	}else if(rbtnEncrypt.isSelected()){
        			//this string manipulation is done so that the end result is: "[outputFilePath][outputFileName].[inputFileExtension] 
//            		String outputFileNoExt = txtOutputFile.getText().substring(0, txtOutputFile.getText().lastIndexOf("."));
//            		String inputFileNameOnly = txtMsgPath.getText().substring(txtMsgPath.getText().lastIndexOf("\\"), txtMsgPath.getText().lastIndexOf("."));
//        			String inputFileExt = txtMsgPath.getText().substring(txtMsgPath.getText().lastIndexOf(".")+1);
//        			String decodedOutputFile = outputFileNoExt + "." + inputFileExt;
//        			String encryptedFile = txtOutputPath.getText() + inputFileNameOnly + " - AES-encrypted." + inputFileExt;
//        			String decryptedFile = outputFileNoExt + " - AES-decrypted." + inputFileExt;
        			
    				try {
    					EncryptFile.main(txtInputPath.getText(), txtOutputPath.getText(), txtOutputFile.getText(), txtKey.getText());
            			EncodeImage.main(txtInputPath.getText(), txtMsgPath.getText(), txtOutputPath.getText(), txtOutputFile.getText(), Integer.parseInt(txtBits.getText()));
    					JOptionPane.showMessageDialog(frame,"Image encrypting successful! \nPlease view results in output file location: " + txtOutputPath.getText(),"Success!",1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(frame,"Image encrypting unsuccessful. Please try again." + txtOutputPath.getText(),"Failure",0);
						e.printStackTrace();
					}
    				    				
            	}else if(rbtnsign.isSelected()){
            		SignFile.main(txtInputPath.getText(), txtMsgPath.getText(), txtOutputPath.getText(), txtOutputFile.getText(), Integer.parseInt(txtBits.getText()), txtKey.getText());
            	}
            }
        });	    
        
        return pnlSend;
	}

}

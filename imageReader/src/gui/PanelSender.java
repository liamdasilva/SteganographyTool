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
            	JFileChooser fileChooser = new JFileChooser();
            	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            	FileNameExtensionFilter filter = new FileNameExtensionFilter(
            	        "PNG Images", "png");
            	    fileChooser.setFileFilter(filter);
            	int result = fileChooser.showOpenDialog(frame);
            	if (result == JFileChooser.APPROVE_OPTION) {
            	    File selection = fileChooser.getSelectedFile();
            	    txtInputPath.setText(selection.getPath());
            	}
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	    	    try {
	    			BufferedImage img = ImageIO.read(new File(txtInputPath.getText()));
	    			Image scaledImg = img.getScaledInstance((int)(GUI.pnlPic.getWidth()*GUI.PIC_WIDTH_SCALE+0.5), (int)(GUI.pnlPic.getHeight()*GUI.PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
	    			ImageIcon imgIcon = new ImageIcon(scaledImg);
	    			GUI.inputPic.setIcon(imgIcon);
	    			GUI.lblNotFound.setText("");
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			GUI.lblNotFound.setText("No preview image available");
	    			GUI.inputPic.setIcon(null);
	    			
//	    			e.printStackTrace();
	    		}
	            
            }
        });
        
        btnMsgPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	JFileChooser fileChooser = new JFileChooser();
            	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            	int result = fileChooser.showOpenDialog(frame);
            	if (result == JFileChooser.APPROVE_OPTION) {
            	    File selection = fileChooser.getSelectedFile();
            	    txtMsgPath.setText(selection.getPath());
            	}
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
            }
        });
	    
        btnOutputPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	JFileChooser fileChooser = new JFileChooser();
            	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            	int result = fileChooser.showOpenDialog(frame);
            	if (result == JFileChooser.APPROVE_OPTION) {
            	    File selection = fileChooser.getSelectedFile();
            	    txtOutputPath.setText(selection.getPath());
            	}
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
            }
        });
    
        txtInputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	    	    try {
	    			BufferedImage img = ImageIO.read(new File(txtInputPath.getText()));
	    			Image scaledImg = img.getScaledInstance((int)(GUI.pnlPic.getWidth()*GUI.PIC_WIDTH_SCALE+0.5), (int)(GUI.pnlPic.getHeight()*GUI.PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
	    			ImageIcon imgIcon = new ImageIcon(scaledImg);
	    			GUI.inputPic.setIcon(imgIcon);
	    			GUI.lblNotFound.setText("");
	    		} catch (IOException err) {
	    			// TODO Auto-generated catch block
	    			GUI.lblNotFound.setText("No preview image available");
	    			GUI.inputPic.setIcon(null);
//	    			err.printStackTrace();
	    		}
	        }
	    });

        txtMsgPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	        }
	    });
        
        txtOutputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	        }
	    });
        
        txtOutputFile.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
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

    					
    					
//						SecretKey secKey = EncryptFile.main(txtMsgPath.getText(),txtOutputPath.getText(), encryptedFile);
//            			EncodeImage.main(txtInputPath.getText(), txtMsgPath.getText(), txtOutputPath.getText(), txtOutputFile.getText(), Integer.parseInt(txtBits.getText()), txtKey.getText());						
//						DecryptFile.main(txtOutputPath.getText() + "\\" + decodedOutputFile, txtOutputPath.getText(), decryptedFile, secKey);
					} catch (Exception e) {
						// TODO Auto-generated catch block
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

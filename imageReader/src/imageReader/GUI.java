package imageReader;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;

import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;
import javax.swing.JLabel.*;

/**
 * @author m-warren, warr9620@mylaurier.ca
 *
 */

public class GUI extends JPanel{

	public static void main(String[] args) throws IOException{
		createGUI();
	}	    

	static int APP_HEIGHT = 768;
	static int APP_WIDTH = 1024;

     	
	public static void createGUI() {
		
		JFrame frame = new JFrame("Steganografun");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();    
		pane.setLayout(new GridBagLayout());
//		pane.setLayout(new GridLayout(3,1));
		
		
		addPanels(frame, pane);
		
        frame.pack();
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setSize(APP_WIDTH, APP_HEIGHT);

	    frame.setVisible(true);
	}
		
	public static void addPanels(JFrame frame, Container pane){
		//========================================================================================
		//start paramPanel 
		//allows user to input desired .PNG, message to encode, output folder, and output filename
		//========================================================================================
		
		//constants for each of the background colours for each panel
		String PARAM_COLOUR = "#FFCCEE";
		String PIC_COLOUR = "#EEEEFF";
		String MODE_COLOUR = "#EEEEEE";
		String RADIO_COLOUR = "#FFFFFF";
		
		double PIC_WIDTH_SCALE = 0.6;
		double PIC_HEIGHT_SCALE = 0.6;		
		
		GridBagConstraints pane_c = new GridBagConstraints();
		
		JPanel paramPanel = new JPanel();
		paramPanel.setBackground(Color.decode(PARAM_COLOUR));
	    paramPanel.setLayout(new GridBagLayout());
	    paramPanel.setBorder(new TitledBorder("Parameters"));
	    
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
	    paramPanel.add(btnInputPath, c);
	 
//	    JTextField txtInputPath= new JTextField();
	    JTextField txtInputPath= new JTextField("C:\\Users\\Matthew\\Pictures\\alicesrecord.png");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 0;
	    paramPanel.add(txtInputPath, c);
	    
	    JButton btnMsgPath = new JButton("Select Input File to Encode");
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 1;
	    paramPanel.add(btnMsgPath, c);
	 
//	    JTextField txtMsgPath= new JTextField();
	    JTextField txtMsgPath= new JTextField("C:\\Users\\Matthew\\Desktop\\CP373\\CP373-Movie-Review-Matthew-Warren.docx");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 1;
	    paramPanel.add(txtMsgPath, c);
	    
	    JButton btnOutputPath = new JButton("Select Output Folder");
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 2;
	    paramPanel.add(btnOutputPath, c);
	 
//	    JTextField txtOutputPath = new JTextField();
	    JTextField txtOutputPath = new JTextField("C:\\Users\\Matthew\\Desktop");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 2;
	    paramPanel.add(txtOutputPath, c);
	    	    
	    JLabel lblOutputPic = new JLabel("Output Picture Filename");
	    lblOutputPic.setHorizontalAlignment(0);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 3;
	    paramPanel.add(lblOutputPic, c);
	 
	    JTextField txtOutputPic = new JTextField("output.PNG");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 3;
	    paramPanel.add(txtOutputPic, c);
	    
	    pane_c.fill = GridBagConstraints.BOTH;
	    pane_c.gridwidth = 1;
	    pane_c.gridheight = 1;
	    pane_c.weightx = 0.2;
	    pane_c.weighty = 1;
	    pane_c.gridx = 0;
	    pane_c.gridy = 0;
	    pane.add(paramPanel, pane_c);

	    //=============================================================
	    //start picPanel
	    //This will display the input file picture that the user chose
	    //=============================================================

	    JPanel picPanel = new JPanel();
	    picPanel.setBackground(Color.decode(PIC_COLOUR));
	    picPanel.setBorder(new TitledBorder("Picture Preview"));
	    
	    JLabel lblNotFound = new JLabel("No preview image available");
	    picPanel.add(lblNotFound);
	    
	    JLabel inputPic = new JLabel();
	    BufferedImage img;
	    picPanel.add(inputPic);
	    
	    pane_c.fill = GridBagConstraints.BOTH;
	    pane_c.gridheight = 2;
	    pane_c.weightx = 1;
	    pane_c.weighty = 1;
	    pane_c.gridx = 1;
	    pane_c.gridy = 0;
	    pane.add(picPanel, pane_c);
	    
	    //===========================================================================
        //start modePanel section
        //will contain radio buttons for each of the modes that this program can do
        //===========================================================================
        
        JPanel modePanel = new JPanel();
        modePanel.setBackground(Color.decode(MODE_COLOUR));
		modePanel.setLayout(new GridLayout(4,2));
	    modePanel.setBorder(new TitledBorder("Mode Selection"));

	    String encodeString = "Encode picture with message";
	    String encryptString = "Encrypt and then encode picture with message";
	    String watermarkString = "Encode picture with watermark";
	    
	    JRadioButton rbtnEncode = new JRadioButton(encodeString);
	    rbtnEncode.setBackground(Color.decode(RADIO_COLOUR)); 
	    rbtnEncode.setMnemonic(KeyEvent.VK_B);
        rbtnEncode.setActionCommand(encodeString);
        rbtnEncode.setSelected(true);
        modePanel.add(rbtnEncode);
        
        JRadioButton rbtnEncrypt = new JRadioButton(encryptString);
        rbtnEncrypt.setMnemonic(KeyEvent.VK_B);
        rbtnEncrypt.setActionCommand(encryptString);
        modePanel.add(rbtnEncrypt);
        
        JRadioButton rbtnWatermark = new JRadioButton(watermarkString);
        rbtnWatermark.setMnemonic(KeyEvent.VK_B);
        rbtnWatermark.setActionCommand(watermarkString);
        modePanel.add(rbtnWatermark);	    
	    
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnEncode);
        group.add(rbtnEncrypt);
        group.add(rbtnWatermark);
	    
	    JButton btnSubmit = new JButton("Run Program");
    	btnSubmit.setEnabled(false);
	    JPanel panelButton = new JPanel();
	    panelButton.add(btnSubmit);
	    modePanel.add(panelButton);
	    
	    pane_c.fill = GridBagConstraints.BOTH;
	    pane_c.gridwidth = 1;
	    pane_c.gridheight = 1;
	    pane_c.weightx = 0.2;
	    pane_c.weighty = 1;
	    pane_c.gridx = 0;
	    pane_c.gridy = 1;
	    pane.add(modePanel, pane_c);

	    //===============================================================
	    //start Listeners
	    //contains each of the listeners for buttons and textboxes
	    //===============================================================
	    rbtnEncode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnEncode.setBackground(Color.decode(RADIO_COLOUR));
            	rbtnEncrypt.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            	rbtnWatermark.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            }
        });
	    
	    rbtnEncrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnEncode.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            	rbtnEncrypt.setBackground(Color.decode(RADIO_COLOUR));
            	rbtnWatermark.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            }
        });
	    
	    rbtnWatermark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnEncode.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            	rbtnEncrypt.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            	rbtnWatermark.setBackground(Color.decode(RADIO_COLOUR));
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
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	    	    try {
	    			BufferedImage img = ImageIO.read(new File(txtInputPath.getText()));
	    			Image scaledImg = img.getScaledInstance((int)(frame.getWidth()*PIC_WIDTH_SCALE+0.5), (int)(frame.getHeight()*PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
	    			ImageIcon imgIcon = new ImageIcon(scaledImg);
	    			inputPic.setIcon(imgIcon);
	    			lblNotFound.setText("");
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			lblNotFound.setText("No preview image available");
	    			inputPic.setIcon(null);
	    			
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
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
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
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
            }
        });
    
        txtInputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	    	    try {
	    			BufferedImage img = ImageIO.read(new File(txtInputPath.getText()));
	    			Image scaledImg = img.getScaledInstance((int)(frame.getWidth()*PIC_WIDTH_SCALE+0.5), (int)(frame.getHeight()*PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
	    			ImageIcon imgIcon = new ImageIcon(scaledImg);
	    			inputPic.setIcon(imgIcon);
	    			lblNotFound.setText("");
	    		} catch (IOException err) {
	    			// TODO Auto-generated catch block
	    			lblNotFound.setText("No preview image available");
	    			inputPic.setIcon(null);
//	    			err.printStackTrace();
	    		}
	        }
	    });

        txtMsgPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	        }
	    });
        
        txtOutputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	        }
	    });
        
        txtOutputPic.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	            if(txtInputPath.getText().length() == 0 || txtMsgPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
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
            			//this string manipulation is done so that the end result is: "[outputFilePath][outputFileName].[inputFileExtension] 
            			String outputFileNoExt = txtOutputPic.getText().substring(0, txtOutputPic.getText().lastIndexOf("."));
            			String inputFileExt = txtMsgPath.getText().substring(txtMsgPath.getText().lastIndexOf(".")+1);
            			String decodedOutputFile = outputFileNoExt + "." + inputFileExt;

            			//call function to encode + decode image
            			readImage.main(
            					txtInputPath.getText(), 
            					txtMsgPath.getText(), 
            					txtOutputPath.getText() + "\\" + txtOutputPic.getText(), 
            					txtOutputPath.getText() + "\\" + decodedOutputFile );
            			
            			JOptionPane.showMessageDialog(
            					frame, 
            					"Image encoding successful! \nPlease view results in output file location: " + txtOutputPath.getText(), 
            					"Success!", 
            					1);
            		}catch(Exception e){
            			JOptionPane.showMessageDialog(
            					frame, 
            					"Image encoding unsuccessful. Please try again." + txtOutputPath.getText(), 
            					"Failure", 
            					0);
            			e.printStackTrace();
            		}
            	}else if(rbtnEncrypt.isSelected()){
        			//this string manipulation is done so that the end result is: "[outputFilePath][outputFileName].[inputFileExtension] 
            		String outputFileNoExt = txtOutputPic.getText().substring(0, txtOutputPic.getText().lastIndexOf("."));
            		String inputFileNameOnly = txtMsgPath.getText().substring(txtMsgPath.getText().lastIndexOf("\\"), txtMsgPath.getText().lastIndexOf("."));
        			String inputFileExt = txtMsgPath.getText().substring(txtMsgPath.getText().lastIndexOf(".")+1);
        			String decodedOutputFile = outputFileNoExt + "." + inputFileExt;
        			String encryptedFile = txtOutputPath.getText() + inputFileNameOnly + " - AES-encrypted." + inputFileExt;
        			String decryptedFile = outputFileNoExt + " - AES-decrypted." + inputFileExt;
        			
    				try {
						SecretKey secKey = EncryptFile.main(txtMsgPath.getText(),txtOutputPath.getText(), encryptedFile);
						//call function to encode + decode image
            			readImage.main(
            					txtInputPath.getText(), 
            					txtMsgPath.getText(), 
            					txtOutputPath.getText() + "\\" + txtOutputPic.getText(), 
            					txtOutputPath.getText() + "\\" + decodedOutputFile);
						DecryptFile.main(txtOutputPath.getText() + "\\" + decodedOutputFile, txtOutputPath.getText(), decryptedFile, secKey);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
    				
            	}else if(rbtnWatermark.isSelected()){
            		System.out.println("Run watermark code here");
            	}
            }
        });	    
	}            
		
}

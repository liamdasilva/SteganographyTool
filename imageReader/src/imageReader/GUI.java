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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*;
import javax.swing.JLabel.*;

/**
 * @author m-warren, warr9620@mylaurier.ca
 *
 */
		
public class GUI extends JPanel{

	static String PARAM_COLOUR = "#FFCCEE";
	static String PIC_COLOUR = "#EEEEFF";
	static String MODE_COLOUR = "#EEEEEE";
	static String RADIO_COLOUR = "#FFFFFF";
	static double PIC_WIDTH_SCALE = 0.8;
	static double PIC_HEIGHT_SCALE = 0.8;
	static int APP_HEIGHT = 768;
	static int APP_WIDTH = 1024;

	//this has to be initialized first prior to making the panels since the listeners inside depend on this picture panel
	static JPanel pnlPic = new JPanel();
    static JLabel lblNotFound = new JLabel("No preview image available");
    static JLabel inputPic = new JLabel();
    
	public static void main(String[] args) throws IOException{
		createGUI();
	}	    

	public static void createGUI() {
		
		JFrame frame = new JFrame("Steganografun - Alice-o-Vision");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();    
		pane.setLayout(new GridLayout(1,2));
		
		
		addTabbedPane(frame, pane);
		
        frame.pack();
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setSize(APP_WIDTH, APP_HEIGHT);

	    frame.setVisible(true);
	}
		
	public static void addTabbedPane(JFrame frame, Container pane){

		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel pnlSend = makePnlSend(frame);
		tabbedPane.addTab("Sender", null, pnlSend,"Encode or encrypt image for sending");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		JPanel pnlRcv= makePnlRcv(frame);
		tabbedPane.addTab("Receiver", null, pnlRcv,"Decode or decrypt image sent");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
		
		JPanel pnlAtk= makePnlAtk(frame);
		tabbedPane.addTab("Attacker", null, pnlAtk,"Attempt to crack an image's secret message");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
		
		JPanel pnlMisc = makePnlMisc(frame);
		tabbedPane.addTab("Misc.", null, pnlMisc,"Perform other functions for testing");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_4);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
				
		pane.add(tabbedPane);
				
	    pnlPic.setBackground(Color.decode(PIC_COLOUR));
	    pnlPic.setBorder(new TitledBorder("Picture Preview"));
	    pnlPic.add(lblNotFound);
	    BufferedImage img;
	    pnlPic.add(inputPic);
	    
	    pane.add(pnlPic);
		
	    tabbedPane.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	int curIndex = tabbedPane.getSelectedIndex();
	        	Component c = null;
	        	switch (curIndex) {
		        	case 0: c = retrieveComponent(pnlSend);
		        			frame.setTitle("Steganografun - Alice-o-Vision");
		                    break;
		            case 1: c = retrieveComponent(pnlRcv);
		            		frame.setTitle("Steganografun - Bob-o-Vision");
		            		break;
		            case 2: c = retrieveComponent(pnlAtk);
		            		frame.setTitle("Steganografun - Eve-o-Vision");
	            			break;
		            case 3: c = retrieveComponent(pnlMisc);
		            		frame.setTitle("Steganografun - Testing-for-Fun");
			            	break;
	        	}
	        	
	        	if (c instanceof JTextField){     
	        		picChangeWhenTab(curIndex, (JTextField)c);
	        	}else{
	        		System.out.println("Error: See tabbedPane change listener - component found is not a textfield");
	        	}
	        }
	    });
	}
		
	public static void picChangeWhenTab(int curIndex, JTextField txtInput){
	    try {
			BufferedImage img = ImageIO.read(new File(txtInput.getText()));
			Image scaledImg = img.getScaledInstance((int)(pnlPic.getWidth()*PIC_WIDTH_SCALE+0.5), (int)(pnlPic.getHeight()*PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
			ImageIcon imgIcon = new ImageIcon(scaledImg);
			inputPic.setIcon(imgIcon);
			lblNotFound.setText("");
		} catch (IOException err) {
			// TODO Auto-generated catch block
			lblNotFound.setText("No preview image available");
			inputPic.setIcon(null);
//			err.printStackTrace();
		}
		
	}
	
	public static Component retrieveComponent(JPanel pnl){
		Component c = null;
		Component component = pnl.getComponent(0);
    	if (component instanceof Container) {
            Container container = (Container)component;
            c = container.getComponent(1);
        }
    	return c;
	}
	
	public static JPanel makePnlSend(JFrame frame){
		JPanel pnlSend = new JPanel();
		pnlSend.setLayout(new GridLayout(2,1));
		
		JPanel pnlParams = new JPanel();
		pnlParams.setLayout(new GridBagLayout());
		pnlParams.setBackground(Color.decode(PARAM_COLOUR));
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
	 
	    JTextField txtOutputPath = new JTextField();
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 2;
	    pnlParams.add(txtOutputPath, c);
	    	    
	    JLabel lblOutputPic = new JLabel("Output Picture Filename");
	    lblOutputPic.setHorizontalAlignment(0);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 3;
	    pnlParams.add(lblOutputPic, c);
	 
	    JTextField txtOutputPic = new JTextField("output - sender.PNG");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 3;
	    pnlParams.add(txtOutputPic, c);

	    JLabel lblBits = new JLabel("Bits to Encode");
	    lblBits.setHorizontalAlignment(0);
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
        pnlModes.setBackground(Color.decode(MODE_COLOUR));
		pnlModes.setLayout(new GridLayout(4,2));
	    pnlModes.setBorder(new TitledBorder("Mode Selection"));

	    String encodeString = "Encode picture with message";
	    String encryptString = "Encrypt and then encode picture with message using key";
	    String watermarkString = "Encode picture with watermark";
	    
	    JRadioButton rbtnEncode = new JRadioButton(encodeString);
	    rbtnEncode.setBackground(Color.decode(RADIO_COLOUR)); 
        rbtnEncode.setActionCommand(encodeString);
        rbtnEncode.setSelected(true);
        pnlModes.add(rbtnEncode);
        
        JRadioButton rbtnEncrypt = new JRadioButton(encryptString);
        rbtnEncrypt.setActionCommand(encryptString);
        pnlModes.add(rbtnEncrypt);
                
        JRadioButton rbtnWatermark = new JRadioButton(watermarkString);
        rbtnWatermark.setActionCommand(watermarkString);
        pnlModes.add(rbtnWatermark);
	    
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnEncode);
        group.add(rbtnEncrypt);
        group.add(rbtnWatermark);
	    
	    JButton btnSubmit = new JButton("Run Program");
    	btnSubmit.setEnabled(false);
	    JPanel pnlButton = new JPanel();
	    pnlButton.add(btnSubmit);
	    pnlModes.add(pnlButton);
	    
	    pnlSend.add(pnlModes);
	    
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
	    			Image scaledImg = img.getScaledInstance((int)(pnlPic.getWidth()*PIC_WIDTH_SCALE+0.5), (int)(pnlPic.getHeight()*PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
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
	    			Image scaledImg = img.getScaledInstance((int)(pnlPic.getWidth()*PIC_WIDTH_SCALE+0.5), (int)(pnlPic.getHeight()*PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
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
        
        return pnlSend;
	}

	public static JPanel makePnlRcv(JFrame frame){
		JPanel pnlRcv = new JPanel();
		pnlRcv.setLayout(new GridLayout(2,1));
		
		JPanel pnlParams = new JPanel();
		pnlParams.setLayout(new GridBagLayout());
		pnlParams.setBackground(Color.decode(PARAM_COLOUR));
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
	 
	    JTextField txtOutputPath = new JTextField();
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 1;
	    pnlParams.add(txtOutputPath, c);
	    	    
	    JLabel lblOutputPic = new JLabel("Output Picture Filename");
	    lblOutputPic.setHorizontalAlignment(0);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 2;
	    pnlParams.add(lblOutputPic, c);
	 
	    JTextField txtOutputPic = new JTextField("output - receiver.PNG");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 2;
	    pnlParams.add(txtOutputPic, c);

	    JLabel lblBits = new JLabel("Bits to Decode");
	    lblBits.setHorizontalAlignment(0);
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
	    
	    pnlRcv.add(pnlParams);
	    
	    //===========================================================================
        //start pnlModes section
        //will contain radio buttons for each of the modes that this panel can do
        //===========================================================================
        
        JPanel pnlModes = new JPanel();
        pnlModes.setBackground(Color.decode(MODE_COLOUR));
		pnlModes.setLayout(new GridLayout(4,2));
	    pnlModes.setBorder(new TitledBorder("Mode Selection"));

	    String decodeString = "Decode picture containing message";
	    String decryptString = "Decrypt then decode picture containing message using key";
	    
        JRadioButton rbtnDecode = new JRadioButton(decodeString);
        rbtnDecode.setActionCommand(decodeString);
        rbtnDecode.setSelected(true);
        rbtnDecode.setBackground(Color.decode(RADIO_COLOUR));
        pnlModes.add(rbtnDecode);
        
        JRadioButton rbtnDecrypt = new JRadioButton(decryptString);
        rbtnDecrypt.setActionCommand(decryptString);
        pnlModes.add(rbtnDecrypt);
                
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnDecode);
        group.add(rbtnDecrypt);
	    
	    JButton btnSubmit = new JButton("Run Program");
    	btnSubmit.setEnabled(false);
	    JPanel pnlButton = new JPanel();
	    pnlButton.add(btnSubmit);
	    pnlModes.add(pnlButton);
	    
	    pnlRcv.add(pnlModes);
	    
	    //===============================================================
	    //start Listeners
	    //contains each of the listeners for buttons and textboxes
	    //===============================================================

	    rbtnDecode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnDecode.setBackground(Color.decode(RADIO_COLOUR));
            	rbtnDecrypt.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            }
        });
	    
	    rbtnDecrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnDecode.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            	rbtnDecrypt.setBackground(Color.decode(RADIO_COLOUR));
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
	            if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	    	    try {
	    			BufferedImage img = ImageIO.read(new File(txtInputPath.getText()));
	    			Image scaledImg = img.getScaledInstance((int)(pnlPic.getWidth()*PIC_WIDTH_SCALE+0.5), (int)(pnlPic.getHeight()*PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
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
	            if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
            }
        });
    
        txtInputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	            if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	    	    try {
	    			BufferedImage img = ImageIO.read(new File(txtInputPath.getText()));
	    			Image scaledImg = img.getScaledInstance((int)(pnlPic.getWidth()*PIC_WIDTH_SCALE+0.5), (int)(pnlPic.getHeight()*PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
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
        
        txtOutputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	            if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	        }
	    });
        
        txtOutputPic.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	        }
	    });
        
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (rbtnDecode.isSelected()){
            		try{
            			//this string manipulation is done so that the end result is: "[outputFilePath][outputFileName].[inputFileExtension] 

            			//w.i.p.
//            			String outputFileNoExt = txtOutputPic.getText().substring(0, txtOutputPic.getText().lastIndexOf("."));
//            			String inputFileExt = txtMsgPath.getText().substring(txtMsgPath.getText().lastIndexOf(".")+1);
//            			String decodedOutputFile = outputFileNoExt + "." + inputFileExt;
//
//            			//call function to encode + decode image
//            			readImage.main(
//            					txtInputPath.getText(), 
//            					txtMsgPath.getText(), 
//            					txtOutputPath.getText() + "\\" + txtOutputPic.getText(), 
//            					txtOutputPath.getText() + "\\" + decodedOutputFile );
//            			
//            			JOptionPane.showMessageDialog(
//            					frame, 
//            					"Image encoding successful! \nPlease view results in output file location: " + txtOutputPath.getText(), 
//            					"Success!", 
//            					1);
            		}catch(Exception e){
            			JOptionPane.showMessageDialog(
            					frame, 
            					"Image encoding unsuccessful. Please try again." + txtOutputPath.getText(), 
            					"Failure", 
            					0);
            			e.printStackTrace();
            		}
            	}else if(rbtnDecrypt.isSelected()){
        			//this string manipulation is done so that the end result is: "[outputFilePath][outputFileName].[inputFileExtension] 

            		//w.i.p.
//            		String outputFileNoExt = txtOutputPic.getText().substring(0, txtOutputPic.getText().lastIndexOf("."));
//            		String inputFileNameOnly = txtMsgPath.getText().substring(txtMsgPath.getText().lastIndexOf("\\"), txtMsgPath.getText().lastIndexOf("."));
//        			String inputFileExt = txtMsgPath.getText().substring(txtMsgPath.getText().lastIndexOf(".")+1);
//        			String decodedOutputFile = outputFileNoExt + "." + inputFileExt;
//        			String encryptedFile = txtOutputPath.getText() + inputFileNameOnly + " - AES-encrypted." + inputFileExt;
//        			String decryptedFile = outputFileNoExt + " - AES-decrypted." + inputFileExt;
//        			
//    				try {
//						SecretKey secKey = EncryptFile.main(txtMsgPath.getText(),txtOutputPath.getText(), encryptedFile);
//						//call function to encode + decode image
//            			readImage.main(
//            					txtInputPath.getText(), 
//            					txtMsgPath.getText(), 
//            					txtOutputPath.getText() + "\\" + txtOutputPic.getText(), 
//            					txtOutputPath.getText() + "\\" + decodedOutputFile);
//						DecryptFile.main(txtOutputPath.getText() + "\\" + decodedOutputFile, txtOutputPath.getText(), decryptedFile, secKey);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
            	}		    				
            }
        });	    
        return pnlRcv;
	}

	public static JPanel makePnlAtk(JFrame frame){
		JPanel pnlAtk = new JPanel();
		pnlAtk.setLayout(new GridLayout(2,1));
		
		JPanel pnlParams = new JPanel();
		pnlParams.setLayout(new GridBagLayout());
		pnlParams.setBackground(Color.decode(PARAM_COLOUR));
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
	 
	    JTextField txtOutputPath = new JTextField();
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 1;
	    pnlParams.add(txtOutputPath, c);
	    	    
	    JLabel lblOutputPic = new JLabel("Output Picture Filename");
	    lblOutputPic.setHorizontalAlignment(0);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 2;
	    pnlParams.add(lblOutputPic, c);
	 
	    JTextField txtOutputPic = new JTextField("output - attacker.PNG");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 2;
	    pnlParams.add(txtOutputPic, c);

	    JLabel lblBits = new JLabel("Bits to Shift");
	    lblBits.setHorizontalAlignment(0);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 3;
	    pnlParams.add(lblBits, c);
	 
	    JTextField txtBits = new JTextField("3");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 3;
	    pnlParams.add(txtBits, c);
	    
	    pnlAtk.add(pnlParams);
	    
	    //===========================================================================
        //start pnlModes section
        //will contain radio buttons for each of the modes that this panel can do
        //===========================================================================
        
        JPanel pnlModes = new JPanel();
        pnlModes.setBackground(Color.decode(MODE_COLOUR));
		pnlModes.setLayout(new GridLayout(4,2));
	    pnlModes.setBorder(new TitledBorder("Mode Selection"));

	    String statString = "Use statistical analysis";
	    String shiftString = "Use bit shifts to crack a picture";
	    
	    JRadioButton rbtnStat = new JRadioButton(statString);
	    rbtnStat.setBackground(Color.decode(RADIO_COLOUR)); 
        rbtnStat.setActionCommand(statString);
        rbtnStat.setSelected(true);
        pnlModes.add(rbtnStat);
        
        JRadioButton rbtnShift = new JRadioButton(shiftString);
        rbtnShift.setActionCommand(shiftString);
        pnlModes.add(rbtnShift);
        
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnStat);
        group.add(rbtnShift);
	    
	    JButton btnSubmit = new JButton("Run Program");
    	btnSubmit.setEnabled(false);
	    JPanel pnlButton = new JPanel();
	    pnlButton.add(btnSubmit);
	    pnlModes.add(pnlButton);
	    
	    pnlAtk.add(pnlModes);
	    
	    //===============================================================
	    //start Listeners
	    //contains each of the listeners for buttons and textboxes
	    //===============================================================
	    rbtnStat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnStat.setBackground(Color.decode(RADIO_COLOUR));
            	rbtnShift.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            }
        });

	    rbtnShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnStat.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            	rbtnShift.setBackground(Color.decode(RADIO_COLOUR));
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
            	if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	    	    try {
	    			BufferedImage img = ImageIO.read(new File(txtInputPath.getText()));
	    			Image scaledImg = img.getScaledInstance((int)(pnlPic.getWidth()*PIC_WIDTH_SCALE+0.5), (int)(pnlPic.getHeight()*PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
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
	            if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
            }
        });
    
        txtInputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	    	    try {
	    			BufferedImage img = ImageIO.read(new File(txtInputPath.getText()));
	    			Image scaledImg = img.getScaledInstance((int)(pnlPic.getWidth()*PIC_WIDTH_SCALE+0.5), (int)(pnlPic.getHeight()*PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
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
        
        txtOutputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	        }
	    });
        
        txtOutputPic.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputPic.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	        }
	    });
        
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (rbtnStat.isSelected()){
            		
            	}else if(rbtnShift.isSelected()){
            	
            	}	    				
            }
        });	    
        return pnlAtk;
	}
	
	public static JPanel makePnlMisc(JFrame frame){
		JPanel pnlMisc = new JPanel();
		pnlMisc.setLayout(new GridLayout(2,1));
		
		JPanel pnlParams = new JPanel();
		pnlParams.setLayout(new GridBagLayout());
		pnlParams.setBackground(Color.decode(PARAM_COLOUR));
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
	 
	    JTextField txtOutputPath = new JTextField();
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 2;
	    pnlParams.add(txtOutputPath, c);
	    	    
	    JLabel lblOutputPic = new JLabel("Output Picture Filename");
	    lblOutputPic.setHorizontalAlignment(0);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 3;
	    pnlParams.add(lblOutputPic, c);
	 
	    JTextField txtOutputPic = new JTextField("output - misc.PNG");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 3;
	    pnlParams.add(txtOutputPic, c);
	    
	    JLabel lblKey= new JLabel("Secret Key");
	    lblKey.setHorizontalAlignment(0);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridwidth = 1;
	    c.weightx = 0.1;
	    c.gridx = 0;
	    c.gridy = 4;
	    pnlParams.add(lblKey, c);
	 
	    JTextField txtKey= new JTextField("ThisIsAKey");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 4;
	    pnlParams.add(txtKey, c);
	    
	    pnlMisc.add(pnlParams);
	    
	    //===========================================================================
        //start pnlModes section
        //will contain radio buttons for each of the modes that this panel can do
        //===========================================================================
        
        JPanel pnlModes = new JPanel();
        pnlModes.setBackground(Color.decode(MODE_COLOUR));
		pnlModes.setLayout(new GridLayout(4,2));
	    pnlModes.setBorder(new TitledBorder("Mode Selection"));

	    String encodeString = "Encode picture with message";
	    String allBitsString = "Test all possible bit encodings on a picture";
	    
	    JRadioButton rbtnEncode = new JRadioButton(encodeString);
	    rbtnEncode.setBackground(Color.decode(RADIO_COLOUR)); 
        rbtnEncode.setActionCommand(encodeString);
        rbtnEncode.setSelected(true);
        pnlModes.add(rbtnEncode);
        
        JRadioButton rbtnAllBits= new JRadioButton(allBitsString);
        rbtnAllBits.setActionCommand(allBitsString);
        pnlModes.add(rbtnAllBits);
	    
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnEncode);
        group.add(rbtnAllBits);
	    
	    JButton btnSubmit = new JButton("Run Program");
    	btnSubmit.setEnabled(false);
	    JPanel pnlButton = new JPanel();
	    pnlButton.add(btnSubmit);
	    pnlModes.add(pnlButton);
	    
	    pnlMisc.add(pnlModes);
	    
	    //===============================================================
	    //start Listeners
	    //contains each of the listeners for buttons and textboxes
	    //===============================================================
	    rbtnEncode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnEncode.setBackground(Color.decode(RADIO_COLOUR));
            	rbtnAllBits.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            }
        });

	    
	    rbtnAllBits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnEncode.setBackground(UIManager.getColor("Panel.background")); //sets to default background color
            	rbtnAllBits.setBackground(Color.decode(RADIO_COLOUR));
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
	    			Image scaledImg = img.getScaledInstance((int)(pnlPic.getWidth()*PIC_WIDTH_SCALE+0.5), (int)(pnlPic.getHeight()*PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
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
	    			Image scaledImg = img.getScaledInstance((int)(pnlPic.getWidth()*PIC_WIDTH_SCALE+0.5), (int)(pnlPic.getHeight()*PIC_HEIGHT_SCALE+0.5), Image.SCALE_DEFAULT);
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
            	}else if(rbtnAllBits.isSelected()){
    				    				
            	}
            }
        });	    
        return pnlMisc;
	}	
	
}

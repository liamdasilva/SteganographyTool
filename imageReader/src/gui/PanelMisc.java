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

import modes.DecodeImage;
import modes.EncodeImage;
import modes.readImage;

public class PanelMisc {

	public static JPanel makePnlMisc(JFrame frame){
		JPanel pnlMisc = new JPanel();
		pnlMisc.setLayout(new GridLayout(2,1));
		
		JPanel pnlParams = new JPanel();
		pnlParams.setLayout(new GridBagLayout());
		pnlParams.setBackground(Color.decode(GUI.M_PARAM_COLOUR));
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
	 
	    JTextField txtOutputFile = new JTextField("output - misc.PNG");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 3;
	    pnlParams.add(txtOutputFile, c);
	    
	    JLabel lblKey = new JLabel("Secret Key");
	    lblKey.setHorizontalAlignment(0);
	    lblKey.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
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
        pnlModes.setBackground(Color.decode(GUI.M_MODE_COLOUR));
		pnlModes.setLayout(new GridLayout(4,2));
	    pnlModes.setBorder(new TitledBorder("Mode Selection"));

	    String encodeStringM = "Test all bit encodings (1-8) on a picture";
	    String decodeStringM = "Decode each encoded picture in a group (Select \"...1.PNG\" as Input)";
	    
        JRadioButton rbtnEncodeM= new JRadioButton(encodeStringM);
        rbtnEncodeM.setBackground(Color.decode(GUI.M_RADIO_COLOUR));
        rbtnEncodeM.setSelected(true);
        rbtnEncodeM.setActionCommand(encodeStringM);
        pnlModes.add(rbtnEncodeM);
        
	    JRadioButton rbtnDecodeM = new JRadioButton(decodeStringM);
	    rbtnDecodeM.setBackground(Color.decode(GUI.M_MODE_COLOUR)); 
        rbtnDecodeM.setActionCommand(decodeStringM);
        pnlModes.add(rbtnDecodeM);

	    
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnEncodeM);
        group.add(rbtnDecodeM);
	    
	    JButton btnSubmit = new JButton("Run Program");
	    btnSubmit.setEnabled(false);
	    JPanel pnlButton = new JPanel();
	    pnlButton.setBackground(Color.decode(GUI.M_MODE_COLOUR));
	    pnlButton.add(btnSubmit);
	    pnlModes.add(pnlButton);
	    
	    pnlMisc.add(pnlModes);
	    
	    //===============================================================
	    //start Listeners
	    //contains each of the listeners for buttons and textboxes
	    //===============================================================
	    rbtnEncodeM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnEncodeM.setBackground(Color.decode(GUI.M_RADIO_COLOUR));
            	rbtnDecodeM.setBackground(Color.decode(GUI.M_MODE_COLOUR));

            	String outputFileNoExt = txtOutputFile.getText().substring(0, txtOutputFile.getText().lastIndexOf("."));
            	txtOutputFile.setText(outputFileNoExt + ".PNG");
            	
            	btnMsgPath.setVisible(true);
            	txtMsgPath.setVisible(true);
            }
        });
	    
	    rbtnDecodeM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnEncodeM.setBackground(Color.decode(GUI.M_MODE_COLOUR));
            	rbtnDecodeM.setBackground(Color.decode(GUI.M_RADIO_COLOUR));
            	
            	btnMsgPath.setVisible(false);
            	txtMsgPath.setVisible(false);
            	String outputFileNoExt = txtOutputFile.getText().substring(0, txtOutputFile.getText().lastIndexOf("."));
            	txtOutputFile.setText(outputFileNoExt + ".txt");
            	UtilitiesGUI.decideBtnSubmitEnabled(pnlParams, btnSubmit);
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

        txtMsgPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
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
        
        txtKey.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	UtilitiesGUI.decideBtnSubmitEnabled(pnlParams, btnSubmit);
	        }
	    });
        
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String outputFileExt = txtOutputFile.getText().substring(txtOutputFile.getText().lastIndexOf(".")+1);
            	String outputFileNoExt = txtOutputFile.getText().substring(0, txtOutputFile.getText().lastIndexOf("."));
    			String outputFileNumberless = txtOutputPath.getText() + File.separator + outputFileNoExt; 
    			
            	if (rbtnEncodeM.isSelected()){
            		try{
            			for (int i = 1; i <= 8; i++){
            				EncodeImage.main(txtInputPath.getText(), txtMsgPath.getText(), outputFileNumberless + i + ".PNG", i);
            			}	            			
	        			JOptionPane.showMessageDialog(frame,"Image encodings successful! \nPlease view results in output folder: " + txtOutputPath.getText(),"Success!",1);
            		}catch(Exception e){
            			JOptionPane.showMessageDialog(frame,"Image encodings unsuccessful. Please try again. \nOutput can be found here: " + txtOutputPath.getText(),"Failure",0);
            			e.printStackTrace();
            		}
            	}else if(rbtnDecodeM.isSelected()){
            		try{
            			String inputFileNumberless = txtInputPath.getText().substring(0, txtInputPath.getText().lastIndexOf("1"));
            			String inputFileExt = txtInputPath.getText().substring(txtInputPath.getText().lastIndexOf(".")+1);
            			for (int i = 1; i <= 8; i++){
            				DecodeImage.main(inputFileNumberless + i + "." + inputFileExt, outputFileNumberless + i + " - decoded" + "." + outputFileExt, i);
            			}	            			
	        			JOptionPane.showMessageDialog(frame,"Image decodings successful! \nPlease view results in output folder: " + txtOutputPath.getText(),"Success!",1);
            		}catch(Exception e){
            			JOptionPane.showMessageDialog(frame,"Image decodings unsuccessful. Please try again. \nOutput can be found here: " + txtOutputPath.getText(),"Failure",0);
            			e.printStackTrace();
            		}    				
            	}
            }
        });	    
        return pnlMisc;
	}	

}

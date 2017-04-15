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

import modes.Utilities;

public class PanelAttacker {
	public static JPanel makePnlAtk(JFrame frame){
		JPanel pnlAtk = new JPanel();
		pnlAtk.setLayout(new GridLayout(2,1));
		
		JPanel pnlParams = new JPanel();
		pnlParams.setLayout(new GridBagLayout());
		pnlParams.setBackground(Color.decode(GUI.A_PARAM_COLOUR));
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
	 
	    JTextField txtOutputPath = new JTextField(System.getProperty("user.home") + "\\Desktop");
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
	 
	    JTextField txtOutputFile = new JTextField("output - attacker.png");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 2;
	    pnlParams.add(txtOutputFile, c);

	    JLabel lblBits = new JLabel("Bits to Shift/Use");
	    lblBits.setHorizontalAlignment(0);
	    lblBits.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
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
        pnlModes.setBackground(Color.decode(GUI.A_MODE_COLOUR));
		pnlModes.setLayout(new GridLayout(4,2));
	    pnlModes.setBorder(new TitledBorder("Mode Selection"));

	    String shiftString = "Use bit shifts to crack a picture";
	    String leastSigBitString = "Use least significant bit analysis to crack a picture";
	    
	    
	    JRadioButton rbtnShift = new JRadioButton(shiftString);
	    rbtnShift.setBackground(Color.decode(GUI.A_RADIO_COLOUR)); 
        rbtnShift.setActionCommand(shiftString);
        rbtnShift.setSelected(true);
        pnlModes.add(rbtnShift);
        
        JRadioButton rbtnLeastSigBit = new JRadioButton(leastSigBitString);
        rbtnLeastSigBit.setBackground(Color.decode(GUI.A_MODE_COLOUR));
        rbtnLeastSigBit.setActionCommand(shiftString);
        pnlModes.add(rbtnLeastSigBit);
        
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnShift);
        group.add(rbtnLeastSigBit);
	    
	    JButton btnSubmit = new JButton("Run Program");
	    btnSubmit.setEnabled(false);
	    JPanel pnlButton = new JPanel();
	    pnlButton.setBackground(Color.decode(GUI.A_MODE_COLOUR));
	    pnlButton.add(btnSubmit);
	    pnlModes.add(pnlButton);
	    
	    pnlAtk.add(pnlModes);
	    
	    //===============================================================
	    //start Listeners
	    //contains each of the listeners for buttons and textboxes
	    //===============================================================
	    rbtnShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnShift.setBackground(Color.decode(GUI.A_RADIO_COLOUR));
            	rbtnLeastSigBit.setBackground(Color.decode(GUI.A_MODE_COLOUR));
            }
        });

	    rbtnLeastSigBit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnShift.setBackground(Color.decode(GUI.A_MODE_COLOUR));
            	rbtnLeastSigBit.setBackground(Color.decode(GUI.A_RADIO_COLOUR));
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
        
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	boolean success;
            	String outputFilePathComplete = txtOutputPath.getText() + File.separator + txtOutputFile.getText();
            	if (rbtnShift.isSelected()){
            		success = Utilities.shiftBits(txtInputPath.getText(), Integer.parseInt(txtBits.getText()), outputFilePathComplete);
            		if (success){
            			JOptionPane.showMessageDialog(frame,"Image bits shifted successfully! \nPlease view results in output file: " + outputFilePathComplete ,"Success!",1);
            		}else{
						JOptionPane.showMessageDialog(frame,"Image bit shift unsuccessful. Please try again. \nOutput can be found here: " + outputFilePathComplete ,"Failure",0);
            		}
            	}else if(rbtnLeastSigBit.isSelected()){
            		success = Utilities.getLeastSigBits(txtInputPath.getText(), Integer.parseInt(txtBits.getText()), outputFilePathComplete);
            		if (success){
            			JOptionPane.showMessageDialog(frame,"Least significant bit analysis completed successfully! \nPlease view results in output file: " + outputFilePathComplete,"Success!",1);
            		}else{
						JOptionPane.showMessageDialog(frame,"Least significant bit analysis was unsuccessful. Please try again. \nOutput can be found here: " + outputFilePathComplete,"Failure",0);
            		}
            	}	    				
            }
        });	    
        return pnlAtk;
	}
}

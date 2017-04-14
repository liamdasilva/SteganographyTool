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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	 
	    JTextField txtOutputFile = new JTextField("output - attacker.txt");
	    c.fill = GridBagConstraints.BOTH;
	    c.gridwidth = 2;
	    c.weightx = 1.0;
	    c.gridx = 1;
	    c.gridy = 2;
	    pnlParams.add(txtOutputFile, c);

	    JLabel lblBits = new JLabel("Bits to Shift");
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

	    String statString = "Use statistical analysis";
	    String shiftString = "Use bit shifts to crack a picture";
	    
	    JRadioButton rbtnStat = new JRadioButton(statString);
	    rbtnStat.setBackground(Color.decode(GUI.A_RADIO_COLOUR)); 
        rbtnStat.setActionCommand(statString);
        rbtnStat.setSelected(true);
        pnlModes.add(rbtnStat);
        
        JRadioButton rbtnShift = new JRadioButton(shiftString);
        rbtnShift.setBackground(Color.decode(GUI.A_MODE_COLOUR));
        rbtnShift.setActionCommand(shiftString);
        pnlModes.add(rbtnShift);
        
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnStat);
        group.add(rbtnShift);
	    
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
	    rbtnStat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnStat.setBackground(Color.decode(GUI.A_RADIO_COLOUR));
            	rbtnShift.setBackground(Color.decode(GUI.A_MODE_COLOUR));
            }
        });

	    rbtnShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	rbtnStat.setBackground(Color.decode(GUI.A_MODE_COLOUR));
            	rbtnShift.setBackground(Color.decode(GUI.A_RADIO_COLOUR));
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
            	if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
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
	            if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
            }
        });
    
        txtInputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
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
        
        txtOutputPath.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
	                btnSubmit.setEnabled(false);
	            else{
	                btnSubmit.setEnabled(true);
	            }
	        }
	    });
        
        txtOutputFile.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) { //watch for key strokes
	        	if(txtInputPath.getText().length() == 0 || txtOutputPath.getText().length() == 0 || txtOutputFile.getText().length() == 0)
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
}

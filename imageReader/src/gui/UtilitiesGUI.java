package gui;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;



public class UtilitiesGUI {
	
	public static boolean checkTextFieldsToEnableSubmit(JPanel pnlParams){
        
		boolean allFilled = true;
		Component c;
		JTextField ctxt;
		int componentCount = pnlParams.getComponents().length;
		
		for (int i = 0; i < componentCount - 1; i++){
			c = pnlParams.getComponent(i);
			if (c instanceof JTextField){
				ctxt = (JTextField)c;
//				System.out.println("i:" + i + " || " + ctxt.getText());
				if(ctxt.getText().isEmpty() && ctxt.isVisible()){
					allFilled = false;
				}
			}
		}
		
		return allFilled;
	}
	
	public static void decideBtnSubmitEnabled(JPanel pnlParams, JButton btnSubmit){
    	if(UtilitiesGUI.checkTextFieldsToEnableSubmit(pnlParams)){
            btnSubmit.setEnabled(true);
    	}else{
            btnSubmit.setEnabled(false);
        }
	}
	
	public static void refreshPicture(JTextField txtInputPath){
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
//			err.printStackTrace();
		}
	}
	
	public static void chooseFolder(JFrame frame, JTextField txtOutputPath){
		JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	int result = fileChooser.showOpenDialog(frame);
    	if (result == JFileChooser.APPROVE_OPTION) {
    	    File selection = fileChooser.getSelectedFile();
    	    txtOutputPath.setText(selection.getPath());
    	}
	}
	
	public static void chooseFile(JFrame frame, JTextField txtMsgPath){
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    	int result = fileChooser.showOpenDialog(frame);
    	if (result == JFileChooser.APPROVE_OPTION) {
    	    File selection = fileChooser.getSelectedFile();
    	    txtMsgPath.setText(selection.getPath());
    	}
	}
	
	public static void chooseImage(JFrame frame, JTextField txtInputPath){
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
	}
	
}

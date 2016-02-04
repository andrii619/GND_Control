package relink.guiview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class UploadFirmware extends JFrame {
	
	private JPanel contentPanel;
	private JPanel firmwarePanel;
	private JPanel buttonPanel;
	
	private SpringLayout contentLayout;
	
	
	public UploadFirmware()
	{
		super("Upload Firmware");
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(600,600));
		
		contentLayout = new SpringLayout();
		contentPanel = new JPanel(contentLayout);
		JLabel firmwareLabel= new JLabel("Select Firmware Type:");
		
		contentPanel.add(firmwareLabel);
		contentLayout.putConstraint(SpringLayout.WEST, firmwareLabel, 50, SpringLayout.WEST, contentPanel);
		contentLayout.putConstraint(SpringLayout.NORTH, firmwareLabel, 50, SpringLayout.NORTH, contentPanel);
		
		firmwarePanel = new JPanel();
		firmwarePanel.setPreferredSize(new Dimension(300,300));
		firmwarePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		//contentLayout.putConstraint(SpringLayout.WEST, c1, pad, e2, c2);
		
		contentPanel.add(firmwarePanel);
		
		this.add(contentPanel);
		
		
		this.pack();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			UploadFirmware m = new UploadFirmware();
			m.setVisible(true);
	}

}

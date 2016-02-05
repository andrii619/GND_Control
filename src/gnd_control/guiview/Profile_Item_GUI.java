package gnd_control.guiview;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


public class Profile_Item_GUI extends JPanel{
	Profile_Item profile;
	SpringLayout spring = new SpringLayout();
	JLabel l1 = new JLabel("Name:");
	JLabel l2 = new JLabel("<html>Desciption:");
	JLabel l3;
	JButton b = new JButton("CHOOSE");
	JPanel p1 = new JPanel(new BorderLayout());//main
	JPanel p2 = new JPanel(new GridLayout(1,1));
	JPanel p3 = new JPanel(spring);
	
	public Profile_Item_GUI(Profile_Item profile) throws IOException{
		URL img1 = Profile_Item_GUI.this.getClass().getResource("quadicon.png");
		Image g1 = ImageIO.read(img1);
		g1 = g1.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		ImageIcon icon1 = new ImageIcon(g1);
		l3 = new JLabel(icon1);
		p2.setBorder(BorderFactory.createEtchedBorder());
		p2.add(l3);
		l1.setText(l1.getText()+" "+profile.Title);
		spring.putConstraint(SpringLayout.WEST,l1,4,SpringLayout.WEST,p3);
		p3.add(l1);
		l2.setText(l2.getText()+" "+profile.Description);
		spring.putConstraint(SpringLayout.NORTH,l2,16,SpringLayout.NORTH,p3);
		spring.putConstraint(SpringLayout.WEST,l2,4,SpringLayout.WEST,p3);
		spring.putConstraint(SpringLayout.EAST,l2,-4,SpringLayout.WEST,b);
		p3.add(l2);
		spring.putConstraint(SpringLayout.EAST,b,-16,SpringLayout.EAST,p3);
		spring.putConstraint(SpringLayout.NORTH,b,32,SpringLayout.NORTH,p3);
		p3.add(b);
		p1.add(p3,BorderLayout.CENTER);
		p1.add(p2,BorderLayout.WEST);
		add(p1);
		setLayout(new GridLayout(1,1));
		this.setMinimumSize(new Dimension(1000,80));
		this.setMaximumSize(new Dimension(9000,80));
	}
	/*
	public static void main(String[] args){
		JFrame f = new JFrame();
		try {
			f.add(new Profile_Item_GUI(new Profile_Item("Test Drone Title","This is only a test<br>So Chill I am trying stuff out</html>")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/
}

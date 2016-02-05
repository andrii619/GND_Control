package gnd_control.guiview;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;


public class Profile_HUB_GUI extends JPanel{
	SpringLayout spring = new SpringLayout();
	CardLayout mcard = new CardLayout();
	CardLayout card = new CardLayout();
	JPanel p0 = new JPanel(mcard);//The true master
	JPanel p1 = new JPanel(new BorderLayout());
	JPanel p2 = new JPanel(spring);//South
	JPanel p3 = new JPanel(card);//center
	JPanel p4 = new JPanel(new GridLayout(1,1));//Scroll List
	JPanel p5 = new JPanel();//Default no profiles
	JPanel p6 = new JPanel();//Opened A profile
	JPanel p7 = new JPanel();//Create a Profile
	JButton b1 = new JButton("CREATE NEW PROFILE");
	public Profile_HUB_GUI(){//ArrayList<Profile_Item> items)
		JPanel grow = new JPanel();// (items.size(),1)
		grow.setLayout( new BoxLayout(grow, BoxLayout.Y_AXIS));
		try {
			grow.add(new Profile_Item_GUI(new Profile_Item("Test Drone Title","This is only a test<br>So Chill I am trying stuff out</html>")));//(items.get(i))
			grow.add(new Profile_Item_GUI(new Profile_Item("Test Drone Title","This is only a test<br>So Chill I am trying stuff out</html>")));
			grow.add(new Profile_Item_GUI(new Profile_Item("Test Drone Title","This is only a test<br>So Chill I am trying stuff out</html>")));
			grow.add(new Profile_Item_GUI(new Profile_Item("Test Drone Title","This is only a test<br>So Chill I am trying stuff out</html>")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JScrollPane s = new JScrollPane(grow);
		b1.addActionListener(new switchToCreateProfile());
		p4.add(s);
		p2.setPreferredSize(new Dimension(300,64));
		p1.setBorder(BorderFactory.createTitledBorder("Drone Profiles"));
		p2.setBorder(BorderFactory.createRaisedBevelBorder());
		spring.putConstraint(SpringLayout.NORTH,b1,16,SpringLayout.NORTH,p2);
		spring.putConstraint(SpringLayout.EAST,b1,-32,SpringLayout.EAST,p2);
		spring.putConstraint(SpringLayout.WEST,b1,-192,SpringLayout.EAST,p2);
		p2.add(b1);
		p1.add(p2,BorderLayout.SOUTH);
		p3.add(p4,"LIST");
		p5.add(new JLabel("No Profiles Exist"));
		p3.add(p5,"NO LIST");
		p3.add(p6,"OPEN ITEM");
		card.show(p3, "NO LIST");
		p1.add(p3,BorderLayout.CENTER);
		p0.add(p1, "OG");
		p0.add(p7, "CREATE");
		add(p0);
		setLayout(new GridLayout(1,1));
	}
	public class switchToCreateProfile implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mcard.show(p0, "CREATE");	
		}
	}
	//
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.add(new Profile_HUB_GUI());
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

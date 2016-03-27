package gnd_control.guiview.water;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Water_Pseudo_Manual_Control_GUI extends JPanel{
	JPanel p1 = new JPanel(new GridLayout(10,1));//main
	JButton b1 = new JButton("UPDATE DRONE");
	JLabel l1 = new JLabel("YALL");
	JLabel l2 = new JLabel("PITCH");
	JLabel l3 = new JLabel("ROLL");
	JLabel l4 = new JLabel("THRUST");
	Spinner_NUM n1 = new Spinner_NUM();
	Spinner_NUM n2 = new Spinner_NUM();
	Spinner_NUM n3 = new Spinner_NUM();
	Spinner_NUM n4 = new Spinner_NUM();
	Water_Pseudo_Manual_Control_GUI(){
		p1.add(l1);
		p1.add(n1);
		p1.add(l2);
		p1.add(n2);
		p1.add(l3);
		p1.add(n3);
		p1.add(l4);
		p1.add(n4);
		p1.add(b1);
		add(p1);
		this.setMinimumSize(new Dimension(320,360));
		this.setPreferredSize(getMinimumSize());
		this.setSize(getMinimumSize());
		//this.setBorder(BorderFactory.createTitledBorder("Test"));
		p1.setPreferredSize(getMinimumSize());
		
	}
	public static void main(String[] args) {
		Water_Pseudo_Manual_Control_GUI l = new Water_Pseudo_Manual_Control_GUI();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new GridLayout(1,1));
		frame.add(l);
		frame.pack();
		frame.setMinimumSize(new Dimension(500,400));
		frame.setSize(500,680);
		frame.setVisible(true);
	}

}

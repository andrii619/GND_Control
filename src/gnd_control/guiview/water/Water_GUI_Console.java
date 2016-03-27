package gnd_control.guiview.water;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

/**
 * <b>Water_GUI_Console</b> extands JPanel. A panel for underwater drone control.
 */
public class Water_GUI_Console extends JPanel{
	CardLayout card = new CardLayout();
	JPanel pc = new JPanel(card);
	JPanel p0 = new JPanel(new BorderLayout());//God Panel
	SpringLayout spring = new SpringLayout();
	SpringLayout s2 = new SpringLayout();
	JPanel p1 = new JPanel(spring);//Main Panel
	JPanel p3 = new JPanel(s2);
	JPanel p4 = new JPanel();
	JScrollPane p2 = null;
	JButton b1 = new JButton("More Information");
	JButton b2 = new JButton("Minimize");
	JTextArea a1 = new JTextArea();
	
	public Water_GUI_Console(){
		b2.addActionListener(new space());
		setBorder(BorderFactory.createTitledBorder("CONSOLE"));
		((TitledBorder) getBorder()).setTitleJustification(TitledBorder.CENTER);
		p1.setBorder(BorderFactory.createRaisedBevelBorder());
		spring.putConstraint(SpringLayout.NORTH,b1,16,SpringLayout.NORTH,p1);
		spring.putConstraint(SpringLayout.SOUTH,b1,-16,SpringLayout.SOUTH,p1);
		spring.putConstraint(SpringLayout.EAST,b1,-16,SpringLayout.EAST,p1);
		spring.putConstraint(SpringLayout.WEST,b1,-160,SpringLayout.EAST,p1);
		p1.add(b1);
		//a1.setEditable(false);
		p2 = new JScrollPane(a1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spring.putConstraint(SpringLayout.NORTH,p2,16,SpringLayout.NORTH,p1);
		spring.putConstraint(SpringLayout.SOUTH,p2,-16,SpringLayout.SOUTH,p1);
		spring.putConstraint(SpringLayout.WEST,p2,16,SpringLayout.WEST,p1);
		spring.putConstraint(SpringLayout.EAST,p2,-16,SpringLayout.WEST,b1);
		p1.add(p2);
		
		p3.setMinimumSize(new Dimension(300,32));
		p3.setPreferredSize(new Dimension(300,32));
		s2.putConstraint(SpringLayout.WEST,b2,-160,SpringLayout.EAST,p3);
		s2.putConstraint(SpringLayout.EAST,b2,-16,SpringLayout.EAST,p3);
		p3.add(b2);
		pc.add(p1,"SHOW");
		pc.add(p4,"HIDE");
		p0.add(p3,BorderLayout.NORTH);
		p0.add(pc,BorderLayout.CENTER);
		add(p0);
		setLayout(new GridLayout(1,1));
		this.setMinimumSize(new Dimension(300,160));
		this.setSize(getMinimumSize());
		this.setPreferredSize(getMinimumSize());
	}
	public class space implements ActionListener{
		boolean hit = false;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(hit){
				System.out.println("We tried");
				Water_GUI_Console.this.card.show(Water_GUI_Console.this.pc, "SHOW");
				Water_GUI_Console.this.b2.setText("MINIMIZE");
				Water_GUI_Console.this.setMinimumSize(new Dimension(300,160));
				Water_GUI_Console.this.setSize(getMinimumSize());
				Water_GUI_Console.this.setPreferredSize(getMinimumSize());
				Water_GUI_Console.this.revalidate();
				Water_GUI_Console.this.repaint();
				hit = false;
			}
			else{
				System.out.println("We tried");
				Water_GUI_Console.this.card.show(Water_GUI_Console.this.pc, "HIDE");
				Water_GUI_Console.this.b2.setText("MAXIMIZE");
				Water_GUI_Console.this.setMinimumSize(new Dimension(300,56));
				Water_GUI_Console.this.setSize(getMinimumSize());
				Water_GUI_Console.this.setPreferredSize(getMinimumSize());
				Water_GUI_Console.this.revalidate();
				Water_GUI_Console.this.repaint();
				hit = true;
			}
		}	
	}
	public static void main(String[] args){
		Water_GUI_Console l = new Water_GUI_Console();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new GridLayout(1,1));
		frame.add(l);
		frame.pack();
		frame.setMinimumSize(l.getMinimumSize());
		frame.setSize(680,160);
		frame.setVisible(true);
	}
}

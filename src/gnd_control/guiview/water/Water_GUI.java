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
import javax.swing.JToolBar;
import javax.swing.SpringLayout;

/**
 * The main GUI used for underwater control.
 *
 */
public class Water_GUI extends JPanel{
	SpringLayout spring = new SpringLayout();
	
	CardLayout card = new CardLayout();
	JPanel p1 = new JPanel(new BorderLayout(8,0));//main
	JPanel p2 = new JPanel(new GridLayout(2,1));//Un Used[]
	JScrollPane p3 = new LineNumbering();//Center [Text]
	Water_GUI_RSideBar p4 = new Water_GUI_RSideBar();//East Command Past
	JPanel p5 = new JPanel();//West
	JPanel p6 = new Water_GUI_Console();//South
	JPanel p7 = new JPanel(spring);
	JToolBar tool = new JToolBar();
	JButton b1 = new JButton("Load (Import)");
	JButton b2 = new JButton("Save (Export)");
	JButton b3 = new JButton("Compile");
	JButton b4 = new JButton("Run");
	JButton b5 = new JButton(">>");
	JButton b6 = new JButton("<<");
	public Water_GUI(){
		
		b5.addActionListener(new expandRight());
		//p7.setBorder(BorderFactory.createTitledBorder("TEST"));
		p7.setMinimumSize(new Dimension(900,16));
		spring.putConstraint(SpringLayout.WEST,b6,4,SpringLayout.WEST,p7);
		spring.putConstraint(SpringLayout.EAST,b6,64,SpringLayout.WEST,p7);
		p7.add(b6);
		spring.putConstraint(SpringLayout.WEST,b5,-64,SpringLayout.EAST,p7);
		spring.putConstraint(SpringLayout.EAST,b5,-4,SpringLayout.EAST,p7);
		p7.add(b5);
		
		p2.add(tool);
		p2.add(p7);
		tool.setFloatable(false);
		tool.add(b1);
		tool.add(b2);
		tool.add(b3);
		tool.add(b4);
		p1.add(p3, BorderLayout.CENTER);
		p1.add(p6, BorderLayout.SOUTH);
		p1.add(p2,BorderLayout.NORTH);
		p1.add(p4, BorderLayout.EAST);
		p1.add(p5, BorderLayout.WEST);
		//p1.add(tool, BorderLayout.PAGE_START);
		add(p1);
		setLayout(new GridLayout(1,1));
	}
	public class expandLeft implements ActionListener{
		boolean hit = false;
		@Override
		public void actionPerformed(ActionEvent arg0) {
				
		}
	}
	public class expandRight implements ActionListener{
		boolean hit = false;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(hit){
				Water_GUI.this.p4.hidePanel();
				hit = false;
			}
			else{
				Water_GUI.this.p4.showPanel();
				hit = true;
				System.out.println("LOL");
			}
		}
	}
	public static void main(String[] args){
		JFrame f = new JFrame();
		f.add(new Water_GUI());
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

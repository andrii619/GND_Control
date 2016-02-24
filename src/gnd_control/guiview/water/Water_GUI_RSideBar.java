package gnd_control.guiview.water;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;


public class Water_GUI_RSideBar extends JPanel{
	CardLayout card = new CardLayout();
	
	JPanel p2 = new JPanel(new GridLayout(2,1,8,8));//Main Panel
	JPanel p3 = new JPanel();//Top
	JPanel p4 = new JPanel();//Bottom
	JPanel p5 = new JPanel();//Empty on Purpose
	String[] colNames = {"Var","Value"};
	String[][] rowVals  = {{"",""},{"",""}};
	JTable table = new JTable(rowVals,colNames);
	public Water_GUI_RSideBar(){
		setLayout(card);
		p3.setBorder(BorderFactory.createBevelBorder(0));
		p4.setBorder(BorderFactory.createBevelBorder(0));
		p3.add(table);
		p2.add(p3);
		p2.add(p4);
		this.add(p2,"SHOW");
		this.add(p5,"HIDE");
		this.setMinimumSize(new Dimension(280,680));
		this.setSize(getMinimumSize());
		this.setPreferredSize(getSize());
		this.setBorder(BorderFactory.createTitledBorder("TEST"));
	}
	public void showPanel(){
		card.show(this, "SHOW");
		setMinimumSize(new Dimension(280,680));
		setSize(getMinimumSize());
		setPreferredSize(getMinimumSize());
		revalidate();
		repaint();
	}
	public void hidePanel(){
		card.show(this, "HIDE");
		setMinimumSize(new Dimension(0,0));
		setSize(getMinimumSize());
		setPreferredSize(getMinimumSize());
		revalidate();
		repaint();
	}
	public static void main(String[] args){
		Water_GUI_RSideBar l = new Water_GUI_RSideBar();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new GridLayout(1,1));
		frame.add(l);
		frame.pack();
		frame.setMinimumSize(l.getMinimumSize());
		frame.setSize(280,680);
		frame.setVisible(true);
	}
}

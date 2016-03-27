package gnd_control.guiview.water;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Spinner_NUM extends JPanel{
	SpringLayout spring = new SpringLayout();
	JSpinner s = new JSpinner();
	JSlider  l = new JSlider();
	public Spinner_NUM(){
		l.setValue(0);
		s.addChangeListener(new valueChange(true));
		l.addChangeListener(new valueChange(false));
		setLayout(spring);
		spring.putConstraint(SpringLayout.WEST,l,0,SpringLayout.WEST,this);
		spring.putConstraint(SpringLayout.EAST,l,-8,SpringLayout.WEST,s);
		add(l);
		spring.putConstraint(SpringLayout.EAST,s,-4,SpringLayout.EAST,this);
		spring.putConstraint(SpringLayout.WEST,s,-52,SpringLayout.EAST,this);
		add(s);
		this.setPreferredSize(new Dimension(200,30));
		this.setMinimumSize(getPreferredSize());
		this.setSize(getPreferredSize());
	}
	public class valueChange implements ChangeListener{
		boolean spinner = true;
		public valueChange(boolean spinner){
			this.spinner = spinner;
		}
		@Override
		public void stateChanged(ChangeEvent e) {
			if(spinner==true){
				int i = ((Integer)(s.getValue())).intValue();
				if(i<0){
					s.setValue(new Integer(0));
				}
				else if(i>1000){
					s.setValue(new Integer(1000));
				}
				else{
					l.setValue(i/10);
				}
			}
			else{
				int i = l.getValue();
				if(i<0){
					l.setValue(0);
				}
				else if(i>100){
					l.setValue(1000);
				}
				else{
					s.setValue(new Integer(i*10));
				}
			}
		}
	}
	public static void main(String[] args) {
		Spinner_NUM l = new Spinner_NUM();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new GridLayout(1,1));
		frame.add(l);
		frame.pack();
		frame.setMinimumSize(l.getMinimumSize());
		frame.setSize(600,200);
		frame.setMinimumSize(new Dimension(200,60));
		frame.setVisible(true);
	}

}

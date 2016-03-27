package gnd_control.guiview.water;
import java.awt.Color;
 

import java.awt.GridLayout;
import java.beans.PropertyVetoException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
 
 
/**
 * A scroll panel that has line numbering. Extends a JScrollPanel
 *
 */
public class LineNumbering extends JScrollPane{
	private JTextArea jta;
	private JTextArea lines;
 
	public LineNumbering(){
		jta = new JTextArea();
		lines = new JTextArea("1");
 
		lines.setBackground(Color.LIGHT_GRAY);
		lines.setEditable(false);
 
		jta.getDocument().addDocumentListener(new DocumentListener(){
			public String getText(){
				int caretPosition = jta.getDocument().getLength();
				Element root = jta.getDocument().getDefaultRootElement();
				int Dig = (int)Math.log10(root.getElementIndex( caretPosition ) + 1);
				int spot = 0;
				String sub = "";
				String text = "1 " + System.getProperty("line.separator");
				text = addZero(text,Dig);
				for(int i = 2; i < root.getElementIndex( caretPosition ) + 2; i++){
					spot = (int)Math.log10(i);
					sub = addZero(i + " "+System.getProperty("line.separator"),Dig - spot);
					text =text + sub;
				}
				return text;
			}
			public String addZero(String text, int toAdd){
				if(toAdd<1){
					return text;
				}
				else{
					for(int i=0;i<toAdd;i++){
						text = "0" + text;
					}
					return text;
				}
			}
			@Override
			public void changedUpdate(DocumentEvent de) {
				lines.setText(getText());
				refresh();
			}
			
			@Override
			public void insertUpdate(DocumentEvent de) {
				lines.setText(getText());
				refresh();
			}
 
			@Override
			public void removeUpdate(DocumentEvent de) {
				lines.setText(getText());
				refresh();
			}
			public void refresh(){//maybe pass a parent in TODO
				
				LineNumbering.this.revalidate();
				LineNumbering.this.repaint();
			}
		});
		this.getViewport().add(jta);
		this.setRowHeaderView(lines);
		
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setBorder(BorderFactory.createBevelBorder(0));
	}
 
	public static void main(String[] args){
		/*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });*/
		LineNumbering l = new LineNumbering();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new GridLayout(1,1));
		frame.add(l);
		frame.pack();
		frame.setSize(500,500);
		frame.setVisible(true);
	}
}
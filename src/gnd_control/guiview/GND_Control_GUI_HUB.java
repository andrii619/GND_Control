package gnd_control.guiview;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;

import gnd_control.control.Control;
import gnd_control.control.GND_Control;
import gnd_control.guiview.interractive.Interractive_GUI;
import gnd_control.guiview.profile.Profile_HUB_GUI;
import gnd_control.guiview.water.Water_GUI;

/**
 * The main Application class
 *
 */
public class GND_Control_GUI_HUB extends JFrame implements SplashListener{
	//private GND_Control_GUI_HUB hub;
	public Control control = new GND_Control();
	// all sub windows
	private BoardConnect boardConnect;
	private VehicleStatus statusPanel;
	private JPanel googleMap;
	//private MyGoogleMap googleMap;
	
	JPanel main = new JPanel(new BorderLayout());
	JLayeredPane p0 = new JLayeredPane();
	JTabbedPane p1 = new JTabbedPane();
	JPanel p2 = new Profile_HUB_GUI();
	//JPanel p3;
	JPanel p4 = new Water_GUI();
	JPanel p5 = new Interractive_GUI(control);
	JLabel l1 = new JLabel("(C) 2016 Software Engineering Team: Lucas Rivera(laneboy), Andrii Hlyvko(AndriiDSD), Russell Epstein, Jonathan Zelaya, Prerak Mehta, Thomas Ippolito, and Kevin Wu");
	JMenuBar bar = new JMenuBar();
	JMenu m1 = new JMenu("FILE");
	JMenu m2 = new JMenu("SETTINGS");
	JMenu m3 = new JMenu("UPDATES");
	JMenu m4 = new JMenu("ABOUT");
	JMenuItem i1 = new JMenuItem("Ex1");
	JMenuItem i2 = new JMenuItem("Hide Tab Images");
	JButton b1 = new JButton("");
	//SpringLayout spring = new SpringLayout();
	ImageIcon icon5;
	ImageIcon icon6;
	ArrayList<ImageIcon> tablist = new ArrayList<ImageIcon>();
	ArrayList<String> namelist = new ArrayList<String>();
	
	
	
	public GND_Control_GUI_HUB() throws IOException{
		//control = new GND_Control();
		/////////////////////////////
		googleMap=new JPanel();
		//googleMap = new MyGoogleMap(this);
		///////////////////////////
		
		m1.add(i1);
		m2.add(i2);
		i2.addActionListener(new HideTabs());
		bar.add(m1);
		bar.add(m2);
		bar.add(m3);
		bar.add(m4);
		URL img1 = this.getClass().getResource("quadicon.png");
		URL img2 = this.getClass().getResource("routes.png");
		URL img3 = this.getClass().getResource("waves.png");
		URL img4 = this.getClass().getResource("control.png");
		URL img5 = this.getClass().getResource("connect.png");
		URL img6 = this.getClass().getResource("disconnect.png");
		URL img7 = this.getClass().getResource("donate.png");
		URL img8 = this.getClass().getResource("ufirmware.png");
		Image g1 = ImageIO.read(img1);
		Image g2 = ImageIO.read(img2);
		Image g3 = ImageIO.read(img3);
		Image g4 = ImageIO.read(img4);
		Image g5 = ImageIO.read(img5);
		Image g6 = ImageIO.read(img6);
		Image g7 = ImageIO.read(img7);
		Image g8 = ImageIO.read(img8);
		g1 = g1.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		g2 = g2.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		g3 = g3.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		g4 = g4.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		g5 = g5.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		g6 = g6.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		g7 = g7.getScaledInstance(72, 72, Image.SCALE_SMOOTH);
		g8 = g8.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		ImageIcon icon1 = new ImageIcon(g1);
		ImageIcon icon2 = new ImageIcon(g2);
		ImageIcon icon3 = new ImageIcon(g3);
		ImageIcon icon4 = new ImageIcon(g4);
		ImageIcon icon7 = new ImageIcon(g7);
		ImageIcon icon8 = new ImageIcon(g8);
		icon5 = new ImageIcon(g5);
		icon6 = new ImageIcon(g6);
		p1.addTab("", icon1, p2,"Drone Profile");tablist.add(icon1);namelist.add("Drone Profiles");
		p1.addTab("", icon2, googleMap,"Google Maps");tablist.add(icon2);namelist.add("Destination Routes");
		p1.addTab("", icon3, p4,"Whoi Controls");tablist.add(icon3);namelist.add("Water Controls");
		p1.addTab("", icon4, p5,"Drone Controls");tablist.add(icon4);namelist.add("Drone Control");
		p1.addTab("", icon8, null, "Upload Firmware");tablist.add(icon8);namelist.add("Upload Firmware");
		p1.addTab("", null, null, "About/Help");tablist.add(null);namelist.add("Info");
		p1.addTab("", icon7,null,"PLEASE Donate!");tablist.add(icon7);namelist.add("Donate");
		b1.setIcon(icon5);
		
		main.add(bar,BorderLayout.NORTH);
		main.add(p1,BorderLayout.CENTER);
		main.add(l1,BorderLayout.SOUTH);
		this.addComponentListener(new ChangeSize());
		main.setSize(584,460);
	    main.setLocation(0, 0);
	    main.setOpaque(false);
		p0.add(main,1);
		b1.addActionListener(new connect());
		b1.setLocation(main.getSize().width-112, 32);
		b1.setSize(72, 64);
		p0.add(b1,0);
		add(p0);
		
		////////////////////////////////////
		statusPanel=new VehicleStatus(this);
		//p0.add(statusPanel);
		main.add(statusPanel,BorderLayout.EAST);
		this.control.addVehicleListener(statusPanel);
		//this.control.addVehicleListener(googleMap);
		//googleMap=new MyGoogleMap(this);
		///////////////////////////////////
		
		
		this.addWindowStateListener(new windowChange());
		this.setLocation(100, 0);
		this.setSize(1200, 700);
		this.setMinimumSize(this.getSize());
		this.setTitle("GND Control");
		URL imgurl = this.getClass().getResource("gnd.png");
		Image g = ImageIO.read(imgurl);
		g = g.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		this.setIconImage(g);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(false);
		
		boardConnect = new BoardConnect(this);
		
		this.pack();
		
		
	}
	public class windowChange implements WindowListener, WindowStateListener{
		@Override
		public void windowActivated(WindowEvent arg0) {}
		@Override
		public void windowClosed(WindowEvent arg0) {
			control.closeCurrentConnections();
			control.saveCurrentProfile();
			System.exit(0);//TODO save state to file
		}
		@Override
		public void windowClosing(WindowEvent arg0) {}
		@Override
		public void windowDeactivated(WindowEvent arg0) {}
		@Override
		public void windowDeiconified(WindowEvent arg0) {}
		@Override
		public void windowIconified(WindowEvent arg0) {}
		@Override
		public void windowOpened(WindowEvent arg0) {}
		@Override
		public void windowStateChanged(WindowEvent e) {
			int state = e.getNewState();
			if ((state & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
				Dimension D = GND_Control_GUI_HUB.this.getSize();
				GND_Control_GUI_HUB.this.main.setSize(D.width-16, D.height-40);
				GND_Control_GUI_HUB.this.b1.setLocation(main.getSize().width-112, 32);
			}
		}
		
	}
	boolean hit = false;
	public class HideTabs implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(hit){
				GND_Control_GUI_HUB.this.i2.setText("Hide Tab Images");
				((BorderLayout)main.getLayout()).setVgap(0);
				b1.setLocation(main.getSize().width-112, 32);
				for(int i=0;i<p1.getTabCount();i++){
					p1.setIconAt(i, tablist.get(i));
					p1.setTitleAt(i, "");
				}
				hit = false;
			}
			else{
				GND_Control_GUI_HUB.this.i2.setText("Show Tab Images");
				((BorderLayout)main.getLayout()).setVgap(20);
				b1.setLocation(main.getSize().width-73, 0);
				for(int i=0;i<p1.getTabCount();i++){
					p1.setIconAt(i, null);
					p1.setTitleAt(i, namelist.get(i));
				}
				hit = true;
			}
		}
	}
	public class ChangeSize implements ComponentListener{
		@Override
		public void componentHidden(ComponentEvent arg0){}
		@Override
		public void componentMoved(ComponentEvent arg0) {}
		@Override
		public void componentResized(ComponentEvent e) {
			Component c = (Component)e.getSource();
			Dimension D = GND_Control_GUI_HUB.this.getSize();
			GND_Control_GUI_HUB.this.main.setSize(D.width-16, D.height-40);
			if(hit){
				GND_Control_GUI_HUB.this.b1.setLocation(main.getSize().width-73, 0);
			}
			else{
				GND_Control_GUI_HUB.this.b1.setLocation(main.getSize().width-112, 32);
			}
			//System.out.println("width = "+D.width +" height = " + D.height);
		}
		@Override
		public void componentShown(ComponentEvent arg0) {}
	}
	public class connect implements ActionListener{
		boolean connection = false;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(connection){
				GND_Control_GUI_HUB.this.b1.setIcon(icon5);
				connection = false;
			}
			else{
				GND_Control_GUI_HUB.this.b1.setIcon(icon6);
				connection = true;
			}
			if(control.getCurrentVehicle() != null)
			{
				boardConnect.setVisible(true);
				GND_Control_GUI_HUB.this.setVisible(false);
			}
		}
	}
	public void finish_splash()
	{
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		// do splash first
		
		GND_Control_GUI_HUB m=null;
		
		try {
			m = new GND_Control_GUI_HUB();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new LA_Productions(m);
	}
}

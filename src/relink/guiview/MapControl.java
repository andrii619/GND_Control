package relink.guiview;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXMapKit;

public class MapControl extends JFrame {
	
	JXMapKit map;
	JPanel contentPane;
	
	
	public MapControl()
	{
		super("Map Control");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("./copter.png"));
		this.setPreferredSize(new Dimension(500,500));
		
		contentPane = new JPanel();
		map = new JXMapKit();
		map.setDefaultProvider(JXMapKit.DefaultProviders.OpenStreetMaps);
		map.setPreferredSize(new Dimension(500,500));
		contentPane.add(map);
		
		this.setContentPane(contentPane);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		Image m = (new ImageIcon("./copter.png")).getImage();
		this.setIconImage(m);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//JXMapKit m;
		new MapControl();
	}

}

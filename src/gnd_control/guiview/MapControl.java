package gnd_control.guiview;

//import com.google.maps;
//import GeocodingApi;
import java.awt.Dimension;
//import javafx.application.Application;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.TileFactory;

////// AIzaSyBdaICElKSehUsECqpLInkg9f_ufYH1Aco 
/**
 * This class is the GUI that is used for controlling the drone using maps.
 *
 */
public class MapControl extends JFrame {
	
	JXMapKit map;
	JPanel contentPane;
	//GeoApiContext context = new GeoApiContext().setApiKey("AIza...");
	//GeocodingApi m;
	public MapControl()
	{
		super("Map Control");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("./copter.png"));
		this.setPreferredSize(new Dimension(500,500));
		
		contentPane = new JPanel();
		
		
		
		
		
		//map = new JXMapKit();
		//map.setDefaultProvider(JXMapKit.DefaultProviders.OpenStreetMaps);
	//	map.setPreferredSize(new Dimension(500,500));
		//contentPane.add(map);
		//GeoPosition g =new GeoPosition(1.0,1.0,1.0,1.0,1.0,1.0);
		//map.setAddressLocation(new GeoPosition(40.521899, -74.459634));
		//map.setCenterPosition(new GeoPosition(40.521899, -74.459634));
		//map.setAddressLocationShown(true);
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

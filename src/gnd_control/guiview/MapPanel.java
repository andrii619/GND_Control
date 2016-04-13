package gnd_control.guiview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import gnd_control.control.Control;
import gnd_control.model.GPosition;
import gnd_control.model.VehicleStateListener;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

public class MapPanel extends JPanel implements VehicleStateListener, MapComponentInitializedListener  {
	
	private JFXPanel jfxframe;
	private SpringLayout layout;
	
	private JButton zoomInButton;
	private JButton zoomOutButton;
	
	private GoogleMapView mapView;
	private GoogleMap map;
	
	private Marker copterMarker;
	
	private buttonListener listener;
	
	private Control control;
	
	//private LatLong tmp = new LatLong(47.6097, -122.3331);
	private double lat = 47.6097;
	private double lon = -122.3331;
	
	private static final String MAP_FILE = "."+File.separator+"src"+File.separator+"html"+File.separator+"copter.png";
	
	public MapPanel(Control c)
	{
		this.control=c;
		this.layout=new SpringLayout();
		this.setLayout(layout);
		
		this.jfxframe = new JFXPanel();
		jfxframe.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add(jfxframe);
		layout.putConstraint(SpringLayout.NORTH, jfxframe, 50, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, jfxframe, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, jfxframe, -100, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, jfxframe, -100, SpringLayout.EAST, this);
		
		zoomInButton = new JButton("Zoom In");
		zoomOutButton = new JButton("Zoom Out");
		
		this.add(zoomInButton);
		layout.putConstraint(SpringLayout.WEST, zoomInButton, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, zoomInButton, 5, SpringLayout.SOUTH, jfxframe);
		
		
		this.add(zoomOutButton);
		layout.putConstraint(SpringLayout.WEST, zoomOutButton, 10, SpringLayout.EAST, zoomInButton);
		layout.putConstraint(SpringLayout.NORTH, zoomOutButton, 5, SpringLayout.SOUTH, jfxframe);
		
		
		
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				initMap(jfxframe);
			}
			
		});
		listener = new buttonListener();
		zoomInButton.addActionListener(listener);
		zoomOutButton.addActionListener(listener);
		
		
	}
	
	private void initMap(JFXPanel mapPanel)
	{
		Scene scene = createScene();
		mapPanel.setScene(scene);
	}
	private Scene createScene()
	{
		try {
			mapView = new GoogleMapView();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    mapView.addMapInializedListener(this);
	    Scene scene = new Scene(mapView);
	    
	    return scene;
	}

	@Override
	public void armedChanged(boolean armed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flightModeChanged(String mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void locationChange(GPosition position) {
		// TODO Auto-generated method stub
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				map.removeMarker(copterMarker);
				System.out.println("BLAH "+position.getLatitude()+", "+position.getLongtitude());
				lat+=0.00001;
				lon+=0.00001;
				LatLong temp = new LatLong(position.getLatitude(),position.getLongtitude());//new LatLong(lat,lon);
				copterMarker.setPosition(temp);
				map.addMarker(copterMarker);
				//map.setCenter(temp);
				map.centerProperty();
				
			}
			
		});
	}

	@Override
	public void batteryLevelChange(double level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectedChanged(boolean connected) {
		// TODO Auto-generated method stub
		if(connected)
		{
			if(control.getCurrentVehicle()!=null)
			{
				GPosition t=control.getCurrentVehicle().getLocation();
				if(t!=null)
				{
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							map.removeMarker(copterMarker);
							if(t!=null)
							copterMarker.setPosition(new LatLong(t.getLatitude(),t.getLongtitude()));
							map.addMarker(copterMarker);
							map.setCenter(new LatLong(t.getLatitude(),t.getLongtitude()));map.centerProperty();
						}
						
					});
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		JFrame m =new JFrame();
		m.add(new MapPanel(null));
		
		m.setPreferredSize(new Dimension(500,500));
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.pack();
		m.setVisible(true);
		
	}

	@Override
	public void mapInitialized() {
		// TODO Auto-generated method stub
		MapOptions mapOptions = new MapOptions();

	    mapOptions.center(new LatLong(47.6097, -122.3331))
	            .mapType(MapTypeIdEnum.SATELLITE)
	            .overviewMapControl(false)
	            .panControl(false)
	            .rotateControl(false)
	            .scaleControl(false)
	            .streetViewControl(false)
	            .zoomControl(false)
	            .zoom(14);

	    map = mapView.createMap(mapOptions);

	    //Add a marker to the map
	    MarkerOptions markerOptions = new MarkerOptions();
	    URL t = this.getClass().getResource("copter3.png");
	    markerOptions.position( new LatLong(47.6, -122.3) ).icon(t.getPath())//icon("../src/gnd_control/guiview/copter3.png")
	                .visible(Boolean.TRUE)
	                .title("My Marker");

	    copterMarker = new Marker( markerOptions );
	    map.addMarker(copterMarker);map.centerProperty();
	}

	protected class buttonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == zoomInButton)
			{
				Platform.runLater(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						map.setZoom(map.getZoom()+1);
					}
					
				});
			}
			else if(e.getSource() == zoomOutButton)
			{
				Platform.runLater(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						map.setZoom(map.getZoom()-1);
					}
					
				});
			}
		}
		
	}
}

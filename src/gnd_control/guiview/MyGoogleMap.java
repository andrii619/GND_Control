package gnd_control.guiview;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import gnd_control.model.GPosition;
import gnd_control.model.VehicleStateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
 
public class MyGoogleMap extends JPanel implements VehicleStateListener {
	private GND_Control_GUI_HUB hub;
	private JButton zoomInButton, zoomOutButton, setMarkerButton,
		waypointsToVehicleButton, waypointsFromVehicleButton;
	
	private ButtonListener listener;
	
	private SpringLayout layout;
	private JPanel buttonPanel;
	
	private Browser browser;
	private BrowserView browserView;
 
   private static final int MIN_ZOOM = 0;
   private static final int MAX_ZOOM = 21;
   private static final String MAP_FILE = "."+File.separator+"src"+File.separator+"html"+File.separator+"map.html";
 
   /**
    * In map.html file default zoom value is set to 4.
    */
   private static int zoomValue = 4;
   
   public MyGoogleMap(GND_Control_GUI_HUB hub)
   {
	   this.hub=hub;
	   layout=new SpringLayout();
	   this.setLayout(layout);
	   
	   
	   zoomInButton=new JButton("Zoom In");
	   zoomOutButton = new JButton("Zoom Out");
	   setMarkerButton =new JButton("Add Marker");
	   
	   browser = new Browser();
	   browserView = new BrowserView(browser);
	   
	   listener = new ButtonListener();
	   zoomInButton.addActionListener(listener);
	   zoomOutButton.addActionListener(listener);
	   setMarkerButton.addActionListener(listener);
	   
	   //buttonPanel=new JPanel(layout);
	   //buttonPanel.setPreferredSize(new Dimension(300,300));
	   //buttonPanel.setBorder(BorderFactory.createTitledBorder("TEST"));
	   //this.add(setMarkerButton);
	   browserView.setPreferredSize(new Dimension(500,500));
	   browserView.setVisible(true);
	   this.add(browserView);
	   
	   //this.add(browserView);
	   //browserView.setPreferredSize(new Dimension(900,500));
	   
	   
	   browser.loadURL("file:///"+new File(MAP_FILE).getAbsolutePath());
   }
 
   public static void main(String[] args) {
       final Browser browser = new Browser();
       BrowserView browserView = new BrowserView(browser);
 
       JButton zoomInButton = new JButton("Zoom In");
       zoomInButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (zoomValue < MAX_ZOOM) {
                   browser.executeJavaScript("map.setZoom(" + ++zoomValue + ")");
               }
           }
       });
 
       JButton zoomOutButton = new JButton("Zoom Out");
       zoomOutButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (zoomValue > MIN_ZOOM) {
                   browser.executeJavaScript("map.setZoom(" + --zoomValue + ")");
               }
           }
       });
 
       JButton setMarkerButton = new JButton("Set Marker");
       setMarkerButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               browser.executeJavaScript("var myLatlng = new google.maps.LatLng(48.4431727,23.0488126);\n" +
                       "var marker = new google.maps.Marker({\n" +
                       "    position: myLatlng,\n" +
                       "    map: map,\n" +
                       "    title: 'Hello World!'\n" +
                       "});");
           }
       });
 
       JPanel toolBar = new JPanel();
       toolBar.add(zoomInButton);
       toolBar.add(zoomOutButton);
       toolBar.add(setMarkerButton);
 
       JFrame frame = new JFrame("map");
       frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       frame.add(toolBar, BorderLayout.SOUTH);
       frame.add(browserView, BorderLayout.CENTER);
       frame.setSize(900, 500);
       frame.setLocationRelativeTo(null);
       frame.setVisible(true);
       //File m=new File("./src/html/map.html");
       File m =new File(MAP_FILE);
       browser.loadURL("file:///"+m.getAbsolutePath());
      // browser.loadURL("file:///C:/Users/repstein26/git/GND_Control_/src/gnd_control/guiview/map.html");
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
public void locationChange(GPosition p) {
	// TODO Auto-generated method stub
	
}

@Override
public void batteryLevelChange(double value) {
	// TODO Auto-generated method stub
	
}

@Override
public void connectedChanged(boolean connected) {
	// TODO Auto-generated method stub
	
}

protected class ButtonListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==zoomInButton)
		{
			if (zoomValue < MAX_ZOOM) {
                browser.executeJavaScript("map.setZoom(" + ++zoomValue + ")");
		}
		else if(e.getSource()==zoomOutButton)
		{
			if (zoomValue > MIN_ZOOM) {
                browser.executeJavaScript("map.setZoom(" + --zoomValue + ")");
		}
		else if(e.getSource()==setMarkerButton)
		{
			browser.executeJavaScript("var myLatlng = new google.maps.LatLng(48.4431727,23.0488126);\n" +
                    "var marker = new google.maps.Marker({\n" +
                    "    position: myLatlng,\n" +
                    "    map: map,\n" +
                    "    title: 'Hello World!'\n" +
                    "});");
		}
	}
	
}
	}
}



}
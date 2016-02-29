package gnd_control.guiview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;

public class LA_Productions extends JWindow {
	
	private static JProgressBar progressbar;
	private static int counter=0;
	private static final int TIMER_VAL = 50;
	private static final int PROGRESS_MAX=98;
	private static Timer timer;
	
	private JLabel splashLabel;
	private List<BufferedImage> images = new ArrayList<BufferedImage>(3);
	private SplashListener splashlistener;
	//private static ImageIcon l=new ImageIcon("./lucal.png");
	//private static ImageIcon a = new ImageIcon("./andrii.png");
	//private static ImageIcon la = new ImageIcon("la1.png");
	
	public LA_Productions(SplashListener t)
	{
		splashlistener = t;
		try {
			for(int i=0; i<99;i++)
			{
				images.add(ImageIO.read(new File("./src/gnd_control/guiview/splashscreen/backsplashl"+i+".png")));
			}
			//images.add(ImageIO.read(new File("./src/gnd_control/guiview/splashscreen/backsplashl0.png")));
			//images.add(ImageIO.read(new File("./src/gnd_control/guiview/splashscreen/backsplashl1.png")));
			//images.add(ImageIO.read(new File("./src/gnd_control/guiview/splashscreen/backsplashl2.png")));
			//images.add(ImageIO.read(new File("./splashscreen/backsplashl0.png")));
			//images.add(ImageIO.read(new File("./andrii.png")));
			//images.add(ImageIO.read(new File("./la1.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JPanel contentPane = new JPanel(new BorderLayout());
		splashLabel = new JLabel(new ImageIcon(images.get(0)));
		//JLabel splashLabel = new JLabel(l);
		contentPane.add(splashLabel);
		
		//progressbar = new JProgressBar();
		//progressbar.setOrientation(JProgressBar.HORIZONTAL);
		//progressbar.setMaximum(PROGRESS_MAX);
		//progressbar.setBorder(BorderFactory.createLineBorder(Color.black));
		//progressbar.setForeground(Color.green);
		//contentPane.add(progressbar, BorderLayout.SOUTH);
		
		this.setContentPane(contentPane);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		
		
		startProgressBar();
		
	}

	private void startProgressBar() {
		// TODO Auto-generated method stub
		timer = new Timer(TIMER_VAL, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//progressbar.setValue(counter);
				if(counter == PROGRESS_MAX)
				{
					splashlistener.finish_splash();
					LA_Productions.this.dispose();
					timer.stop();
					//System.exit(0);
				}
				//if(counter >30 && counter <60)
				//{
				splashLabel.setIcon(new ImageIcon(images.get(counter)));
				//}
				//if(counter >=60)
				//{
				//	splashLabel.setIcon(new ImageIcon(images.get(2)));
			//	}
				counter++;
			}
			
		});
		timer.start();
	}
	
	//public static void main(String[] args)
	//{
	//	new LA_Productions();
	//}
}

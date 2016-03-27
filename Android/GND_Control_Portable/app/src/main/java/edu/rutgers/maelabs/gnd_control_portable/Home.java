package edu.rutgers.maelabs.gnd_control_portable;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ScaleDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
//[Activity (Label = "@string/app_name", MainLauncher = true, Icon="@drawable/ic_launcher")]
public class Home extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Resources res = getResources();
        TabHost tabHost = getTabHost();

        // Profiles tab
        ScaleDrawable sd = new ScaleDrawable(res.getDrawable(R.drawable.quadiconlow), 0, 10f, 10f);

        Intent intentWindows = new Intent().setClass(this, Profile.class);
        TabHost.TabSpec tabSpecWindows = tabHost
                .newTabSpec("Windows")
                .setIndicator("", sd.getDrawable())
                .setContent(intentWindows);
        ScaleDrawable sd2 = new ScaleDrawable(res.getDrawable(R.drawable.routeslow), 0, 10f, 10f);
        Intent intentWindows2 = new Intent().setClass(this, Profile.class);
        TabHost.TabSpec tabSpecWindows2 = tabHost
                .newTabSpec("Windows")
                .setIndicator("", sd2.getDrawable())
                .setContent(intentWindows);
        ScaleDrawable sd3 = new ScaleDrawable(res.getDrawable(R.drawable.waveslow), 0, 10f, 10f);
        Intent intentWindows3 = new Intent().setClass(this, Profile.class);
        TabHost.TabSpec tabSpecWindows3 = tabHost
                .newTabSpec("Windows")
                .setIndicator("", sd3.getDrawable())
                .setContent(intentWindows);
        ScaleDrawable sd4 = new ScaleDrawable(res.getDrawable(R.drawable.controllow), 0, 10f, 10f);
        Intent intentWindows4 = new Intent().setClass(this, Profile.class);
        TabHost.TabSpec tabSpecWindows4 = tabHost
                .newTabSpec("Windows")
                .setIndicator("", sd4.getDrawable())
                .setContent(intentWindows);
        /*ScaleDrawable sd5 = new ScaleDrawable(res.getDrawable(R.drawable.aboutlow), 0, 10f, 10f);
        Intent intentWindows5 = new Intent().setClass(this, Profile.class);
        TabHost.TabSpec tabSpecWindows5 = tabHost
                .newTabSpec("Windows")
                .setIndicator("", sd5.getDrawable())
                .setContent(intentWindows);
        ScaleDrawable sd6 = new ScaleDrawable(res.getDrawable(R.drawable.donatelow), 0, 10f, 10f);
        Intent intentWindows6 = new Intent().setClass(this, Profile.class);
        TabHost.TabSpec tabSpecWindows6 = tabHost
                .newTabSpec("Windows")
                .setIndicator("", sd4.getDrawable())
                .setContent(intentWindows);*/
        // add all tabs
        tabHost.addTab(tabSpecWindows);
        tabHost.addTab(tabSpecWindows2);
        tabHost.addTab(tabSpecWindows3);
        tabHost.addTab(tabSpecWindows4);
        //tabHost.addTab(tabSpecWindows5);
        //tabHost.addTab(tabSpecWindows6);
    }
}

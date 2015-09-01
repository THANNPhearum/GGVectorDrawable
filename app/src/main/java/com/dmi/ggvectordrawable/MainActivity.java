package com.dmi.ggvectordrawable;

import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final int W = 0;
    private final int H = 1;
    @Bind(R.id.img_mdpi)
    ImageView imgMdpi;
    @Bind(R.id.img_hdpi)
    ImageView imgHdpi;
    @Bind(R.id.img_xhdpi)
    ImageView imgXhdpi;
    @Bind(R.id.img_xxhdpi)
    ImageView imgXxhdpi;


    @Bind(R.id.txt_info_mdpi)
    TextView txtInfoMdpi;
    @Bind(R.id.txt_info_hdpi)
    TextView txtInfohdpi;
    @Bind(R.id.txt_info_xhdpi)
    TextView txtInfoXhdpi;
    @Bind(R.id.txt_info_xxhdpi)
    TextView txtInfoXxhdpi;
    @Bind(R.id.txt_info_devices)
    TextView txtInfoDevices;


    private Menu m;
    private int pixel;
    private String density;
    private String model;
    private int[] screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        model = Util.getDeviceName();
        pixel = getResources().getDisplayMetrics().densityDpi;
        density = Util.getDensityName(this);
        screen = Util.getResolution(getWindowManager());

        imgMdpi.setImageResource(R.drawable.ic_person);
        imgHdpi.setImageResource(R.drawable.ic_laptop);
        imgXhdpi.setImageResource(R.drawable.ic_gamepad);
        imgXxhdpi.setImageResource(R.drawable.ic_cake);
        updateInfo();
    }


    private void updateInfo() {
        txtInfoMdpi.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtInfoDevices.setText(String.format("Model: %s\nResolution: %dx%d\nDensity: %s, %d dpi", model, screen[W], screen[H], density, pixel));
                txtInfoMdpi.setText(String.format("Size: %dx%d px", imgMdpi.getWidth(), imgMdpi.getHeight()));
                txtInfohdpi.setText(String.format("Size: %dx%d px", imgHdpi.getWidth(), imgHdpi.getHeight()));
                txtInfoXhdpi.setText(String.format("Size: %dx%d px", imgXhdpi.getWidth(), imgXhdpi.getHeight()));
                txtInfoXxhdpi.setText(String.format("Size: %dx%d px", imgXxhdpi.getWidth(), imgXxhdpi.getHeight()));

            }
        }, 1000L);
    }

}

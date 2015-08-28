package com.dmi.ggvectordrawable;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.btn_vector_small)
    Button btnSmall;
    @Bind(R.id.btn_vector_medium)
    Button btnMedium;
    @Bind(R.id.btn_vector_large)
    Button btnLarge;
    @Bind(R.id.btn_vector_extra_large)
    Button btnExtraLarge;

    @Bind(R.id.img_mic)
    ImageView imgMic;
    @Bind(R.id.img_album)
    ImageView imgAlbum;
    @Bind(R.id.img_radio)
    ImageView imgRadio;
    @Bind(R.id.img_vcam)
    ImageView imgCam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnSmall.setOnClickListener(this);
        btnMedium.setOnClickListener(this);
        btnLarge.setOnClickListener(this);
        btnExtraLarge.setOnClickListener(this);
    }

    private int getDimenSize(int id) {
        return (int) (getResources().getDimension(id) / getResources().getDisplayMetrics().density);
    }


    private void resize(final ImageView imageView, final int size) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.getLayoutParams().width = getDimenSize(size);
                imageView.getLayoutParams().height = getDimenSize(size);
                imageView.requestLayout();
            }
        });
    }

    private void colorFilter(final ImageView imageView, final int color) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int highlightColor = getResources().getColor(color);
                ColorFilter filter = new PorterDuffColorFilter(highlightColor, PorterDuff.Mode.SRC_ATOP);
                imageView.setColorFilter(filter);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_vector_small:

                resize(imgMic, R.dimen.ic_size_small);
                resize(imgAlbum, R.dimen.ic_size_small);
                resize(imgRadio, R.dimen.ic_size_small);
                resize(imgCam, R.dimen.ic_size_small);
                //color
                colorFilter(imgMic, R.color.color_red);
                colorFilter(imgAlbum, R.color.color_red);
                colorFilter(imgRadio, R.color.color_red);
                colorFilter(imgCam, R.color.color_red);
                break;
            case R.id.btn_vector_medium:
                resize(imgMic, R.dimen.ic_size_medium);
                resize(imgAlbum, R.dimen.ic_size_medium);
                resize(imgRadio, R.dimen.ic_size_medium);
                resize(imgCam, R.dimen.ic_size_medium);
                //color
                colorFilter(imgMic, R.color.color_pink);
                colorFilter(imgAlbum, R.color.color_pink);
                colorFilter(imgRadio, R.color.color_pink);
                colorFilter(imgCam, R.color.color_pink);
                break;
            case R.id.btn_vector_large:
                resize(imgMic, R.dimen.ic_size_large);
                resize(imgAlbum, R.dimen.ic_size_large);
                resize(imgRadio, R.dimen.ic_size_large);
                resize(imgCam, R.dimen.ic_size_large);
                //color
                colorFilter(imgMic, R.color.color_puple);
                colorFilter(imgAlbum, R.color.color_puple);
                colorFilter(imgRadio, R.color.color_puple);
                colorFilter(imgCam, R.color.color_puple);
                break;
            case R.id.btn_vector_extra_large:
                resize(imgMic, R.dimen.ic_size_extra_large);
                resize(imgAlbum, R.dimen.ic_size_extra_large);
                resize(imgRadio, R.dimen.ic_size_extra_large);
                resize(imgCam, R.dimen.ic_size_extra_large);
                //color
                colorFilter(imgMic, R.color.color_brown);
                colorFilter(imgAlbum, R.color.color_brown);
                colorFilter(imgRadio, R.color.color_brown);
                colorFilter(imgCam, R.color.color_brown);
                break;
        }
    }
}

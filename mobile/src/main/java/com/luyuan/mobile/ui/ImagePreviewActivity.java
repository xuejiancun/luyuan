package com.luyuan.mobile.ui;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.luyuan.mobile.R;


public class ImagePreviewActivity extends Activity {

    private static final int DELAY_MILLIS = 1000;
    private String filePath = "";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.image_preview_activity, null);
        imageView = (ImageView) view.findViewById(R.id.imageview_image_preview);

        if (getIntent() != null && getIntent().getStringExtra("filePath") != null) {
            filePath = getIntent().getStringExtra("filePath");
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        imageView.setImageBitmap(BitmapFactory.decodeFile(filePath, options));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setContentView(view);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation.setDuration(DELAY_MILLIS);
        view.startAnimation(alphaAnimation);

    }

}

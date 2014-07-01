package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.luyuan.mobile.R;

public class UploadMaterialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_upload_material);

        setContentView(R.layout.activity_upload_material);

        rePlaceTabContent();
    }

    private void rePlaceTabContent() {
        UploadMaterialFragment uploadMaterialFragment = new UploadMaterialFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_content_upload_material, uploadMaterialFragment);
        fragmentTransaction.commit();
    }

}

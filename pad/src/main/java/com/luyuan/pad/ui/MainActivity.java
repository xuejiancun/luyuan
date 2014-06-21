package com.luyuan.pad.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.pad.MainApplication;
import com.luyuan.pad.R;
import com.luyuan.pad.model.VersionData;
import com.luyuan.pad.util.GlobalConstantValues;
import com.luyuan.pad.util.GsonRequest;
import com.luyuan.pad.util.RequestManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    private int seletedIndex;
    private ProgressDialog mProgressDialog;

    private ArrayList<LinearLayout> tabLayoutList = new ArrayList<LinearLayout>();
    private ArrayList<TextView> tabTextViewList = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);

        initTab();

        if (MainApplication.REMEMBER_IF_NEED_UPDATE) {
            checkNewVersion();
        }

        if (getIntent() != null && getIntent().getStringExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN) != null) {
            String param = getIntent().getStringExtra(GlobalConstantValues.INTENT_HOME_TO_MAIN);

            if (param.equals(GlobalConstantValues.TAB_POPULAR_CAR)) {
                clickPopularTab();
            } else if (param.equals(GlobalConstantValues.TAB_PRODUCT_APPRECIATE)) {
                clickProductTab();
            } else if (param.equals(GlobalConstantValues.TAB_TECH_EMBODIED)) {
                clickTechTab();
            } else if (param.equals(GlobalConstantValues.TAB_LUYUAN_CULTURE)) {
                clickLuyuanTab();
            }
        } else {
            Dialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.app_param_error)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        rePlaceTabContentForSearch(GlobalConstantValues.API_SEARCH_DATA + "&query=" + query);
        changeTabBackStyle(3);

        return true;
    }

    private void initTab() {
        LinearLayout home_layout = (LinearLayout) findViewById(R.id.layout_home);
        LinearLayout popular_layout = (LinearLayout) findViewById(R.id.layout_popular);
        LinearLayout product_layout = (LinearLayout) findViewById(R.id.layout_product);
        LinearLayout tech_layout = (LinearLayout) findViewById(R.id.layout_tech);
        LinearLayout luyuan_layout = (LinearLayout) findViewById(R.id.layout_luyuan);

        home_layout.setOnClickListener(this);
        popular_layout.setOnClickListener(this);
        product_layout.setOnClickListener(this);
        tech_layout.setOnClickListener(this);
        luyuan_layout.setOnClickListener(this);

        // do not change the order
        tabLayoutList.add(home_layout);
        tabLayoutList.add(popular_layout);
        tabLayoutList.add(product_layout);
        tabLayoutList.add(tech_layout);
        tabLayoutList.add(luyuan_layout);

        TextView home_textview = (TextView) findViewById(R.id.textview_goto_home);
        TextView popular_textview = (TextView) findViewById(R.id.textview_goto_popular);
        TextView product_textview = (TextView) findViewById(R.id.textview_goto_product);
        TextView tech_textview = (TextView) findViewById(R.id.textview_goto_tech);
        TextView luyuan_textview = (TextView) findViewById(R.id.textview_goto_luyuan);

        // do not change the order
        tabTextViewList.add(home_textview);
        tabTextViewList.add(popular_textview);
        tabTextViewList.add(product_textview);
        tabTextViewList.add(tech_textview);
        tabTextViewList.add(luyuan_textview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_home:
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_popular:
                if (seletedIndex != 2) {
                    clickPopularTab();
                }
                break;
            case R.id.layout_product:
                if (seletedIndex != 3) {
                    clickProductTab();
                }
                break;
            case R.id.layout_tech:
                clickTechTab();
                break;
            case R.id.layout_luyuan:
                if (seletedIndex != 5) {
                    clickLuyuanTab();
                }
                break;
        }
    }

    private void clickPopularTab() {
        rePlaceTabContentForSlide(GlobalConstantValues.API_POPULAR_CAR);
        changeTabBackStyle(2);
    }

    private void clickProductTab() {
        ProductHomeFragment productHomeFragment = new ProductHomeFragment();
        rePlaceTabContent(productHomeFragment);
        changeTabBackStyle(3);
    }

    private void clickTechTab() {
        TechMainFragment techMainFragment = new TechMainFragment();
        rePlaceTabContent(techMainFragment);
        changeTabBackStyle(4);
    }

    private void clickLuyuanTab() {
        LuyuanMainFragment luyuanMainFragment = new LuyuanMainFragment();
        rePlaceTabContent(luyuanMainFragment);
        changeTabBackStyle(5);
    }

    private void rePlaceTabContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void rePlaceTabContentForSearch(String api) {
        ProductMainFragment productMainFragment = new ProductMainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_API_URL, api);
        args.putString(GlobalConstantValues.PARAM_CAR_TYPE, GlobalConstantValues.TAB_QUERY_CAR);
        productMainFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, productMainFragment);
        fragmentTransaction.commit();
    }

    private void rePlaceTabContentForSlide(String api) {
        ImagePagerFragment imagePagerFragment = new ImagePagerFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_API_URL, api);
        imagePagerFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, imagePagerFragment);
        fragmentTransaction.commit();
    }

    private void changeTabBackStyle(int index) {
        seletedIndex = index;
        for (int i = 0; i < tabLayoutList.size(); i++) {
            if (i == index - 1) {
                tabLayoutList.get(i).setSelected(true);
                tabTextViewList.get(i).setTextColor(Color.parseColor(GlobalConstantValues.COLOR_BOTTOM_TAB_SELECTED));
            } else {
                tabLayoutList.get(i).setSelected(false);
                tabTextViewList.get(i).setTextColor(Color.parseColor(GlobalConstantValues.COLOR_BOTTOM_TAB_UNSELECTED));
            }
        }
    }

    public boolean checkNewVersion() {
        final boolean result = false;

        GsonRequest gsonObjRequest = new GsonRequest<VersionData>(Request.Method.GET, GlobalConstantValues.API_CHECK_VERSION,
                VersionData.class, new Response.Listener<VersionData>() {
            @Override
            public void onResponse(final VersionData response) {
                if (response != null && response.getSuccess().equals("true")) {
                    try {
                        PackageInfo packageInfo = getPackageManager().getPackageInfo("com.luyuan.pad.mberp", 0);
                        if (Float.valueOf(response.getVersion()) > Float.valueOf(packageInfo.versionName)) {

                            Dialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                    .setMessage(R.string.dialog_hint_new_version)
                                    .setTitle(R.string.dialog_hint)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            mProgressDialog = new ProgressDialog(MainActivity.this);
                                            mProgressDialog.setTitle(R.string.dialog_downloading_file);
                                            mProgressDialog.setIndeterminate(true);
                                            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                            mProgressDialog.setCancelable(true);

                                            final DownloadTask downloadTask = new DownloadTask(MainActivity.this);
                                            downloadTask.execute(response.getUrl());

                                            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                @Override
                                                public void onCancel(DialogInterface dialog) {
                                                    downloadTask.cancel(true);
                                                }
                                            });
                                        }
                                    })
                                    .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            MainApplication.REMEMBER_IF_NEED_UPDATE = false;
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .create();
                            alertDialog.show();

                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        RequestManager.getRequestQueue().add(gsonObjRequest);

        return result;
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/pad.apk");

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null) {
                Dialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setMessage(R.string.dialog_download_error)
                        .setTitle(R.string.dialog_hint)
                        .setPositiveButton(R.string.dialog_confirm, null)
                        .setCancelable(true)
                        .create();
                alertDialog.show();
            } else {
                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pad.apk";
                Uri uri = Uri.fromFile(new File(fileName));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");

                startActivity(intent);
            }
        }
    }

}
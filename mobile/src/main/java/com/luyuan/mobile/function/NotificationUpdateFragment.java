package com.luyuan.mobile.function;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.VersionData;
import com.luyuan.mobile.ui.LoginActivity;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// 系统更新
public class NotificationUpdateFragment extends Fragment {

    private ProgressDialog mProgressDialog;
    private ProgressDialog dialog;
    private TextView textview_latest_version;
    private TextView textview_current_version;
    private TextView textview_need_title;
    private TextView textview_need;
    private TextView textview_size_title;
    private TextView textview_size;
    private Button button_update;
    private Button button_enter;

    private int current_code;
    private int latest_code;
    private String latest_version = "";
    private String current_version = "";
    private int need;
    private float size;
    private String download_url = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_update_fragment, null);

        textview_latest_version = (TextView) view.findViewById(R.id.textview_latest_version);
        textview_current_version = (TextView) view.findViewById(R.id.textview_current_version);
        textview_need_title = (TextView) view.findViewById(R.id.textview_need_title);
        textview_need = (TextView) view.findViewById(R.id.textview_need);
        textview_size_title = (TextView) view.findViewById(R.id.textview_size_title);
        textview_size = (TextView) view.findViewById(R.id.textview_size);
        button_update = (Button) view.findViewById(R.id.button_update);
        button_enter = (Button) view.findViewById(R.id.button_enter);

        try {
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            current_code = packageInfo.versionCode;
            current_version = packageInfo.versionName;
            textview_current_version.setText(current_version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((Button) view.findViewById(R.id.button_enter)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MyGlobal.getUser().getSessionId().isEmpty()) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("stId", MyGlobal.getUser().getStId());
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        ((Button) view.findViewById(R.id.button_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_code >= latest_code) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.already_latest_version)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }

                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setTitle(R.string.dialog_downloading_file);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(true);

                final DownloadTask downloadTask = new DownloadTask(getActivity());
                downloadTask.execute(download_url);

                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        downloadTask.cancel(true);
                    }
                });
            }
        });

        if (MyGlobal.checkNetworkConnection(getActivity())) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.loading));
            dialog.setCancelable(true);
            dialog.show();

            GsonRequest gsonObjRequest = new GsonRequest<VersionData>(Request.Method.GET, MyGlobal.API_CHECK_VERSION + "&versionCode=" + current_code,
                    VersionData.class, new Response.Listener<VersionData>() {
                @Override
                public void onResponse(final VersionData response) {
                    dialog.dismiss();

                    if (response != null && response.getSuccess().equals("true")) {
                        download_url = response.getUrl();
                        latest_code = response.getCode();
                        latest_version = response.getVersion();
                        textview_latest_version.setText(latest_version);
                        need = response.getNeed();
                        size = response.getSize();
                        if (latest_code <= current_code) {
                            textview_need.setText(R.string.update_latest_version);
                            textview_size.setText(R.string.update_latest_version);
                        } else {
                            String needText = "";
                            if (need == 0) {
                                textview_need.setTextColor(Color.parseColor("#46C50C"));
                                needText = "可选";
                            } else if (need == 1) {
                                button_enter.setVisibility(View.GONE);
                                textview_need.setTextColor(Color.parseColor("#FF0000"));
                                needText = "必须";
                            }
                            textview_need.setText(needText);
                            textview_size.setText(String.valueOf(size) + " MB");
                        }

                    } else {
                        new AlertDialog.Builder(getActivity())
                                .setMessage(R.string.interact_data_error)
                                .setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null)
                                .create()
                                .show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();

                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.interact_data_error)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                }
            }
            );

            RequestManager.getRequestQueue().add(gsonObjRequest);
        }

        return view;
    }

    // 异步线程下载文件任务
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

                // 下载文件
                input = connection.getInputStream();
                output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/pad.apk");

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // 更新下载进度
                    if (fileLength > 0)
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
                Dialog alertDialog = new AlertDialog.Builder(getActivity())
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
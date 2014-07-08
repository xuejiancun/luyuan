package com.luyuan.mobile.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.luyuan.mobile.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class HttpMultipartPost extends AsyncTask<String, Integer, String> {

    private Context context;
    private ArrayList<String> filePaths;
    private List<BasicNameValuePair> pairs;
    private String hint;
    private ProgressDialog pd;
    private long totalSize;

    public HttpMultipartPost(Context context, List<BasicNameValuePair> pairs, ArrayList<String> filePaths, String hint) {
        this.pairs = pairs;
        this.context = context;
        this.filePaths = filePaths;
        this.hint = hint;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage(hint);
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String serverResponse = null;

        // compress files
        File dir = new File(MyGlobal.COMPRESS_PATH);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }
        File compressed = new File(dir, MyGlobal.getUser().getUsername() + "_" + MyGlobal.sdf.format(new Date()) + ".zip");

        Collection<File> files = new ArrayList<File>();
        for (String path : filePaths) {
            File file = new File(path);
            files.add(file);
        }

        try {
            ZipUtils.zipFiles(files, compressed);
        } catch (IOException e) {
            serverResponse = "upload_compress_error";
        }

        pairs.add(new BasicNameValuePair("attachment", compressed.getName()));

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext httpContext = new BasicHttpContext();
        HttpPost httpPostFile = new HttpPost(MyGlobal.API_UPLOAD_MATERIAL);

        try {
            CustomMultipartEntity multipartContent = new CustomMultipartEntity(
                    new CustomMultipartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    }
            );

            multipartContent.addPart("file", new FileBody(compressed));
            totalSize = multipartContent.getContentLength();

            httpPostFile.setEntity(multipartContent);
            HttpResponse response = httpClient.execute(httpPostFile, httpContext);
            serverResponse = EntityUtils.toString(response.getEntity());

            if (serverResponse.equals("upload_success_false")) {
                return serverResponse;
            }

            pairs.add(new BasicNameValuePair("filepath", serverResponse));

            HttpPost httpPostData = new HttpPost(MyGlobal.API_SUBMIT_MATERIAL);
            httpPostData.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
            httpPostData.setHeader("Cookie", "ASP.NET_SessionId=" + MyGlobal.getUser().getSessionId());
            response = httpClient.execute(httpPostData, httpContext);
            serverResponse = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return serverResponse;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        pd.setProgress((int) (progress[0]));
    }

    @Override
    protected void onPostExecute(String result) {
        pd.dismiss();

        if (result.equals("submit_success_true")) {
            new AlertDialog.Builder(context)
                    .setMessage(R.string.submitted_success)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create()
                    .show();
        } else if (result.contains("submit_success_false")) {
            new AlertDialog.Builder(context)
                    .setMessage(R.string.submitted_error)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create()
                    .show();
        } else if (result.contains("upload_success_false")) {
            new AlertDialog.Builder(context)
                    .setMessage(R.string.upload_error)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create()
                    .show();
        } else if (result.contains("compress_error")) {
            new AlertDialog.Builder(context)
                    .setMessage(R.string.compress_error)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create()
                    .show();
        } else {
            new AlertDialog.Builder(context)
                    .setMessage(R.string.submitted_error)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create()
                    .show();
        }

    }

    @Override
    protected void onCancelled() {
    }

}

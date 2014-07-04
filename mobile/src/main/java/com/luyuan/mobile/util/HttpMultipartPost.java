package com.luyuan.mobile.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
import java.util.List;

public class HttpMultipartPost extends AsyncTask<String, Integer, String> {

    private Context context;
    private String filePath;
    private List<BasicNameValuePair> pairs;
    private String hint;
    private ProgressDialog pd;
    private long totalSize;

    public HttpMultipartPost(Context context, List<BasicNameValuePair> pairs, String filePath, String hint) {
        this.pairs = pairs;
        this.context = context;
        this.filePath = filePath;
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

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext httpContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(MyGlobal.API_UPLOAD_MATERIAL);

        try {
            CustomMultipartEntity multipartContent = new CustomMultipartEntity(
                    new CustomMultipartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    }
            );

            multipartContent.addPart("file", new FileBody(new File(filePath)));
            totalSize = multipartContent.getContentLength();

            httpPost.setEntity(multipartContent);
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
            HttpResponse response = httpClient.execute(httpPost, httpContext);
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
    }

    @Override
    protected void onCancelled() {
    }

}

package com.luyuan.mobile.production;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.model.WarehouseLocationInventoryListAdapter;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import org.apache.http.message.BasicNameValuePair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseAutomaticScanActivity extends Activity {

    private String tab = "function";
    private Button bPlay;
    private Button bPause;
    private SoundPool sp;
    private HashMap spMap;
    private EditText editText2, editText_bc, editText_ship;
    private TextView TextView_ship, TextView_bc, TextView_showship;
    private Button serch, add, modify, delete;
    private ListView listView;
    private ProgressDialog progressDialog;
    private WarehouseAutomaticScanActivity li = null;
    private List<BasicNameValuePair> list;
    private List<String> list1;
    private List<Map<String, Object>> listItems;
    private Context c;
    private static final int ITEM_MODIFY = 1;
    private static final int ITEM_DELETE = 2;
    private String fhtemp = "";
    private String fh = "";
    private String fhcode = "";
    private String t = "";
    private String temp = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    WarehouseLocationInventoryListAdapter listViewAdapter = new WarehouseLocationInventoryListAdapter(c, listItems);
                    listView.setAdapter(listViewAdapter);

                    break;

                default:
                    break;
            }
            super.handleMessage(msg);


        }
    };
    private SpannableString ss;

    @SuppressWarnings("unchecked")
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_whautomaticscan);
        setContentView(R.layout.warehouse_autoscan_activity);
        TextView_bc = (TextView) findViewById(R.id.editText_baocuo);
        TextView_ship = (TextView) findViewById(R.id.TextView_shiporder);
        TextView_showship = (TextView) findViewById(R.id.TextView_show1);
        ss = new SpannableString("错误");
        //设置0-2的字符颜色
        ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText2 = (EditText) findViewById(R.id.editText_shaomiao);

        editText2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                try {
                    temp = editText2.getText().toString().trim();

                    if ((temp.length() < 12 && temp.length() > 0) || temp.length() > 12 && temp.length() < 16) {
                        TextView_bc.setText(TextView_bc.getText().toString().trim() + "\n" + editText2.getText().toString().trim() + "\r" + "错误" + ": " + "扫描有误");
                        editText2.setText("");
                        editText2.setFocusable(true);

                        editText2.setFocusableInTouchMode(true);
                        editText2.requestFocus();

                        playSounds(1, 0);
                    } else {


                        if (temp.substring(0, 4).equals("ZCFH")) {
                            fhcode = temp;
                            TextView_showship.setText(fhcode);

                            TextView_bc.setText("");
                            editText2.setText("");
                            editText2.setFocusable(true);

                            editText2.setFocusableInTouchMode(true);
                            editText2.requestFocus();


                        } else if (temp.length() == 16) {

                            //    List<BasicNameValuePair>  list = new ArrayList<BasicNameValuePair>();
                            //    list.add( new BasicNameValuePair("ProductBarCode",temp));
                            //    list.add( new BasicNameValuePair("VoucerCode",fhcode));
                            //   try {
                            //	    t = http.PostData(list, "/modules/An.Warehouse.Web/Ajax/ProductApprovedHandler.ashx?fn=updateproductbarcode");
                            //	    li = JSONHelper.parseObject(t, WarehouseAutomaticScanActivity.class);
                            //  } catch (Exception e) {
                            //	    e.printStackTrace();
                            //  };
                            StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_AUTOMATIC_SCAN);
                            url.append("&ProductBarCode=" + temp + "&VoucherCode=" + fhcode);
                            if (MyGlobal.checkNetworkConnection(WarehouseAutomaticScanActivity.this)) {


                                GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url.toString(), SuccessData.class,
                                        new Response.Listener<SuccessData>() {

                                            @Override
                                            public void onResponse(SuccessData response) {
                                                if (response != null) {
                                                    t = response.getsingleInfo().toString();
                                                    if (t.substring(0, 2).equals("ok")) {

                                                        t = t.replace("<br />", "");
                                                        t.split("∏").toString();

                                                        if (t.split("∏")[2] == t.split("∏")[3]) {
                                                            TextView_showship.setText(TextView_showship.getText().toString().trim().substring(0, 12) + " " + t.split("∏")[2] + "/" + t.split("∏")[3] + " " + "已满足" + "\n");
                                                            editText2.setFocusable(true);

                                                            editText2.setFocusableInTouchMode(true);
                                                            editText2.requestFocus();


                                                        } else {

                                                            TextView_showship.setText(TextView_showship.getText().toString().trim().substring(0, 12) + " " + t.split("∏")[2] + "/" + t.split("∏")[3] + " " + "未满足" + "\n");
                                                            editText2.setFocusable(true);

                                                            editText2.setFocusableInTouchMode(true);
                                                            editText2.requestFocus();

                                                        }
                                                        TextView_bc.setText(TextView_bc.getText().toString().trim() + "\n" + editText2.getText().toString().trim() + " " + "正常 " + "\n" + t.split("∏")[1] + "\n");
                                                        TextView_ship.setText(t.split("∏")[4]);
                                                        playSounds(2, 0);
                                                        editText2.setText("");
                                                        editText2.setFocusable(true);

                                                        editText2.setFocusableInTouchMode(true);
                                                        editText2.requestFocus();


                                                    } else {

                                                        TextView_bc.setText(TextView_bc.getText().toString().trim() + "\n" + editText2.getText().toString().trim() + "\r" + ss + ": " + t + " \n");


                                                        if (t.equals("重复扫描")) {
                                                            playSounds(4, 0);
                                                        } else {
                                                            playSounds(3, 0);
                                                        }
                                                        editText2.setText("");
                                                        editText2.setFocusable(true);

                                                        editText2.setFocusableInTouchMode(true);
                                                        editText2.requestFocus();
                                                    }
                                                }
                                            }

                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //dialog.dismiss();

                                        new AlertDialog.Builder(WarehouseAutomaticScanActivity.this).setMessage(error.getMessage().toString()).setTitle(R.string.dialog_hint)
                                                .setPositiveButton(R.string.dialog_confirm, null).create().show();
                                    }
                                });

                                RequestManager.getRequestQueue().add(gsonObjRequest);
                            }


                        } else {

                            TextView_bc.setText(TextView_bc.getText().toString().trim() + "\n" + editText2.getText().toString().trim() + "\r" + "错误" + ": " + "扫描有误");
                            playSounds(1, 0);
                            editText2.setText("");
                            editText2.setFocusable(true);

                            editText2.setFocusableInTouchMode(true);
                            editText2.requestFocus();


                        }

                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });


        c = this;

        sp = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        spMap = new HashMap();
        spMap.put(1, sp.load(this, R.raw.error, 1));
        spMap.put(2, sp.load(this, R.raw.ok, 1));
        spMap.put(3, sp.load(this, R.raw.proderro, 1));
        spMap.put(4, sp.load(this, R.raw.re, 1));


    }

    public SpannableString getSizeSpanUsePx(String fhcode2, String str, int start, int end, int pxSize) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new AbsoluteSizeSpan(pxSize), 4, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public void playSounds(int sound, int number) {
        AudioManager am = (AudioManager) this.getSystemService(this.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolumn / audioMaxVolumn;

        sp.play((Integer) spMap.get(sound), volumnRatio, volumnRatio, 1, number, 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//            return true;
//        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("stId", MyGlobal.getUser().getStId());
            intent.putExtra("tab", tab);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

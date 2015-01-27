package com.luyuan.mobile.production;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.JSONHelper;
import com.luyuan.mobile.model.ReturnJson;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.model.WarehouseBinInventoryCheckSave;
import com.luyuan.mobile.model.WarehouseBinInventoryDetaillist_detail;
import com.luyuan.mobile.model.http;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import org.apache.http.message.BasicNameValuePair;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WarehouseBinInventoryCheckActivity extends Activity {

    private String tab = "function";
    private Button serch, clear, save;
    private ListView listView;
    private ProgressDialog progressDialog;
    private EditText editText_pbarcode, editText_binnum;
    private WarehouseBinInventoryCheckActivity li = null;
    private List<BasicNameValuePair> list;
    private List<String> list1;
    private List<Map<String, Object>> listItems;
    private Context c;
    private static final int ITEM_MODIFY = 1;
    private static final int ITEM_DELETE = 2;
    private ProgressDialog dialog;
    private CharSequence t;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    WarehouseBinInventoryDetaillist_detail listViewAdapter = new
                            WarehouseBinInventoryDetaillist_detail(c, listItems);
                    listView.setAdapter(listViewAdapter);

                    break;

                default:
                    break;
            }
            super.handleMessage(msg);


        }
    };
    private SpannableString ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_whbincheck);
        setContentView(R.layout.warehouse_bininventorycheck_activity);
        editText_pbarcode = (EditText) findViewById(R.id.editText_pbarcode);
        editText_binnum = (EditText) findViewById(R.id.editText_binnum);
        serch = (Button) findViewById(R.id.button_search);
        clear = (Button) findViewById(R.id.button_clear);
        save = (Button) findViewById(R.id.button_ok);

        //  editText_pbarcode.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        // listView = (ListView)findViewById(R.id.listView_detail);
        c = this;
        serch.setOnClickListener(serchcilck);
        clear.setOnClickListener(clearcilck);
        save.setOnClickListener(savecilck);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return true;
        }
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

    Button.OnClickListener serchcilck = new Button.OnClickListener() {
        @SuppressLint("NewApi")
        public void onClick(View v) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            progressDialog = ProgressDialog.show(
                    WarehouseBinInventoryCheckActivity.this, "请稍等...", "正在查询数据....",
                    true, false);
            Intent intent = new Intent(WarehouseBinInventoryCheckActivity.this,
                    WarehouseBinInventoryCheckDetailActivity.class);
            startActivity(intent);

            progressDialog.dismiss();
        }


    };
    Button.OnClickListener clearcilck = new Button.OnClickListener() {
        @SuppressLint("NewApi")
        public void onClick(View v) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            progressDialog = ProgressDialog.show(
                    WarehouseBinInventoryCheckActivity.this, "请稍等...", "正在查询数据....",
                    true, false);
            editText_pbarcode.setText("");
            progressDialog.dismiss();


        }
    };
    Button.OnClickListener savecilck = new Button.OnClickListener() {
        @SuppressLint("NewApi")
        public void onClick(View v) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            WarehouseBinInventoryCheckSave wb = new WarehouseBinInventoryCheckSave();
            String binnum = editText_binnum.getText().toString();
            if (binnum.equals("")) {
                new AlertDialog.Builder(WarehouseBinInventoryCheckActivity.this).setMessage("无库位号").setTitle(R.string.dialog_hint)
                        .setPositiveButton(R.string.dialog_confirm, null).create().show();
                return;
            }
            wb.setWbName(URLEncoder.encode(binnum));
            String pbarcode = editText_pbarcode
                    .getText().toString().replace("\r\n", ",").replace("\n", ",");
            if (pbarcode.equals("")) {
                new AlertDialog.Builder(WarehouseBinInventoryCheckActivity.this).setMessage("无整车条码").setTitle(R.string.dialog_hint)
                        .setPositiveButton(R.string.dialog_confirm, null).create().show();
                return;
            }
            wb.setProductBarCodes(pbarcode);
            wb.setUnitID(MyGlobal.getUser().getUnitId());
            wb.setHeID(MyGlobal.getUser().getHeId());
            //String json = JSONHelper.toJSON(wb).replace("\"[", "[").replace("]\"", "]").replace("'", "\"");
            //String s = "";
            //List<BasicNameValuePair>  list = new ArrayList<BasicNameValuePair>();
            //list.add( new BasicNameValuePair("json",json));
//			try{
//				s = http.PostData(list,
//						"/modules/An.Warehouse.Web/Ajax/whBinInventoryCheckHandler.ashx?fn=savedata4lwp");
//
//				toastShow(s);
//				if (JSONHelper.parseObject(s, ReturnJson.class).getSuccess()) {
//
//					new AlertDialog.Builder(WarehouseBinInventoryCheckActivity.this).setTitle("提示").setMessage("盘点成功!").setNeutralButton("知道了", new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//						}
//					}).show();
//				}else{
//					new AlertDialog.Builder(WarehouseBinInventoryCheckActivity.this).setTitle("提示").setMessage(s).setNeutralButton("知道了", new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//						}
//					}).show();
//
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
            String json = new Gson().toJson(wb);
            StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_BINSAVE);
            url.append("&json=" + json);
            if (MyGlobal.checkNetworkConnection(WarehouseBinInventoryCheckActivity.this)) {
                dialog = new ProgressDialog(WarehouseBinInventoryCheckActivity.this);
                dialog.setMessage(getText(R.string.search_loading));
                dialog.setCancelable(true);
                dialog.show();

                GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url.toString(), SuccessData.class,
                        new Response.Listener<SuccessData>() {

                            @Override
                            public void onResponse(SuccessData response) {
                                dialog.dismiss();
                                if (response.getSuccess().equals("true")) {
                                    new AlertDialog.Builder(WarehouseBinInventoryCheckActivity.this).setMessage("盘点成功").setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null).create().show();

                                } else {
                                    new AlertDialog.Builder(WarehouseBinInventoryCheckActivity.this).setMessage(response.getData().get(0).getInfo()).setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null).create().show();
                                }
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        new AlertDialog.Builder(WarehouseBinInventoryCheckActivity.this).setMessage(error.getMessage().toString()).setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null).create().show();
                    }
                });

                RequestManager.getRequestQueue().add(gsonObjRequest);
            }

        }
    };

    public void toastShow(String text) {
        Toast.makeText(WarehouseBinInventoryCheckActivity.this, text, 1000).show();
    }
}

package com.luyuan.mobile.production;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.model.whBinExchangeData;
import com.luyuan.mobile.model.whBinExchangeDetail;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import org.apache.http.message.BasicNameValuePair;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseBinExchangectivity extends Activity  {

    private String tab = "function";
	private Button save,clear;
	private ListView listView;
	private ProgressDialog progressDialog;
	private EditText edittext_inbin,edittext_outbin,edittext_product;
    private TextView textview_product;
	//private WarehouseBinExchangectivity li = null ;
	private List<BasicNameValuePair> list;
	private List<String> list1;
	private List<Map<String, Object>> listItems;
	private Context c;
	private static final int ITEM_MODIFY = 1;
	private static final int ITEM_DELETE = 2;
    private ProgressDialog dialog;
    private CharSequence t;
    private SoundPool sp;
    private HashMap spMap;
    private whBinExchangeData whbinexchangedata = new whBinExchangeData();
    private String recentproduct="";
   // private Boolean flag=false;
    private List listproduct = new ArrayList();
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case 1:
					//warehouselistViewAdapter = new
					//		WarehouseBinInventoryDetaillist_detail(c, listItems);
					//listView.setAdapter(listViewAdapter);

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
	    actionBar.setTitle(R.string.function_whbinexchange);
	    setContentView(R.layout.warehouse_binexchange_activity);
	    save = (Button)findViewById(R.id.button_exchange);
        clear = (Button)findViewById(R.id.button_clear);
        edittext_inbin = (EditText) findViewById(R.id.edittext_inbin);
        edittext_outbin = (EditText) findViewById(R.id.edittext_outbin);
        edittext_product = (EditText) findViewById(R.id.edittext_product);
        textview_product = (TextView)findViewById(R.id.textview_product);
	    listView = (ListView)findViewById(R.id.listView_information);
	    c = this;
	    save.setOnClickListener(savecilck);
        clear.setOnClickListener(clearclick);
//        edittext_product.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                ((TextView) findViewById(R.id.edittext_product)).setText("");
//                return true;
//            }
//        });
//        edittext_product.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                    edittext_product.setFocusable(true);
//                    edittext_product.setFocusableInTouchMode(true);
//                    edittext_product.requestFocus();
//            }
//        });
        edittext_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(flag==true)

              ((TextView) findViewById(R.id.edittext_product)).setText("");
            }
        });
        edittext_product.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                recentproduct="";
                try{
                    if(s.length()>16)
                    {
                        //((TextView) findViewById(R.id.edittext_product)).setText("");
                        edittext_product.setText(s.subSequence(0,16).toString());
                        textview_product.setText(s.subSequence(0,16).toString());
                    }
                    if(s.length()==16) {
                        textview_product.setText(s.toString());
                        String outbin = edittext_outbin.getText().toString();
                        if (outbin.equals("")) {
                            new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage("无调出库位号").setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null).create().show();
                            return;
                        }
                        String inbin = edittext_inbin.getText().toString();
                        if (inbin.equals("")) {
                            new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage("无调入库位号").setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null).create().show();
                            return;
                        }
                        final String temp = s.toString();
                        if (temp.length() != 16) {
                            //TextView_bc.setText(TextView_bc.getText().toString().trim()+"\n" +  editText2.getText().toString().trim() +"\r" +  "错误"+  ": " +"扫描有误" );
                            playSounds(1, 0);
                            new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage("扫描有误").setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null).create().show();



                        } else {
                            if(listproduct.size() > 0) {
                                int k, size = 0;
                                size = listproduct.size();
                                for (k = 0; k < size; k++) {
                                    if (temp.equals(listproduct.get(k).toString()))
                                        break;

                                }
                                if (k < size) {
                                    new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage("整车条码重复扫描").setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null).create().show();
                                    return;

                                }
                            }
                            whBinExchangeData wb = new whBinExchangeData();
                            wb.setOutbin(URLEncoder.encode(outbin));
                            wb.setInbin(URLEncoder.encode(inbin));
                            wb.setRecentproduct(temp);
                            wb.setUnitID(MyGlobal.getUser().getUnitId());
                            //ListAdapter ld = listView.getAdapter();

                            if (listView !=null && listView.getCount() > 0) {
                                List<whBinExchangeDetail> data = new ArrayList<whBinExchangeDetail>();
                                for (int i = 0; i < listView.getCount(); i++) {

                                    whBinExchangeDetail detail = new whBinExchangeDetail();
                                    View v = listView.getAdapter().getView(i,null,null);
                                    String productcode=((TextView) v.findViewById(R.id.textview_wbcode)).getText().toString();
                                    detail.setProductCode(productcode);
                                    //wb.getData().get(i).setProductCode(productcode);
                                    String qty=((TextView) v.findViewById(R.id.textview_unit)).getText().toString();
                                    detail.setQty(qty);
                                    data.add(i,detail);
                                   // wb.getData().get(i).setQty(qty);

                                }
                                wb.setData(data);
                            }

                            String json = new Gson().toJson(wb);
                            StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_BINEXCHANGE_QUERY);
                            url.append("&json=" + json);
                            if (MyGlobal.checkNetworkConnection(WarehouseBinExchangectivity.this)) {
                                dialog = new ProgressDialog(WarehouseBinExchangectivity.this);
                                dialog.setMessage(getText(R.string.search_loading));
                                dialog.setCancelable(true);
                                dialog.show();
                                GsonRequest gsonObjRequest = new GsonRequest<whBinExchangeData>(Request.Method.GET, url.toString(), whBinExchangeData.class,
                                        new Response.Listener<whBinExchangeData>() {

                                            @Override
                                            public void onResponse(whBinExchangeData response) {
                                                dialog.dismiss();
                                                if (response.getSuccess().equals("true")) {
                                                    recentproduct = ((TextView) findViewById(R.id.edittext_product)).getText().toString();
                                                    if(whbinexchangedata.getData().size()>0)
                                                    {
                                                        for(int j=0;j<response.getData().size();j++)
                                                        {
                                                            String Productcode=response.getData().get(j).getProductCode();
                                                            int t;
                                                            for(t=0;t<whbinexchangedata.getData().size();t++)
                                                            {
                                                                if(Productcode.equals(whbinexchangedata.getData().get(t).getProductCode())) {
                                                                    String qty = "0";
                                                                    try {
                                                                        qty = whbinexchangedata.getData().get(t).getQty();
                                                                        if (qty.equals("")) {
                                                                            qty = "0";
                                                                        }

                                                                    } catch (Exception e) {
                                                                    }
                                                                    whbinexchangedata.getData().get(t).setQty(String.valueOf(Integer.parseInt(qty)+1));
                                                                    break;
                                                                }

                                                            }
                                                            if(t==whbinexchangedata.getData().size())
                                                            {
                                                                whbinexchangedata.getData().add(whbinexchangedata.getData().size(),response.getData().get(j));
                                                                //whbinexchangedata.setRecentproduct(recentproduct);
                                                            }
                                                         }

                                                    }
                                                    else {
                                                        whbinexchangedata = response;
                                                        //whbinexchangedata.setRecentproduct(recentproduct);
                                                    }
                                                    if(listproduct.size() > 0) {
                                                        int t, size = 0;
                                                        size = listproduct.size();
                                                        for (t = 0; t < size; t++) {
                                                            if (recentproduct.equals(listproduct.get(t).toString()))
                                                                break;

                                                        }
                                                        if (t == size) {
                                                            //listView.setAdapter(new SearchListAdapter(WarehouseBinExchangectivity.this));
                                                            listproduct.add(recentproduct);
                                                        }
                                                    } else {

                                                        listproduct.add(recentproduct);
                                                     }
                                                    listView.setAdapter(new SearchListAdapter(WarehouseBinExchangectivity.this));

                                                }
                                                else
                                                {
                                                    new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage(response.getData().get(0).getresult()).setTitle(R.string.dialog_hint)
                                                            .setPositiveButton(R.string.dialog_confirm, null).create().show();
                                                }


                                            }

                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        dialog.dismiss();

                                    }
                                });
//                                 ((TextView) findViewById(R.id.edittext_product)).setText("");
//                                   edittext_product.setFocusable(true);
//                    edittext_product.setFocusableInTouchMode(true);
//                    edittext_product.requestFocus();
                                RequestManager.getRequestQueue().add(gsonObjRequest);
                            }


                        }
                    }
                    //recentproduct="";


                }
                catch(Exception e)
                {
                   // TextView_bc.setText(TextView_bc.getText().toString().trim()+"\n" +  editText2.getText().toString().trim() +"\r" +  "错误"+  ": " +"扫描有误" );
                    playSounds(1,0);
                    new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage(e.getMessage().toString()).setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null).create().show();

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
//                if(edittext_product.isFocused()==false) {
//                    edittext_product.setFocusable(true);
//                    edittext_product.setFocusableInTouchMode(true);
//                    edittext_product.requestFocus();
//                }

            }
        });





    }

    public void playSounds(int sound, int number){
        AudioManager am = (AudioManager)this.getSystemService(this.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolumn/audioMaxVolumn;

        sp.play((Integer) spMap.get(sound), volumnRatio, volumnRatio, 1, number, 1);
    }



    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        if(getFragmentManager().getBackStackEntryCount()>0) {
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
    Button.OnClickListener clearclick = new Button.OnClickListener (){
        @SuppressLint("NewApi")
        public void onClick(View v) {
//            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//                    .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//            progressDialog = ProgressDialog.show(
//                    WarehouseBinExchangectivity.this, "请稍等...", "正在重置....",
//                    true, false);
            edittext_product.setText("");
            edittext_outbin.setText("");
            edittext_inbin.setText("");
            try {

                listView.setAdapter(null);
            }
            catch(Exception e)
            {

            }
         //   progressDialog.dismiss();


        }
    };
	Button.OnClickListener savecilck = new Button.OnClickListener (){
		@SuppressLint("NewApi")
		public void onClick(View v) {
//			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//					.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
			//whBinExchangeData wb = new whBinExchangeData();
            String inbin=edittext_inbin.getText().toString();
            if(inbin.equals(""))
            {
                new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage("无调入库位号").setTitle(R.string.dialog_hint)
                        .setPositiveButton(R.string.dialog_confirm, null).create().show();
                return;
            }
			//wb.setInbin(inbin);

            String outbin=edittext_outbin.getText().toString();
            if(outbin.equals(""))
            {
                new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage("无调出库位号").setTitle(R.string.dialog_hint)
                        .setPositiveButton(R.string.dialog_confirm, null).create().show();
                return;
            }
            //wb.setOutbin(inbin);
//            String product=edittext_product
//                    .getText().toString().replace("\r\n", ",").replace("\n", ",");
            if(listproduct.size()==0)
            {
                new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage("无整车条码").setTitle(R.string.dialog_hint)
                        .setPositiveButton(R.string.dialog_confirm, null).create().show();
                return;
            }
            whBinExchangeData savewb = new whBinExchangeData();
            savewb.setOutbin(URLEncoder.encode(outbin));
            savewb.setInbin(URLEncoder.encode(inbin));
            savewb.setUnitID(MyGlobal.getUser().getUnitId());
            if(listproduct.size()>0) {
                List<whBinExchangeDetail> data = new ArrayList<whBinExchangeDetail>();
                for (int i = 0; i < listproduct.size(); i++) {
                    //savewb.getData().get(i).setProductCode(listproduct.get(i).toString());
                    whBinExchangeDetail wbdetail = new whBinExchangeDetail();
                    wbdetail.setProductBarCode(listproduct.get(i).toString());
                    data.add(i, wbdetail);
                }
                savewb.setData(data);
            }
            String json = new Gson().toJson(savewb);
            StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_BINEXCHANGE_SAVE);
            url.append("&json=" +json);
            if (MyGlobal.checkNetworkConnection(WarehouseBinExchangectivity.this)) {
                dialog = new ProgressDialog(WarehouseBinExchangectivity.this);
                dialog.setMessage(getText(R.string.search_loading));
                dialog.setCancelable(true);
                dialog.show();

                GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url.toString(), SuccessData.class,
                        new Response.Listener<SuccessData>() {

                            @Override
                            public void onResponse(SuccessData response) {
                                dialog.dismiss();
                                if (response.getSuccess().equals("true")) {
                                    new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage("调整成功").setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null).create().show();

                                }
                                else
                                {
                                    new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage(response.getData().get(0).getInfo()).setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null).create().show();
                                }
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse( VolleyError error) {
                        dialog.dismiss();

                        new AlertDialog.Builder(WarehouseBinExchangectivity.this).setMessage(error.getMessage().toString()).setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null).create().show();
                    }
                });

                RequestManager.getRequestQueue().add(gsonObjRequest);
            }

		}
	};
    public class SearchListAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public SearchListAdapter( Context c) {
            //super(c, R.layout.item_warehouse_bin_search);
            this.layoutInflater=LayoutInflater.from(c);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(R.layout.item_warehouse_bin_detail,null);
            //if(!recentproduct.equals(""))
          //  {
                //((TextView)convertView.findViewById(R.id.textview_wbname)).setText(whbinexchangedata.getData().get(position).getProductCode());
//                String product=((TextView)convertView.findViewById(R.id.textview_wbcode)).getText().toString();
//                if(recentproduct.equals(product.toString()))
//                {
//                    String qty = ((TextView)convertView.findViewById(R.id.textview_wbcode)).getText().toString();
//                    if(qty.equals(""))
//                    {
//                        qty="0";
//                    }
//                    int value=Integer.parseInt(qty)+1;
//                    ((TextView)convertView.findViewById(R.id.textview_unit)).setText(String.valueOf(value));
//                }
//                else
               // {

                    if(whbinexchangedata.getData().get(position).getQty()=="")
                    {
                        whbinexchangedata.getData().get(position).setQty("1");
                    }
                    ((TextView)convertView.findViewById(R.id.textview_wbcode)).setText(whbinexchangedata.getData().get(position).getProductCode());
                    ((TextView)convertView.findViewById(R.id.textview_unit)).setText(whbinexchangedata.getData().get(position).getQty());
                    ((TextView)convertView.findViewById(R.id.textview_wbname)).setText(whbinexchangedata.getData().get(position).getPrefixName()
                            +"/"+whbinexchangedata.getData().get(position).getColorName());


               // }
           // }

            return convertView;
        }

        @Override
        public int getCount() {
            //return warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().size();
            return whbinexchangedata.getData().size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

    }
}

package com.luyuan.mobile.production;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Dialog;
import android.content.DialogInterface;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.model.WarehouseLocationInventoryListAdapter;
import com.luyuan.mobile.model.WarehouseVoucheritemList;
import com.luyuan.mobile.model.tbl_whPurchaseOrder;
import com.luyuan.mobile.model.tbl_whPurchaseOrderDetail;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.luyuan.mobile.model.http1;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import com.luyuan.mobile.model.JSONHelper;
import com.luyuan.mobile.model.ReturnJson;
import com.google.gson.Gson;

import java.util.Date;
import java.text.SimpleDateFormat;

public class WarehouseVoucherConfirmFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private String code;
    private String whID;
    private String unitID;
    private ProgressDialog dialog;
    private LayoutInflater layoutInflater;
    private WarehouseVoucheritemList warehouseVoucheritemList = new WarehouseVoucheritemList();
    //private Context c;
    //private List<Map<String, Object>> listItems;
    //  StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_PURCHASEORDER_QUERY);
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    Bundle data = msg.getData();
                    new AlertDialog.Builder(getActivity()).setMessage(data.getString("data")).setTitle(R.string.dialog_hint).
                                            setPositiveButton(R.string.dialog_confirm, null).create().show();
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);


        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.warehouse_voucher_confirm_fragment, null);
        listView = (ListView) view.findViewById(R.id.listview_warehouse_voucher_confirm_list);
//        ((EditText) view.findViewById(R.id.edittext_confirm_num)).addTextChangedListener(new TextWatcher() {
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
        ((Button) view.findViewById(R.id.button_warehouse_voucher_confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbl_whPurchaseOrder warehouseVouchersave = new tbl_whPurchaseOrder();
                //List<tbl_whPurchaseOrderDetail> data = new ArrayList<tbl_whPurchaseOrderDetail>();
                //warehouseVouchersave.setPreparedby(MyGlobal.getUser().);
                //warehouseVouchersave.setPreparedTime(new Date());
                warehouseVouchersave.setwhID(whID);
                warehouseVouchersave.setwhpCode(code);
                warehouseVouchersave.setUnitID(unitID);
                String[] part = code.split("-");
                warehouseVouchersave.setOtherOrderCode(part[0]);
                warehouseVouchersave.setBatch(part[1]);
                int j=0;
                for (int i = 0; i < listView.getCount(); i++) {
                    View v = listView.getAdapter().getView(i, null, null);
                    String itemID = ((TextView) v.findViewById(R.id.textview_item_id)).getText().toString();
                    String ExamineQTY = ((TextView) v.findViewById(R.id.edittext_confirm_num)).getText().toString();
                    String itemCount = ((TextView) v.findViewById(R.id.textview_order_num)).getText().toString();
                    boolean chkbox=((CheckBox)v.findViewById(R.id.checkbox_confirm_selection)).isChecked();
                    if(chkbox==true) {
                        tbl_whPurchaseOrderDetail data = new tbl_whPurchaseOrderDetail();
                        data.setExamineQTY(ExamineQTY);
                        data.setitemID(itemID);
                        data.setitemCount(itemCount);
                        data.setQTY(itemCount);
                        warehouseVouchersave.gettbl_whPurchaseOrderDetail().add(j, data);
                        j++;
                    }
                }
                if (MyGlobal.checkNetworkConnection(getActivity())) {
                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage(getText(R.string.search_loading));
                    dialog.setCancelable(true);
                    dialog.show();
                    final String json = new Gson().toJson(warehouseVouchersave);
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                String s = "";
                                List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
                                list.add(new BasicNameValuePair("json", json));
                                s = http1.PostData(list,"/modules/An.Warehouse.Web/Ajax/ArrivalChkQuery.ashx?fn=save");
                                Message msg = new Message();
                                msg.what=1;
                                Bundle d = new Bundle();
                                d.putString("data",JSONHelper.parseObject(s, ReturnJson.class).getData().get(0).getInfo());
                                msg.setData(d);
                                handler.sendMessage(msg);

                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                            dialog.dismiss();

                        }
                    });
                    thread.start();
                }



//                StringBuffer url = new StringBuffer(MyGlobal.API_WHPURSAVE);
//                url.append("&json=" + json);
//                if (MyGlobal.checkNetworkConnection(getActivity())) {
//                    dialog = new ProgressDialog(getActivity());
//                    dialog.setMessage(getText(R.string.search_loading));
//                    dialog.setCancelable(true);
//                    dialog.show();
//
//                    GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.DEPRECATED_GET_OR_POST  , url.toString(), SuccessData.class,
//                            new Response.Listener<SuccessData>() {
//
//                                @Override
//                                public void onResponse(SuccessData response) {
//                                    dialog.dismiss();
//                                    if (response.getSuccess().equals("true")) {
//                                        new AlertDialog.Builder(getActivity()).setMessage(R.string.save_success).setTitle(R.string.dialog_hint)
//                                                .setPositiveButton(R.string.dialog_confirm, null).create().show();
//
//                                    } else {
//                                        new AlertDialog.Builder(getActivity()).setMessage(response.getData().get(0).getInfo()).setTitle(R.string.dialog_hint)
//                                                .setPositiveButton(R.string.dialog_confirm, null).create().show();
//                                    }
//                                }
//
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            dialog.dismiss();
//
//                            new AlertDialog.Builder(getActivity()).setMessage(error.getMessage().toString()).setTitle(R.string.dialog_hint)
//                                    .setPositiveButton(R.string.dialog_confirm, null).create().show();
//                        }
//                    });
//
//                    RequestManager.getRequestQueue().add(gsonObjRequest);
//                }
//
////                new AlertDialog.Builder(getActivity()).setMessage(json).setTitle(R.string.dialog_hint)
////                        .setPositiveButton(R.string.dialog_confirm, null).create().show();
//
            }
        });

        ((Button) view.findViewById(R.id.button_warehouse_voucher_check)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer url = new StringBuffer(MyGlobal.API_WHPURSUBMIT);
                url.append("&whpCode=" + code);
                if (MyGlobal.checkNetworkConnection(getActivity())) {
                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage(getText(R.string.search_loading));
                    dialog.setCancelable(true);
                    dialog.show();

                    GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url.toString(), SuccessData.class,
                            new Response.Listener<SuccessData>() {

                                @Override
                                public void onResponse(SuccessData response) {
                                    dialog.dismiss();
                                    if (response.getSuccess().equals("true")) {
                                        new AlertDialog.Builder(getActivity()).setMessage(R.string.submit_success).setTitle(R.string.dialog_hint)
                                                .setPositiveButton(R.string.dialog_confirm, null).create().show();

                                    } else {
                                        new AlertDialog.Builder(getActivity()).setMessage(response.getInfo()).setTitle(R.string.dialog_hint)
                                                .setPositiveButton(R.string.dialog_confirm, null).create().show();
                                    }
                                }

                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            new AlertDialog.Builder(getActivity()).setMessage(error.getMessage().toString()).setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null).create().show();
                        }
                    });

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }

//                new AlertDialog.Builder(getActivity()).setMessage(json).setTitle(R.string.dialog_hint)
//                        .setPositiveButton(R.string.dialog_confirm, null).create().show();

            }
        });


        Bundle args = getArguments();
        if (args != null && args.getBundle("data") != null) {
            Bundle data = args.getBundle("data");
            code = data.getString("code");
            whID = data.getString("whID");
            unitID = data.getString("unitID");
        }
        StringBuffer url = new StringBuffer(MyGlobal.API_WHPCODEITEMLIST_QUERY);
        url.append("&whpCode=" + code);

        if (MyGlobal.checkNetworkConnection(getActivity())) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.search_loading));
            dialog.setCancelable(true);
            dialog.show();
            GsonRequest gsonObjRequest = new GsonRequest<WarehouseVoucheritemList>(Request.Method.GET, url.toString(), WarehouseVoucheritemList.class,
                    new Response.Listener<WarehouseVoucheritemList>() {

                        @Override
                        public void onResponse(WarehouseVoucheritemList response) {
                            dialog.dismiss();

                            if (response.getSuccess().equals("true")) {
                                if (response.getWarehouseVoucheritemDetailList().size() == 0) {
                                    new AlertDialog.Builder(getActivity()).setMessage("当前单据返回数据有误")
                                            .setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null).create().show();
                                }
                                warehouseVoucheritemList = response;

                                listView.setAdapter(new SearchListAdapter(getActivity()));

                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                }
            });

            RequestManager.getRequestQueue().add(gsonObjRequest);
        }
        listView.setOnItemClickListener(this);
        return view;
    }

    public class SearchListAdapter extends ArrayAdapter<String> {

        public SearchListAdapter(Context c) {
            super(c, R.layout.item_warehouse_voucher_search);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final View view = layoutInflater.inflate(R.layout.item_warehouse_voucher_confirm, null);
            //if(convertView ==null) {

                ((TextView) view.findViewById(R.id.textview_item_code)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getitemCode());
                ((TextView) view.findViewById(R.id.textview_order_num)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getQTY());
                ((TextView) view.findViewById(R.id.textview_delivery_num)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getQTY());
                ((TextView) view.findViewById(R.id.textview_item_spec)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getitemSpec());
                ((TextView) view.findViewById(R.id.edittext_confirm_num)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getExamineQTY());
                ((TextView) view.findViewById(R.id.textview_item_id)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getitemID());
                ((CheckBox)view.findViewById(R.id.checkbox_confirm_selection)).setChecked(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getCheck());
                //((TextView) view.findViewById(R.id.textview_item_id)).setVisibility(View.GONE);
           // }
            ((TextView) view.findViewById(R.id.textview_item_id)).setVisibility(View.GONE);

            ((TextView) view.findViewById(R.id.edittext_confirm_num)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {


                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String old =warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getExamineQTY();

                    String txt=editable.toString();
                    if(txt.length()==0)
                        txt="0";
                    if(Float.parseFloat(txt) !=Float.parseFloat(old))
                        warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).setExamineQTY(txt);
                }
            });

            ((CheckBox) view.findViewById(R.id.checkbox_confirm_selection)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean chk=false;
                    try
                    {
                        chk=warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getCheck();
                    }
                    catch(Exception e)
                    {

                    }

                    if(chk==true)
                        warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).setCheck(false);
                    else
                        warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).setCheck(true);
                }
            });
            return view;
        }


        @Override
        public int getCount() {
            return warehouseVoucheritemList.getWarehouseVoucheritemDetailList().size();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listView.setItemChecked(i, true);
    }

}
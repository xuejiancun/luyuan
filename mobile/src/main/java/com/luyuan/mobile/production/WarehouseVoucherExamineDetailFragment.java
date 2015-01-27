package com.luyuan.mobile.production;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.JSONHelper;
import com.luyuan.mobile.model.ReturnJson;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.model.WarehousePurchaseVoucherDetail;
import com.luyuan.mobile.model.http1;
import com.luyuan.mobile.model.tbl_whPurchaseOrder;
import com.luyuan.mobile.model.tbl_whPurchaseOrderDetail;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class WarehouseVoucherExamineDetailFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private String whName;
    private String UnitName;
    private String code;
    private ProgressDialog dialog;
    private LayoutInflater layoutInflater;
    private WarehousePurchaseVoucherDetail warehousePurchaseVoucherDetail = new
            WarehousePurchaseVoucherDetail();
    List<EditText> list_actqty = new ArrayList<EditText>();
    List<TextView> list_exqty = new ArrayList<TextView>();
    List<TextView> list_badqty = new ArrayList<TextView>();
    private int count = 0;
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
        View view = inflater.inflate(R.layout.warehouse_examinedetail_fragment, null);
        listView = (ListView) view.findViewById(R.id.listView_information);

        Bundle args = getArguments();
        if (args != null && args.getBundle("data") != null) {
            Bundle data = args.getBundle("data");
            whName = data.getString("whName");
            UnitName = data.getString("UnitName");
            code = data.getString("code");
        }
        ((TextView) view.findViewById(R.id.textView_whpcode)).setText(code);
        ((TextView) view.findViewById(R.id.textView_warehouse)).setText(whName);
        ((TextView) view.findViewById(R.id.textView_unitname)).setText(UnitName);
        StringBuffer url = new StringBuffer(MyGlobal.API_WHPURCHASEDETAIL);
        url.append("&whpCode=" + code);

        if (MyGlobal.checkNetworkConnection(getActivity())) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.search_loading));
            dialog.setCancelable(true);
            dialog.show();
            GsonRequest gsonObjRequest = new GsonRequest<WarehousePurchaseVoucherDetail>(Request
                    .Method
                    .GET, url.toString(), WarehousePurchaseVoucherDetail.class,
                    new Response.Listener<WarehousePurchaseVoucherDetail>() {

                        @Override
                        public void onResponse(WarehousePurchaseVoucherDetail response) {
                            dialog.dismiss();

                            if (response.getSuccess().equals("true")) {
                                warehousePurchaseVoucherDetail = response;
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


        ((Button) view.findViewById(R.id.button_warehouse_voucher_examine)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tbl_whPurchaseOrder warehouseVouchersave = new tbl_whPurchaseOrder();
                warehouseVouchersave.setExamineBy(MyGlobal.getUser().getUsername());
                warehouseVouchersave.setwhpCode(code);
                for (int i = 0; i < listView.getCount(); i++) {
                    View v = listView.getAdapter().getView(i,null,null);
                    tbl_whPurchaseOrderDetail data = new tbl_whPurchaseOrderDetail();
                    String itemID = ((TextView) v.findViewById(R.id.textview_item_id)).getText().toString();
                    String ActualQTY = ((EditText) v.findViewById(R.id.textview_item_actqty)).getText().toString();
                    String BADQTY = ((TextView) v.findViewById(R.id.textview_item_badqty)).getText().toString();
                    String ExamineQTY = ((TextView) v.findViewById(R.id
                            .textview_item_exqty)).getText().toString();
                    data.setActualQTY(ActualQTY);
                    data.setitemID(itemID);
                    data.setBADQTY(BADQTY);
                    data.setExamineQTY(ExamineQTY);
                    warehouseVouchersave.gettbl_whPurchaseOrderDetail().add(i, data);
                }
                final String json = new Gson().toJson(warehouseVouchersave);
                if (MyGlobal.checkNetworkConnection(getActivity())) {
                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage(getText(R.string.search_loading));
                    dialog.setCancelable(true);
                    dialog.show();

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                String s = "";
                                List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
                                list.add(new BasicNameValuePair("json", json));
                                s = http1.PostData(list, "/modules/An.Warehouse.Web/Ajax/ArrivalChkQuery.ashx?fn=finishexamin");
                                Message msg = new Message();
                                msg.what=1;
                                Bundle d = new Bundle();
                                d.putString("data", JSONHelper.parseObject(s, ReturnJson.class).getData().get(0).getInfo());
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
           //     StringBuffer url = new StringBuffer(MyGlobal.API_WHPUREXAMINE);
//                url.append("&json=" + json);
//                if (MyGlobal.checkNetworkConnection(getActivity())) {
//                    dialog = new ProgressDialog(getActivity());
//                    dialog.setMessage(getText(R.string.search_loading));
//                    dialog.setCancelable(true);
//                    dialog.show();
//
//                    GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url.toString(), SuccessData.class,
//                            new Response.Listener<SuccessData>() {
//
//                                @Override
//                                public void onResponse(SuccessData response) {
//                                    dialog.dismiss();
//                                    if (response.getSuccess().equals("true")) {
//                                        new AlertDialog.Builder(getActivity()).setMessage(R
//                                                .string.examine_success).setTitle(R.string
//                                                .dialog_hint)
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
            }
         });


        return view;

    }

//    TextWatcher watcher = new TextWatcher() {
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count,
//                                      int after) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            // TODO Auto-generated method stub
//            for (int i = 0; i < list_actqty.size(); i++) {
//                if (!list_actqty.get(i).getText().toString().equals("")) {
//                    list_badqty.get(i).setText(String.valueOf(Integer.parseInt(list_exqty.get(i)
//                            .getText().toString()) - Integer.parseInt(list_actqty.get(i).getText()
//                            .toString())));
//                    if (Integer.parseInt(list_badqty.get(i).getText().toString()) < 0) {
//                        new AlertDialog.Builder(getActivity()).setTitle("提示").setIcon(R.drawable.wrong).setMessage("不合格数不能为负").setNeutralButton("确定",
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).show();
//                    }
//                }
//
//            }
//        }
//    };

    public class SearchListAdapter extends ArrayAdapter<String> {

        public SearchListAdapter(Context c) {
            super(c,
                    R.layout.item_warehouse_purchasevoucher_detail);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final View view = layoutInflater.inflate(R.layout.item_warehouse_purchasevoucher_detail, null);
            ((TextView) view.findViewById(R.id.textview_item_code)).setText
                    (warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().get(position)
                            .getitemCode());
            ((TextView) view.findViewById(R.id.textview_item_name)).setText
                    (warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().get(position)
                            .getitemName());
            ((TextView) view.findViewById(R.id.textview_item_id)).setText
                    (warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().get(position)
                            .getitemID());
            ((TextView) view.findViewById(R.id.textview_item_qty)).setText
                    (warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().get(position)
                            .getQTY());
            ((TextView) view.findViewById(R.id.textview_item_actqty)).setText
                    (warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().get(position).getActualQTY());
            ((TextView) view.findViewById(R.id.textview_item_exqty)).setText
                    (warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().get(position).getExamineQTY());
            ((TextView) view.findViewById(R.id.textview_item_badqty)).setText
                    (warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().get(position).getBADQTY());


            list_actqty.add(((EditText) view.findViewById(R.id.textview_item_actqty)));
            list_exqty.add(((TextView) view.findViewById(R.id.textview_item_exqty)));
            list_badqty.add(((TextView) view.findViewById(R.id.textview_item_badqty)));
          // list_actqty.get(count).addTextChangedListener(watcher);
            count++;
            ((TextView) view.findViewById(R.id.textview_item_actqty)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {


                }

                @Override
                public void afterTextChanged(Editable editable) {
//                for (int i = 0; i < list_actqty.size(); i++) {
//                if (!list_actqty.get(i).getText().toString().equals("")) {
//                    list_badqty.get(i).setText(String.valueOf(Integer.parseInt(list_exqty.get(i)
//                            .getText().toString()) - Integer.parseInt(list_actqty.get(i).getText()
//                            .toString())));
//                    if (Integer.parseInt(list_badqty.get(i).getText().toString()) < 0) {
//                        new AlertDialog.Builder(getActivity()).setTitle("提示").setIcon(R.drawable.wrong).setMessage("不合格数不能为负").setNeutralButton("确定",
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).show();
//                    }
//                }
//
//            }
                    String old =warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().get(position).getActualQTY();

                    String txt=editable.toString();
                    if(txt.length()==0)
                        txt = "0";
                    if(Float.parseFloat(txt) !=Float.parseFloat(old)) {
                        String exqty=warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().get(position).getExamineQTY();
                        if(Float.parseFloat(exqty)<Float.parseFloat(txt))
                        {
                            new AlertDialog.Builder(getActivity()).setTitle("提示").setIcon(R.drawable.wrong).setMessage("不合格数不能为负").setNeutralButton("确定",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        else {
                            float bqty=Float.parseFloat(exqty)-Float.parseFloat(txt);
                            ((TextView) view.findViewById(R.id.textview_item_badqty)).setText
                                    (String.valueOf(bqty));
                            warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().get(position).setBADQTY(String.valueOf(bqty));
                            warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().get(position).setActualQTY(txt);
                        }

                    }
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return warehousePurchaseVoucherDetail.getWarehousePurchaseVoucherDetailInfo().size();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listView.setItemChecked(i, true);
    }


}
package com.luyuan.mobile.production;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.model.WarehousePurchaseVoucherDetail;
import com.luyuan.mobile.model.tbl_QuaPurVoucher;
import com.luyuan.mobile.model.tbl_whPurchaseOrder;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import java.net.URLEncoder;

public class WarehouseVoucherExamineItemCreateDetailFragment extends Fragment {

    private ListView listView;
    private String whName;
    private String UnitName;
    private String supUnitID;
    private String code;
    private ProgressDialog dialog;
    private LayoutInflater layoutInflater;
    private WarehousePurchaseVoucherDetail warehousePurchaseVoucherDetail = new
            WarehousePurchaseVoucherDetail();
    private int count = 0;
    private Spinner spinner;
    private String[] itemValues;
    private tbl_whPurchaseOrder purchaseorder;
    private RadioButton good, bad;
    private String[] values;
    private String[] valuesqty;
    private String[] valuesitemcode;
    private EditText zg, rb, th, remark, result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.warehouse_examineitemdetail_fragment, null);

        Bundle args = getArguments();
        if (args != null && args.getBundle("data") != null) {
            Bundle data = args.getBundle("data");
            UnitName = data.getString("UnitName");
            code = data.getString("code");
            supUnitID = data.getString("UnitID");
        }
        ((TextView) view.findViewById(R.id.textView_whpcode)).setText(code);
        ((TextView) view.findViewById(R.id.textView_unitname)).setText(UnitName);
        good = (RadioButton) view.findViewById(R.id.radio_good);
        bad = (RadioButton) view.findViewById(R.id.radio_bad);
        remark = (EditText) view.findViewById(R.id.textView_remark);
        result = (EditText) view.findViewById(R.id.textView_result);
        zg = ((EditText) view.findViewById(R.id.textView_zg));
        rb = ((EditText) view.findViewById(R.id.textView_rb));
        th = ((EditText) view.findViewById(R.id.textView_th));
        spinner = (Spinner) view.findViewById(R.id.spinner_item);
        StringBuffer url = new StringBuffer(MyGlobal.API_EXAMINEITEM_QUERY);
        url.append("&whpCode=" + code);
        if (MyGlobal.checkNetworkConnection(getActivity())) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.search_loading));
            dialog.setCancelable(true);
            dialog.show();

            GsonRequest gsonObjRequest = new GsonRequest<tbl_whPurchaseOrder>(Request.Method.GET, url.toString(), tbl_whPurchaseOrder.class,
                    new Response.Listener<tbl_whPurchaseOrder>() {

                        @Override
                        public void onResponse(tbl_whPurchaseOrder response) {
                            dialog.dismiss();
                            // purchaseorder = response;
                            if (response.getSuccess().equals("true")) {

                                int size = response.getdata().size();
                                values = new String[size];
                                valuesqty = new String[size];
                                itemValues = new String[size];
                                valuesitemcode = new String[size];
                                for (int i = 0; i < size; i++) {
                                    values[i] = response.getdata().get(i)
                                            .getitemID();
                                    valuesqty[i] = response.getdata().get(i)
                                            .getExamineQTY();
                                    valuesitemcode[i] = response.getdata().get(i)
                                            .getitemCode();
                                    itemValues[i] = response.getdata().get(i)
                                            .getitemCode() + "|" + response.getdata().get(i)
                                            .getExamineQTY() + response.getdata().get(i)
                                            .getInventoryUOM();
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, itemValues);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(adapter);
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


        ((Button) view.findViewById(R.id.button_warehouse_voucher_examine)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = spinner.getSelectedItemPosition();
                String ItemID = values[pos];
                String exqty = valuesqty[pos];
                String itemCode = valuesitemcode[pos];
                String chk = "";
                if (bad.isChecked()) {
                    chk = "false";
                    if (Integer.parseInt(zg.getText().toString()) == 0 && Integer.parseInt(rb.getText
                            ().toString()) == 0 && Integer.parseInt(th.getText().toString()) == 0) {
                        dialog.dismiss();

                        new AlertDialog.Builder(getActivity()).setMessage("单据检验为不合格," +
                                "请输入重工数或让步数或退货数").setTitle(R.string
                                .dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null).create().show();
                        return;
                    }

                }
                if (good.isChecked()) {
                    chk = "true";
                    if (Integer.parseInt(zg.getText().toString()) != 0 || Integer.parseInt(rb.getText
                            ().toString()) != 0 || Integer.parseInt(th.getText().toString()) != 0) {
                        dialog.dismiss();

                        new AlertDialog.Builder(getActivity()).setMessage("单据检验为合格," +
                                "重工数让步数退货数皆应为0").setTitle(R.string
                                .dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null).create().show();
                        return;
                    }
                }
                //  SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   " + "hh:mm:ss");
                tbl_QuaPurVoucher data = new tbl_QuaPurVoucher();
                //  data.setVoucherCode("BGD000537");
                //data.setUnitID(MyGlobal.getUser().getunitId());
                // data.setSupUnitID(supUnitID);
                data.setPurVoucherCode(code);
                //data.setQPISID("4D1A9338-F337-423F-9D91-3CB68C00424D");
                data.setItemID(ItemID);
                //data.setitemCode(itemCode);
                // data.setQuaQuantity("0");
                // data.setPurQuantity(exqty);
                // data.setCreatedOn(sDateFormat.format(new java.util.Date()));
                data.setRemark(URLEncoder.encode(remark.getText().toString()));
                data.setFinish(URLEncoder.encode(result.getText().toString()));
                data.setIsQualified(chk);
                //data.setQuaBy(MyGlobal.getUser().getUsername());
                // data.setAuditBy(MyGlobal.getUser().getUsername());
                data.setCreatedBy(MyGlobal.getUser().getUsername());
                //data.setRatifyBy("");
                //data.setSortingQty("false");
                data.setHeavyQty(zg.getText().toString());
                data.setConQty(rb.getText().toString());
                data.setRetQty(th.getText().toString());
                //data.setHeID("00000000-0000-0000-0000-000000000000");

                String json = new Gson().toJson(data);
                StringBuffer url = new StringBuffer(MyGlobal.API_QUAPURCREATE);
                url.append("&json=" + json);
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
                                        new AlertDialog.Builder(getActivity()).setMessage(R
                                                .string.examine_success).setTitle(R.string
                                                .dialog_hint)
                                                .setPositiveButton(R.string.dialog_confirm, null).create().show();

                                    } else {
                                        new AlertDialog.Builder(getActivity()).setMessage(response.getData().get(0).getInfo()).setTitle(R.string.dialog_hint)
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

            }
        });


        good.setOnClickListener(hegeoncilck);
        bad.setOnClickListener(buhegeoncilck);
        return view;

    }

    OnClickListener hegeoncilck = new OnClickListener() {
        public void onClick(View v) {
            rb.setText("0");
            zg.setText("0");
            th.setText("0");
            bad.setChecked(false);
        }
    };
    OnClickListener buhegeoncilck = new OnClickListener() {
        public void onClick(View v) {
            good.setChecked(false);
        }
    };


}
package com.luyuan.mobile.production;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Dialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.model.WarehouseVoucheritemList;
import com.luyuan.mobile.model.tbl_whPurchaseOrder;
import com.luyuan.mobile.model.tbl_whPurchaseOrderDetail;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
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
    private WarehouseVoucheritemList warehouseVoucheritemList=new WarehouseVoucheritemList();
  //  StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_PURCHASEORDER_QUERY);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.warehouse_voucher_confirm_fragment, null);
        listView = (ListView) view.findViewById(R.id.listview_warehouse_voucher_confirm_list);
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
                String[] part=code.split("-");
                warehouseVouchersave.setOtherOrderCode(part[0]);
                warehouseVouchersave.setBatch(part[1]);
                for (int i=0;i< listView.getChildCount();i++)
                {
                    tbl_whPurchaseOrderDetail data = new tbl_whPurchaseOrderDetail();
                    String itemID=((TextView) listView.getChildAt(i).findViewById(R.id.textview_item_id)).getText().toString();
                    String ExamineQTY=((TextView) listView.getChildAt(i).findViewById(R.id.edittext_confirm_num)).getText().toString();
                    String itemCount=((TextView) listView.getChildAt(i).findViewById(R.id.textview_order_num)).getText().toString();
                    data.setExamineQTY(ExamineQTY);
                    data.setitemID(itemID);
                    data.setitemCount(itemCount);
                    warehouseVouchersave.gettbl_whPurchaseOrderDetail().add(i,data);
                }
                String json= new Gson().toJson(warehouseVouchersave);
                StringBuffer url = new StringBuffer(MyGlobal.API_WHPURSAVE);
                url.append("&json=" +json);
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
                                        new AlertDialog.Builder(getActivity()).setMessage(R.string.save_success).setTitle(R.string.dialog_hint)
                                                .setPositiveButton(R.string.dialog_confirm, null).create().show();

                                    }
                                    else
                                    {
                                        new AlertDialog.Builder(getActivity()).setMessage(response.getData().get(0).getInfo()).setTitle(R.string.dialog_hint)
                                                .setPositiveButton(R.string.dialog_confirm, null).create().show();
                                    }
                                }

                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse( VolleyError error) {
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

        ((Button) view.findViewById(R.id.button_warehouse_voucher_check)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 StringBuffer url = new StringBuffer(MyGlobal.API_WHPURSUBMIT);
                url.append("&whpCode=" +code);
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

                                    }
                                    else
                                    {
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
            code=data.getString("code");
            whID=data.getString("whID");
            unitID=data.getString("unitID");
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
                        public void onResponse( WarehouseVoucheritemList response) {
                            dialog.dismiss();

                            if (response.getSuccess().equals("true")) {
	                            if(response.getWarehouseVoucheritemDetailList().size()==0)
	                            {
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

        public SearchListAdapter( Context c) {
            super(c, R.layout.item_warehouse_voucher_search);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_warehouse_voucher_confirm, null);
            ((TextView) view.findViewById(R.id.textview_item_code)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getitemCode());
            ((TextView) view.findViewById(R.id.textview_order_num)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getQTY());
            ((TextView) view.findViewById(R.id.textview_delivery_num)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getQTY());
            ((TextView) view.findViewById(R.id.textview_item_spec)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getitemSpec());
            ((TextView) view.findViewById(R.id.edittext_confirm_num)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getExamineQTY());
            ((TextView) view.findViewById(R.id.textview_item_id)).setText(warehouseVoucheritemList.getWarehouseVoucheritemDetailList().get(position).getitemID());
            ((TextView) view.findViewById(R.id.textview_item_id)).setVisibility(View.GONE);
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
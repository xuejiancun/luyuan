package com.luyuan.mobile.production;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.model.WarehouseLocationInventoryData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import java.net.URLEncoder;

public class WarehouseLocationInventorySearchFragment extends Fragment implements AdapterView.OnItemClickListener {

    private LayoutInflater layoutInflater;
    private ListView listView;
    private ProgressDialog dialog;
    private String query;
    private WarehouseLocationInventoryData warehouseLocationInventoryData;
    private int npos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        View view = layoutInflater.inflate(R.layout.warehouse_voucher_search_fragment, null);
        final ListView listView = (ListView) view.findViewById(R.id.listview_search_list);

        Bundle args = getArguments();
        if (args != null) {
            query = args.getString("api");
        }
        StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_LOCATIONINVENTORY_QUERY);
        url.append("&UnitID=" + MyGlobal.getUser().getUnitId() + "&wbName=" + URLEncoder.encode(query));
        if (MyGlobal.checkNetworkConnection(getActivity())) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.search_loading));
            dialog.setCancelable(true);
            dialog.show();
            GsonRequest gsonObjRequest = new GsonRequest<WarehouseLocationInventoryData>(Request.Method.GET, url.toString(), WarehouseLocationInventoryData.class,
                    new Response.Listener<WarehouseLocationInventoryData>() {

                        @Override
                        public void onResponse(WarehouseLocationInventoryData response) {
                            dialog.dismiss();
                            if (response.getSuccess().equals("true")) {
                                warehouseLocationInventoryData = response;

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
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
                String freezeqty = warehouseLocationInventoryData
                        .getWarehouseLocationInventoryDetail().get(position).getFreezeQty();
                final String wbIDDetail = warehouseLocationInventoryData
                        .getWarehouseLocationInventoryDetail().get(position).getwbIDDetail();
                npos = position;
                if (Integer.parseInt(freezeqty) != 0) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.dialog_hint)
                            .setMessage("车型" + warehouseLocationInventoryData
                                    .getWarehouseLocationInventoryDetail().get(position).getProductCode() + "尚有冻结数量，无法删除！")
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .show();
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.dialog_hint)
                            .setMessage(R.string.ifdel)
                            .setNegativeButton(R.string.dialog_cancel,
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {

                                        }
                                    })
                            .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogm, int which) {
                                    StringBuffer url = new StringBuffer(MyGlobal
                                            .API_WHLOCATIONINVENTORYDEL);
                                    url.append("&wbIDDetail=" + wbIDDetail + "&UnitID=" + MyGlobal.getUser().getUnitId());
                                    if (MyGlobal.checkNetworkConnection(getActivity())) {
                                        dialog = new ProgressDialog(getActivity());
                                        dialog.setMessage(getText(R.string.do_loading));
                                        dialog.setCancelable(true);
                                        dialog.show();
                                        GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url.toString(), SuccessData.class,
                                                new Response.Listener<SuccessData>() {

                                                    @Override
                                                    public void onResponse(SuccessData response) {
                                                        dialog.dismiss();
                                                        if (response.getSuccess().equals("true")) {
                                                            new AlertDialog.Builder(getActivity()).setMessage(R.string.delsuccess).setTitle(R.string.dialog_hint)
                                                                    .setPositiveButton(R.string.dialog_confirm, null).create().show();
                                                            warehouseLocationInventoryData
                                                                    .getWarehouseLocationInventoryDetail().remove(npos);
                                                            listView.setAdapter(new SearchListAdapter(getActivity()));
                                                        } else {
                                                            new AlertDialog.Builder(getActivity()
                                                            ).setMessage(response.getData().get(0).getInfo()).setTitle
                                                                    (R.string.dialog_hint)
                                                                    .setPositiveButton(R.string.dialog_confirm, null).create().show();
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
                                }
                            }).show();
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        WarehouseLocationInventoryModifyFragment warehouseLocationInventoryModifyFragment = new
                WarehouseLocationInventoryModifyFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        Bundle data = new Bundle();

        data.putString("wbID", warehouseLocationInventoryData.getWarehouseLocationInventoryDetail
                ().get(i).getwbID());
        data.putString("wbIDDetail", warehouseLocationInventoryData
                .getWarehouseLocationInventoryDetail().get(i).getwbIDDetail());
        data.putString("Qty", warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get(i).getQty());
        data.putString("ProductCode", warehouseLocationInventoryData
                .getWarehouseLocationInventoryDetail().get(i).getProductCode());
        data.putString("wbName", warehouseLocationInventoryData
                .getWarehouseLocationInventoryDetail().get(i).getwbName());

        data.putString("SpecType", warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get(i).getSpecType());
        data.putString("PrefixName", warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get(i).getPrefixName());
        args.putBundle("data", data);
        warehouseLocationInventoryModifyFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, warehouseLocationInventoryModifyFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public class SearchListAdapter extends ArrayAdapter<String> {

        public SearchListAdapter(Context c) {
            super(c, R.layout
                    .item_warehouse_locationinventory_search);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_warehouse_locationinventory_search, null);

            ((TextView) view.findViewById(R.id.textview_warehouse_wbname)).setText
                    (warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get(position).getwbName());
            ((TextView) view.findViewById(R.id.textview_warehouse_qty)).setText
                    (warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get
                            (position).getQty());
            ((TextView) view.findViewById(R.id.textview_warehouse_freezeqty)).setText(warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get
                    (position).getFreezeQty());
            ((TextView) view.findViewById(R.id.textview_warehouse_productcode)).setText
                    (warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get
                            (position).getProductCode());

            ((TextView) view.findViewById(R.id.textview_warehouse_spectype)).setText
                    (warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get
                            (position).getSpecType());

            ((TextView) view.findViewById(R.id.textview_warehouse_prefixname)).setText
                    (warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get
                            (position).getPrefixName());
            ((TextView) view.findViewById(R.id.textview_warehouse_wbid)).setText
                    (warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get
                            (position).getwbID());
            ((TextView) view.findViewById(R.id.textview_warehouse_wbiddetail)).setText
                    (warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get
                            (position).getwbIDDetail());
            return view;
        }

        @Override
        public int getCount() {
            return warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().size();
        }

    }

}
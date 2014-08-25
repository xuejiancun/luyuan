package com.luyuan.mobile.function;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.WarehouseVoucherData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class WarehouseVoucherSearchFragment extends Fragment implements AdapterView.OnItemClickListener {

    private LayoutInflater layoutInflater;
    private ListView listView;
    private ProgressDialog dialog;
    private WarehouseVoucherData warehouseVoucherData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        View view = layoutInflater.inflate(R.layout.warehouse_voucher_search_fragment, null);
        final ListView listView = (ListView) view.findViewById(R.id.listview_search_list);

        StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_PURCHASEORDER_QUERY);
        url.append("&UnitID=" + MyGlobal.getUser().getUnitId());
        if (MyGlobal.checkNetworkConnection(getActivity())) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.loading));
            dialog.setCancelable(true);
            dialog.show();
            GsonRequest gsonObjRequest = new GsonRequest<WarehouseVoucherData>(Request.Method.GET, url.toString(), WarehouseVoucherData.class,
                    new Response.Listener<WarehouseVoucherData>() {

                        @Override
                        public void onResponse(WarehouseVoucherData response) {
                            dialog.dismiss();

                            if (response.getSuccess().equals("true")) {
                                warehouseVoucherData = response;
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        WarehouseVoucherMakeFragment warehouseVoucherMakeFragment = new WarehouseVoucherMakeFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        Bundle data = new Bundle();
        data.putString("code", warehouseVoucherData.getWarehouseVoucherInfos().get(i).getCode());
        data.putString("preparedBy", warehouseVoucherData.getWarehouseVoucherInfos().get(i).getPreparedBy());
        data.putString("unitName", warehouseVoucherData.getWarehouseVoucherInfos().get(i).getUnitLongName());
        data.putString("warehouseName", warehouseVoucherData.getWarehouseVoucherInfos().get(i).getwareHouseName());
        args.putBundle("data", data);
        warehouseVoucherMakeFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, warehouseVoucherMakeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public class SearchListAdapter extends ArrayAdapter<String> {

        public SearchListAdapter(Context c) {
            super(c, R.layout.item_warehouse_voucher_search);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_warehouse_voucher_search, null);

            ((TextView) view.findViewById(R.id.textview_order_no)).setText(warehouseVoucherData.getWarehouseVoucherInfos().get(position).getCode());
            ((TextView) view.findViewById(R.id.textview_order_time)).setText(warehouseVoucherData.getWarehouseVoucherInfos().get(position).getPreparedTime());
            ((TextView) view.findViewById(R.id.textview_order_unit)).setText(warehouseVoucherData.getWarehouseVoucherInfos().get(position).getUnitLongName());

            return view;
        }

        @Override
        public int getCount() {
            return warehouseVoucherData.getWarehouseVoucherInfos().size();
        }

    }

}
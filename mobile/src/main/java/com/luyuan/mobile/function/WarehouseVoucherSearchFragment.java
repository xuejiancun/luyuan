package com.luyuan.mobile.function;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.model.WarehouseVoucherData;
import com.luyuan.mobile.model.WarehouseVoucherInfo;

import java.util.ArrayList;

public class WarehouseVoucherSearchFragment extends Fragment implements AdapterView.OnItemClickListener {

    private LayoutInflater layoutInflater;
    private ListView listView;
    private WarehouseVoucherData warehouseVoucherData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        View view = layoutInflater.inflate(R.layout.fragment_warehouse_voucher_search, null);
        final ListView listView = (ListView) view.findViewById(R.id.listview_warehouse_voucher_search_list);

        // testing code
        warehouseVoucherData = new WarehouseVoucherData();
        WarehouseVoucherInfo warehouseVoucherInfo = new WarehouseVoucherInfo();
        warehouseVoucherInfo.setCode("PO330000001");
        warehouseVoucherInfo.setPreparedTime("2014/4/4 19:32:33");
        warehouseVoucherInfo.setPreparedBy("Jack");
        warehouseVoucherInfo.setUnitName("温州立邦企业有限公司");

        WarehouseVoucherInfo warehouseVoucherInfo2 = new WarehouseVoucherInfo();
        warehouseVoucherInfo2.setCode("PO330000002");
        warehouseVoucherInfo2.setPreparedTime("2014/4/4 19:32:33");
        warehouseVoucherInfo2.setPreparedBy("Bill");
        warehouseVoucherInfo2.setUnitName("温州立邦企业有限公司");

        ArrayList<WarehouseVoucherInfo> warehouseVoucherInfos = new ArrayList<WarehouseVoucherInfo>();
        warehouseVoucherInfos.add(warehouseVoucherInfo);
        warehouseVoucherInfos.add(warehouseVoucherInfo2);

        warehouseVoucherData.setWarehouseVoucherInfos(warehouseVoucherInfos);

        listView.setAdapter(new SearchListAdapter(getActivity()));
        listView.setOnItemClickListener(this);

//        String api = "";
//        Bundle args = getArguments();
//        if (args != null && args.getString(MyGlobal.PARAM_API_URL) != null) {
//            // api = args.getString(MyGlobal.PARAM_API_URL);
//            api = MyGlobal.API_WAREHOUSE_VOUCHER_SEARCH + "&Status=0&pageIndex=0&pageSize=50";
//
//            if (!api.isEmpty() && MyGlobal.checkNetworkConnection(getActivity())) {
//
//                GsonRequest gsonObjRequest = new GsonRequest<WarehouseVoucherData>(Request.Method.GET, api,
//                        WarehouseVoucherData.class, new Response.Listener<WarehouseVoucherData>() {
//                    @Override
//                    public void onResponse(WarehouseVoucherData response) {
//                        if (response != null && response.getSuccess().equals("true")) {
//                            // TODO
//                            listView.setAdapter(new SearchListAdapter(getActivity()));
//
//                        } else {
//                            Dialog alertDialog = new AlertDialog.Builder(getActivity())
//                                    .setMessage(R.string.fetch_data_error)
//                                    .setTitle(R.string.dialog_hint)
//                                    .setPositiveButton(R.string.dialog_confirm, null)
//                                    .create();
//                            alertDialog.show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Dialog alertDialog = new AlertDialog.Builder(getActivity())
//                                .setMessage(R.string.fetch_data_error)
//                                .setTitle(R.string.dialog_hint)
//                                .setPositiveButton(R.string.dialog_confirm, null)
//                                .create();
//                        alertDialog.show();
//                    }
//                }
//                );
//
//                RequestManager.getRequestQueue().add(gsonObjRequest);
//                gsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        MyGlobal.CONNECTION_TIMEOUT_MS,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            }
//
//        } else {
//            Dialog alertDialog = new AlertDialog.Builder(getActivity())
//                    .setMessage(R.string.app_param_error)
//                    .setTitle(R.string.dialog_hint)
//                    .setPositiveButton(R.string.dialog_confirm, null)
//                    .create();
//            alertDialog.show();
//        }

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
        data.putString("unitName", warehouseVoucherData.getWarehouseVoucherInfos().get(i).getUnitName());
        args.putBundle("data", data);
        warehouseVoucherMakeFragment.setArguments(args);


        fragmentTransaction.replace(R.id.frame_content_warehouse_voucher_manager, warehouseVoucherMakeFragment);
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

            ((TextView) view.findViewById(R.id.textview_order_no_warehouse_voucher_search)).setText(warehouseVoucherData.getWarehouseVoucherInfos().get(position).getCode());
            ((TextView) view.findViewById(R.id.textview_order_time_warehouse_voucher_search)).setText(warehouseVoucherData.getWarehouseVoucherInfos().get(position).getPreparedTime());
            ((TextView) view.findViewById(R.id.textview_order_unit_warehouse_voucher_search)).setText(warehouseVoucherData.getWarehouseVoucherInfos().get(position).getUnitName());

            return view;
        }

        @Override
        public int getCount() {
            return warehouseVoucherData.getWarehouseVoucherInfos().size();
        }

    }

}
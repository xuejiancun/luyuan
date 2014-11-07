package com.luyuan.mobile.production;

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
import com.luyuan.mobile.model.WarehouseNewVoucherData;
import com.luyuan.mobile.model.WarehouseVoucherData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class WarehousePurVoucherSearchFragment extends Fragment implements AdapterView.OnItemClickListener {

	private LayoutInflater layoutInflater;
	private ListView listView;
	private ProgressDialog dialog;
    private String query;
    private String unitName;
    private String unitID;
	private WarehouseVoucherData warehouseVoucherData;
    private WarehouseNewVoucherData warehouseNewVoucherData;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layoutInflater = inflater;
		View view = layoutInflater.inflate(R.layout.warehouse_voucher_search_fragment, null);
		final ListView listView = (ListView) view.findViewById(R.id.listview_search_list);

        Bundle args = getArguments();
        if (args != null ) {
            query=args.getString("api");
        }
		StringBuffer url = new StringBuffer(MyGlobal.API_PURORDER_QUERY);
		url.append("&OrderCode="+query+"&InUnitID="+ MyGlobal.getUser().getUnitId());
		if (MyGlobal.checkNetworkConnection(getActivity())) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.search_loading));
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
        if (MyGlobal.checkNetworkConnection(getActivity()))
        {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.search_loading));
            dialog.setCancelable(true);
            dialog.show();
            unitName= warehouseVoucherData.getWarehouseVoucherInfos().get(i).getUnitLongName();
            unitID=warehouseVoucherData.getWarehouseVoucherInfos().get(i).getUnitId();
            StringBuffer url = new StringBuffer(MyGlobal.API_PURORDER_ADD);
            url.append("&OrderCode="+warehouseVoucherData.getWarehouseVoucherInfos().get(i).getCode());

             GsonRequest gsonObjRequest = new GsonRequest<WarehouseNewVoucherData>(Request.Method.GET, url.toString(), WarehouseNewVoucherData.class,
                    new Response.Listener<WarehouseNewVoucherData>() {

                        @Override
                        public void onResponse( WarehouseNewVoucherData response) {
                            dialog.dismiss();

                            if (response.getSuccess().equals("true")) {
                                warehouseNewVoucherData = response;
                                WarehouseVoucherMakeFragment warehouseVoucherMakeFragment = new WarehouseVoucherMakeFragment();
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                Bundle args = new Bundle();
	                            Bundle data = new Bundle();
                                data.putString("code", warehouseNewVoucherData.getData().get(0).getwhpCode());
                                data.putString("preparedBy", warehouseNewVoucherData.getData().get(0).getPreparedby());
                                data.putString("status", "");
                                data.putString("unitName",unitName);
                                data.putString("whName",warehouseNewVoucherData.getData().get(0)
		                                .getwhCode()+"|"+ warehouseNewVoucherData.getData().get(0)
		                                .getwhName());

                                data.putString("unitID",unitID );
                                data.putString("whID",warehouseNewVoucherData.getData().get(0).getwhID());
                                args.putBundle("data", data);
                                warehouseVoucherMakeFragment.setArguments(args);

                                fragmentTransaction.replace(R.id.frame_content, warehouseVoucherMakeFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();


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

	public class SearchListAdapter extends ArrayAdapter<String> {

		public SearchListAdapter( Context c) {
			super(c, R.layout.item_warehouse_voucher_search);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = layoutInflater.inflate(R.layout.item_warehouse_voucher_search, null);

			((TextView) view.findViewById(R.id.textview_order_no)).setText(warehouseVoucherData.getWarehouseVoucherInfos().get(position).getCode());
			((TextView) view.findViewById(R.id.textview_order_time)).setText(warehouseVoucherData.getWarehouseVoucherInfos().get(position).getDelivery());
			((TextView) view.findViewById(R.id.textview_order_unit)).setText(warehouseVoucherData.getWarehouseVoucherInfos().get(position).getUnitName());

			return view;
		}

		@Override
		public int getCount() {
			return warehouseVoucherData.getWarehouseVoucherInfos().size();
		}

	}

}
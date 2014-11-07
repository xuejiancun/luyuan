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
import com.luyuan.mobile.model.WarehouseInventoryData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class WarehouseInventoryFragment extends Fragment implements AdapterView.OnItemClickListener {

	private LayoutInflater layoutInflater;
	private ListView listView;
	private ProgressDialog dialog;
    private String whID;
	private String itemCode;
	private String itemName;
	private String itemSpec;
	private WarehouseInventoryData warehouseInventoryData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layoutInflater = inflater;
		View view = layoutInflater.inflate(R.layout.warehouse_voucher_search_fragment, null);
		final ListView listView = (ListView) view.findViewById(R.id.listview_search_list);
        Bundle args = getArguments();
		if (args != null && args.getBundle("data") != null) {
			Bundle data = args.getBundle("data");
			itemCode=data.getString("itemCode");
			itemName=data.getString("itemName");
			itemSpec=data.getString("itemSpec");
			whID=data.getString("whID");
        }
		StringBuffer url = new StringBuffer(MyGlobal.API_WHINVENTORY);
		String code= MyGlobal.getUser().getStId();
		url.append("&itemCode="+itemCode+"&itemName="+itemName+"&itemSpec="+itemSpec+"&whID" +
				"="+whID+"&code="+code+"");
		if (MyGlobal.checkNetworkConnection(getActivity())) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.search_loading));
            dialog.setCancelable(true);
            dialog.show();
            GsonRequest gsonObjRequest = new GsonRequest<WarehouseInventoryData>(Request.Method.GET, url.toString(), WarehouseInventoryData.class,
                    new Response.Listener<WarehouseInventoryData>() {

                        @Override
                        public void onResponse( WarehouseInventoryData response) {
                            dialog.dismiss();
                            if (response.getSuccess().equals("true")) {
	                            warehouseInventoryData = response;

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
		WarehouseInventoryDetailFragment warehouseInventoryDetailFragment = new
				WarehouseInventoryDetailFragment();
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		String whID= warehouseInventoryData.getWarehouseInventoryDataInfos().get(i).getwhID();
		String itemID=warehouseInventoryData.getWarehouseInventoryDataInfos().get(i).getitemID();
		Bundle args = new Bundle();
		Bundle data = new Bundle();
		data.putString("itemID", itemID);
		data.putString("whID", whID);
		args.putBundle("data", data);
		warehouseInventoryDetailFragment.setArguments(args);
		fragmentTransaction.replace(R.id.frame_content,warehouseInventoryDetailFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	public class SearchListAdapter extends ArrayAdapter<String> {

		public SearchListAdapter( Context c) {
			super(c, R.layout.item_warehouse_inventory_data);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = layoutInflater.inflate(R.layout.item_warehouse_inventory_data, null);

			((TextView) view.findViewById(R.id.textview_item_code)).setText
					(warehouseInventoryData.getWarehouseInventoryDataInfos().get(position).getitemCode());
			((TextView) view.findViewById(R.id.textview_item_name)).setText(warehouseInventoryData
					.getWarehouseInventoryDataInfos().get(position).getitemName());
			((TextView) view.findViewById(R.id.textview_item_id)).setText(warehouseInventoryData
					.getWarehouseInventoryDataInfos().get(position).getitemID());

			((TextView) view.findViewById(R.id.textview_wh_qty)).setText
					(warehouseInventoryData.getWarehouseInventoryDataInfos().get(position).getQty
							()+warehouseInventoryData.getWarehouseInventoryDataInfos().get
							(position).getInventoryUOM());
			((TextView) view.findViewById(R.id.textview_wh_name)).setText(warehouseInventoryData
					.getWarehouseInventoryDataInfos().get(position).getwhName());
			((TextView) view.findViewById(R.id.textview_wh_id)).setText(warehouseInventoryData
					.getWarehouseInventoryDataInfos().get(position).getwhID());

			return view;
		}

		@Override
		public int getCount() {
			return warehouseInventoryData.getWarehouseInventoryDataInfos().size();
		}

	}

}
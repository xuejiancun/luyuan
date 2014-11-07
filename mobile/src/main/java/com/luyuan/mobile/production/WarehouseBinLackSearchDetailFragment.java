package com.luyuan.mobile.production;

import android.app.Fragment;
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
import com.luyuan.mobile.model.WarehouseLocationInventoryData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class WarehouseBinLackSearchDetailFragment extends Fragment implements AdapterView.OnItemClickListener {

	private LayoutInflater layoutInflater;
	private ListView listView;
	private ProgressDialog dialog;
    private String wbcode,time_s,time_e,date_s,date_e;
	private WarehouseLocationInventoryData warehouseLocationInventoryData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layoutInflater = inflater;
		View view = layoutInflater.inflate(R.layout.warehouse_binlack_search_fragment, null);
		final ListView listView = (ListView) view.findViewById(R.id.listview_search_list);

        Bundle args = getArguments();
		if (args != null && args.getBundle("data") != null) {
			Bundle data = args.getBundle("data");
			wbcode=data.getString("wbcode");
			time_s=data.getString("time_s");
			time_e=data.getString("time_e");
			date_s=data.getString("date_s");
			date_e=data.getString("date_e");
		}
		StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_BINLACKDETAILSEASRCH_QUERY);
		url.append("&UnitID=" + MyGlobal.getUser().getUnitId()
				+"&InDate_s="+date_s+"&InDate_e="+date_e+"&wbName="+wbcode+"&Intime_s="+time_s
				+"&Intime_e="+time_e);
		if (MyGlobal.checkNetworkConnection(getActivity())) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.search_loading));
            dialog.setCancelable(true);
            dialog.show();
            GsonRequest gsonObjRequest = new GsonRequest<WarehouseLocationInventoryData>(Request.Method.GET, url.toString(), WarehouseLocationInventoryData.class,
                    new Response.Listener<WarehouseLocationInventoryData>() {

                        @Override
                        public void onResponse( WarehouseLocationInventoryData response) {
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
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

	}

	public class SearchListAdapter extends ArrayAdapter<String> {

		public SearchListAdapter( Context c) {
			super(c, R.layout.item_warehouse_binlack_data);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = layoutInflater.inflate(R.layout.item_warehouse_binlack_data, null);

			((TextView) view.findViewById(R.id.textview_wbname)).setText
					(warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get
							(position).getwbName());
			((TextView) view.findViewById(R.id.textview_wbid)).setText
					(warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get
							(position).getwbID());
			((TextView) view.findViewById(R.id.textview_wbcode)).setText
					(warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().get
							(position).getwbCode());

			return view;
		}

		@Override
		public int getCount() {
			return warehouseLocationInventoryData.getWarehouseLocationInventoryDetail().size();
		}

	}

}
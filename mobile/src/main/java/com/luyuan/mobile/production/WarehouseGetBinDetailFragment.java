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
import com.luyuan.mobile.model.WarehouseGetBin;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import java.net.URLEncoder;

public class WarehouseGetBinDetailFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
	private String sysbin;
	private String whbin;
	private String api;
	private String wbName;
	private String ProductCode;
	private String PrefixName;
	private String SpecType;
	private String wbID;
	private String wbIDDetail;
	private String Qty;
    private ProgressDialog dialog;
    private LayoutInflater layoutInflater;
    private  WarehouseGetBin warehouseGetBin=new WarehouseGetBin();
	private WarehouseLocationInventoryModifyFragment warehouseLocationInventoryModifyFragment=new
			WarehouseLocationInventoryModifyFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.warehouse_getbindetail_fragment, null);
        listView = (ListView) view.findViewById(R.id.listView_information);

	    Bundle args = getArguments();
	    if (args != null && args.getBundle("data") != null) {
		    Bundle data = args.getBundle("data");
//		    SpecType=data.getString("SpecType");
		    Qty=data.getString("Qty");
		    wbID=data.getString("wbID");
		    wbIDDetail=data.getString("wbIDDetail");
//		    PrefixName=data.getString("PrefixName");
//		    ProductCode=data.getString("ProductCode");
		    wbName = data.getString("wbName");
		    sysbin=data.getString("sysBin");
		    whbin=data.getString("whBin");
	    }

        StringBuffer url = new StringBuffer(MyGlobal.API_WHGETBIN);
        url.append("&wbCode=" + sysbin +"&wbName="+ URLEncoder.encode(whbin)+"&UnitID="+ MyGlobal.getUser().getUnitId());

	    if (MyGlobal.checkNetworkConnection(getActivity())) {
		    dialog = new ProgressDialog(getActivity());
		    dialog.setMessage(getText(R.string.search_loading));
		    dialog.setCancelable(true);
		    dialog.show();
		    GsonRequest gsonObjRequest = new GsonRequest<WarehouseGetBin>(Request.Method.GET,
				    url.toString(), WarehouseGetBin.class,
				    new Response.Listener<WarehouseGetBin>() {

					    @Override
					    public void onResponse(WarehouseGetBin response) {
						    dialog.dismiss();

						    if (response.getSuccess().equals("true")) {
							    warehouseGetBin = response;
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
            super(c, R.layout.item_warehouse_bin_detail);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View view = layoutInflater.inflate(R.layout.item_warehouse_bin_detail, null);
            ((TextView) view.findViewById(R.id.textview_wbcode)).setText
		            (warehouseGetBin.getWarehouseGetBinInfo().get(position)
				            .getWbCode());
	        ((TextView) view.findViewById(R.id.textview_wbname)).setText
			        (warehouseGetBin.getWarehouseGetBinInfo().get(position)
					        .getWbName());
            ((TextView) view.findViewById(R.id.textview_unit)).setText
		            (warehouseGetBin.getWarehouseGetBinInfo().get(position)
				            .getWarehouseCode());
	        ((TextView) view.findViewById(R.id.textview_status)).setText
			        (warehouseGetBin.getWarehouseGetBinInfo().get(position)
					        .getIsDel());
	        ((TextView) view.findViewById(R.id.textview_wbid)).setText
			        (warehouseGetBin.getWarehouseGetBinInfo().get(position)
					        .getWbID());

            return view;
        }

        @Override
        public int getCount() {
            return warehouseGetBin.getWarehouseGetBinInfo().size();
        }

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
	    WarehouseLocationInventoryModifyFragment warehouseLocationInventoryModifyFragment = new
			    WarehouseLocationInventoryModifyFragment();
	    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

	    Bundle args = new Bundle();
	    Bundle data = new Bundle();

	    data.putString("wbID",warehouseGetBin.getWarehouseGetBinInfo().get(i).getWbID());
	    data.putString("wbIDDetail",wbIDDetail);
	    data.putString("Qty", Qty);
	    data.putString("ProductCode", ProductCode);
	    data.putString("wbName",warehouseGetBin.getWarehouseGetBinInfo().get(i).getWbName());

	    data.putString("SpecType", SpecType);
	    data.putString("PrefixName",PrefixName);
	    args.putBundle("data", data);
	    warehouseLocationInventoryModifyFragment.setArguments(args);

	    fragmentTransaction.replace(R.id.frame_content, warehouseLocationInventoryModifyFragment);
	    fragmentTransaction.addToBackStack(null);
	    fragmentTransaction.commit();
    }


}
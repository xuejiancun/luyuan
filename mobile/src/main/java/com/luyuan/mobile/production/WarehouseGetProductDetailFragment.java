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
import com.luyuan.mobile.model.WarehouseGetProduct;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class WarehouseGetProductDetailFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
	private String itemName;
	private String itemCode;
	private String itemSpec;
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
    private  WarehouseGetProduct warehouseGetProduct=new WarehouseGetProduct();
	private WarehouseLocationInventoryModifyFragment warehouseLocationInventoryModifyFragment=new
			WarehouseLocationInventoryModifyFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.warehouse_getproductdetail_fragment, null);
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
		    itemSpec=data.getString("itemSpec");
		    itemName=data.getString("itemName");
		    itemCode = data.getString("itemCode");
	    }

        StringBuffer url = new StringBuffer(MyGlobal.API_WHGETPRODUCTDETAIL);
        url.append("&ProductCode=" + itemCode +"&PrefixName="+itemName+"&SpecType="+itemSpec);

	    if (MyGlobal.checkNetworkConnection(getActivity())) {
		    dialog = new ProgressDialog(getActivity());
		    dialog.setMessage(getText(R.string.search_loading));
		    dialog.setCancelable(true);
		    dialog.show();
		    GsonRequest gsonObjRequest = new GsonRequest<WarehouseGetProduct>(Request.Method.GET,
				    url.toString(), WarehouseGetProduct.class,
				    new Response.Listener<WarehouseGetProduct>() {

					    @Override
					    public void onResponse(WarehouseGetProduct response) {
						    dialog.dismiss();

						    if (response.getSuccess().equals("true")) {
							    warehouseGetProduct = response;
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
            super(c, R.layout.item_warehouse_product_detail);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View view = layoutInflater.inflate(R.layout.item_warehouse_product_detail, null);
            ((TextView) view.findViewById(R.id.textview_code)).setText
		            (warehouseGetProduct.getWarehouseGetProductInfo().get(position)
				            .getProductCode());
	        ((TextView) view.findViewById(R.id.textview_name)).setText
			        (warehouseGetProduct.getWarehouseGetProductInfo().get(position)
					        .getPrefixName());
            ((TextView) view.findViewById(R.id.textview_spec)).setText
		            (warehouseGetProduct.getWarehouseGetProductInfo().get(position)
				            .getSpecType());
	        ((TextView) view.findViewById(R.id.textview_uom)).setText
			        (warehouseGetProduct.getWarehouseGetProductInfo().get(position)
					        .getUnit());


            return view;
        }

        @Override
        public int getCount() {
            return warehouseGetProduct.getWarehouseGetProductInfo().size();
        }

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
	    WarehouseLocationInventoryModifyFragment warehouseLocationInventoryModifyFragment = new
			    WarehouseLocationInventoryModifyFragment();
	    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

	    Bundle args = new Bundle();
	    Bundle data = new Bundle();

	    data.putString("wbID",wbID);
	    data.putString("wbIDDetail",wbIDDetail);
	    data.putString("Qty", Qty);
	    data.putString("ProductCode", warehouseGetProduct.getWarehouseGetProductInfo().get(i).getProductCode());
	    data.putString("wbName",wbName);

	    data.putString("SpecType", warehouseGetProduct.getWarehouseGetProductInfo().get(i).getSpecType());
	    data.putString("PrefixName", warehouseGetProduct.getWarehouseGetProductInfo().get(i).getPrefixName());
	    args.putBundle("data", data);
	    warehouseLocationInventoryModifyFragment.setArguments(args);

	    fragmentTransaction.replace(R.id.frame_content, warehouseLocationInventoryModifyFragment);
	    fragmentTransaction.addToBackStack(null);
	    fragmentTransaction.commit();
    }


}
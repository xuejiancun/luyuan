package com.luyuan.mobile.production;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class WarehouseLocationInventoryModifyFragment extends Fragment {

    private ListView listView;
	private ProgressDialog dialog;
	private String wbName;
	private String ProductCode;
	private String PrefixName;
	private String SpecType;
	private String wbID;
	private String wbIDDetail;
	private String Qty;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.warehouse_locationinventorymodify_fragment,null);

	    Bundle args = getArguments();
	    if (args != null && args.getBundle("data") != null) {
		    Bundle data = args.getBundle("data");
		    SpecType=data.getString("SpecType");
		    Qty=data.getString("Qty");
		    wbID=data.getString("wbID");
		    wbIDDetail=data.getString("wbIDDetail");
		    PrefixName=data.getString("PrefixName");
		    ProductCode=data.getString("ProductCode");
		    wbName = data.getString("wbName");
	    }
	    ((TextView) view.findViewById(R.id.textview_wbid)).setText(wbID);
	    ((TextView) view.findViewById(R.id.textview_wbiddetail)).setText(wbIDDetail);
	    ((TextView) view.findViewById(R.id.textview_product_spec)).setText(SpecType);
	    ((TextView) view.findViewById(R.id.textview_product_code)).setText(ProductCode);
	    ((TextView) view.findViewById(R.id.textview_product_name)).setText(PrefixName);
	    ((TextView) view.findViewById(R.id.textview_product_qty)).setText(Qty);
	    ((TextView) view.findViewById(R.id.textview_wbname)).setText(wbName);
	    ((Button) view.findViewById(R.id.button_whbin)).setClickable(false);
        ((Button) view.findViewById(R.id.button_product)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewContent) {
	            String qty = ((TextView) view.findViewById(R.id.textview_product_qty)).getText()
			            .toString().trim();
	            WarehouseGetProductFragment warehouseGetProductFragment  = new
			            WarehouseGetProductFragment();
	            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
	            Bundle args = new Bundle();
	            Bundle data = new Bundle();
	            data.putString("wbID", wbID);
	            data.putString("wbIDDetail", wbIDDetail);
	            data.putString("Qty", qty);
	            data.putString("ProductCode", ProductCode);
	            data.putString("wbName", wbName);

	            data.putString("SpecType", SpecType);
	            data.putString("PrefixName", PrefixName);
	            args.putBundle("data", data);
	            warehouseGetProductFragment.setArguments(args);

	            fragmentTransaction.replace(R.id.frame_content, warehouseGetProductFragment);
	            fragmentTransaction.addToBackStack(null);
	            fragmentTransaction.commit();
            }
        });
	    ((Button) view.findViewById(R.id.button_ok)).setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View viewContent) {
			    String wbid = ((TextView) view.findViewById(R.id.textview_wbid)).getText()
					    .toString().trim();
			    String wbiddetail = ((TextView) view.findViewById(R.id.textview_wbiddetail))
					    .getText()
					    .toString().trim();
			    String qty = ((TextView) view.findViewById(R.id.textview_product_qty)).getText()
					    .toString().trim();
			    String productcode = ((TextView) view.findViewById(R.id.textview_product_code))
					    .getText()
					    .toString().trim();
			    String unitid = MyGlobal.getUser().getUnitId();
			    StringBuffer url = new StringBuffer(MyGlobal.API_WHLOCATIONINVENTORYSAVE);
			    url.append("&wbID=" +wbid+"&wbIDDetail="+wbiddetail+"&UnitID="+unitid+"&Qty="+qty
					    +"&ProductCode="+productcode);
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
		    }
	    });
	    return view;
    }


}
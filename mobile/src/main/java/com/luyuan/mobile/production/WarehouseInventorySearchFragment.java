package com.luyuan.mobile.production;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.PurwhunitData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class WarehouseInventorySearchFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
	private ProgressDialog dialog;
	private PurwhunitData whUnit;
	private String[] widValues;
	private Spinner wspinner;
	private String itemname;
	private String itemcode;
	private String itemspec;
	private String whid;
	private int wPosition;
	private String api;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.warehouse_inventorybook_fragment, null);
	    wspinner = (Spinner)view.findViewById(R.id.spinner_whname);
	    StringBuffer url = new StringBuffer(MyGlobal.API_UNITNAME_QUERY);
	    url.append("&UnitID=" + MyGlobal.getUser().getUnitId());
	    if (MyGlobal.checkNetworkConnection(getActivity())) {
		    dialog = new ProgressDialog(getActivity());
		    dialog.setMessage(getText(R.string.search_loading));
		    dialog.setCancelable(true);
		    dialog.show();

		    GsonRequest gsonObjRequest = new GsonRequest<PurwhunitData>(Request.Method.GET, url.toString(), PurwhunitData.class,
				    new Response.Listener<PurwhunitData>() {

					    @Override
					    public void onResponse(PurwhunitData response) {
						    dialog.dismiss();
						    whUnit = response;
						    if (response.getSuccess().equals("true")) {
							    int sizew=response.getwhInfo().size();
							    String[] valuesw= new String[sizew];
							    widValues=new String[sizew];
							    for(int i=0;i<sizew;i++)
							    {
								    valuesw[i]=response.getwhInfo().get(i).whName;
								    widValues[i]=response.getwhInfo().get(i).whID;
							    }
							    ArrayAdapter<String> adapterw = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item ,valuesw);
							    adapterw.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
							    wspinner.setAdapter(adapterw);
							    wPosition=0;
							    wspinner.setSelection(wPosition);


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
        //listView = (ListView) view.findViewById(R.id.listview_warehouse_choose_list);
        ((Button) view.findViewById(R.id.button_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewContent) {
	            WarehouseInventoryFragment warehouseInventoryFragment = new
			            WarehouseInventoryFragment();
	            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
	            itemname = ((EditText)view.findViewById(R.id.textview_item_name)).getText() .toString().trim();
	            itemcode = ((EditText)view. findViewById(R.id.textview_item_code)).getText().toString().trim();
	            itemspec = ((EditText) view.findViewById(R.id.textview_item_spec)).getText().toString().trim();
	            ArrayAdapter wmyAdap = (ArrayAdapter) wspinner.getAdapter();
	            String whname = wspinner.getSelectedItem().toString();
	            int pos = 0;
	            if(whname.equals("")) pos=0;
	            else
		            pos=wmyAdap.getPosition(whname);
	            whid =  widValues[pos].toString();
	            Bundle args = new Bundle();
	            Bundle data = new Bundle();
	            data.putString("itemCode", itemcode);
	            data.putString("itemName", itemname);
	            data.putString("itemSpec", itemspec);
	            data.putString("whID", whid);
	            args.putBundle("data", data);
	            warehouseInventoryFragment.setArguments(args);
	            fragmentTransaction.replace(R.id.frame_content,warehouseInventoryFragment);
	            fragmentTransaction.addToBackStack(null);
	            fragmentTransaction.commit();
            }
        });

	    return view;
    }

	@Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listView.setItemChecked(i, true);
    }

}
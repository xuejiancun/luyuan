package com.luyuan.mobile.production;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.luyuan.mobile.R;

public class WarehouseGetProductFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
	private ProgressDialog dialog;

	private String itemname;
	private String itemcode;
	private String itemspec;
	private String api;
	private String wbName;
	private String ProductCode;
	private String PrefixName;
	private String SpecType;
	private String wbID;
	private String wbIDDetail;
	private String Qty;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.warehouse_getproduct_fragment, null);
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
        ((Button) view.findViewById(R.id.button_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewContent) {
	            WarehouseGetProductDetailFragment warehouseGetProductDetailFragment = new
			            WarehouseGetProductDetailFragment();
	            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
	            itemname = ((EditText)view.findViewById(R.id.textview_product_name)).getText()
			            .toString().trim();
	            itemcode = ((EditText)view.findViewById(R.id.textview_product_code)).getText().toString().trim();
	            itemspec = ((EditText)view.findViewById(R.id.textview_product_spec)).getText().toString().trim();

	            Bundle args = new Bundle();
	            Bundle data = new Bundle();
	            data.putString("itemCode", itemcode);
	            data.putString("itemName", itemname);
	            data.putString("itemSpec", itemspec);
	            data.putString("wbID",  wbID);
	            data.putString("wbIDDetail", wbIDDetail);
	            data.putString("Qty", Qty);
	            data.putString("ProductCode", ProductCode);
	            data.putString("wbName", wbName);
	            data.putString("SpecType", SpecType);
	            data.putString("PrefixName", PrefixName);
	            args.putBundle("data", data);
	            warehouseGetProductDetailFragment.setArguments(args);
	            fragmentTransaction.replace(R.id.frame_content,warehouseGetProductDetailFragment);
	            fragmentTransaction.addToBackStack(null);
	            fragmentTransaction.commit();
            }
        });
	    ((Button) view.findViewById(R.id.button_back)).setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View viewContent) {
			    WarehouseLocationInventoryModifyFragment warehouseLocationInventoryModifyFragment = new
					    WarehouseLocationInventoryModifyFragment();
			    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

			    Bundle args = new Bundle();
			    Bundle data = new Bundle();
			    data.putString("wbID",  wbID);
			    data.putString("wbIDDetail", wbIDDetail);
			    data.putString("Qty", Qty);
			    data.putString("ProductCode", ProductCode);
			    data.putString("wbName", wbName);
			    data.putString("SpecType", SpecType);
			    data.putString("PrefixName", PrefixName);
			    args.putBundle("data", data);
			    warehouseLocationInventoryModifyFragment.setArguments(args);
			    fragmentTransaction.replace(R.id.frame_content,warehouseLocationInventoryModifyFragment);
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
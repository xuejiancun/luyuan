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
import com.luyuan.mobile.model.WarehouseInventoryBook;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class WarehouseInventoryDetailFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private String whID;
    private String itemID;
    private ProgressDialog dialog;
    private LayoutInflater layoutInflater;
    private WarehouseInventoryBook warehouseInventoryBook=new WarehouseInventoryBook();
  //  StringBuffer url = new StringBuffer(MyGlobal.API_WAREHOUSE_PURCHASEORDER_QUERY);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.warehouse_inventorydetail_fragment, null);
        listView = (ListView) view.findViewById(R.id.listView_information);

        Bundle args = getArguments();
        if (args != null && args.getBundle("data") != null) {
            Bundle data = args.getBundle("data");
            whID=data.getString("whID");
	        itemID=data.getString("itemID");
        }
	  //  TextView textView = (TextView)view.findViewById(R.id.textView_information);
	    //textView.setText("小童SB");
	   // textView.setSingleLine(true);
	  //  textView.setMarqueeRepeatLimit(-1);
//	    textView.setTextSize(30);
//	    textView.setHorizontallyScrolling(true);
//	    textView.setFocusable(true);
        StringBuffer url = new StringBuffer(MyGlobal.API_WHINVENTORYDETAIL);
        url.append("&itemID=" + itemID +"&whID="+whID);

        if (MyGlobal.checkNetworkConnection(getActivity())) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.search_loading));
            dialog.setCancelable(true);
            dialog.show();
             GsonRequest gsonObjRequest = new GsonRequest<WarehouseInventoryBook>(Request.Method
		             .GET, url.toString(), WarehouseInventoryBook.class,
                    new Response.Listener<WarehouseInventoryBook>() {

                        @Override
                        public void onResponse( WarehouseInventoryBook response) {
                            dialog.dismiss();

                            if (response.getSuccess().equals("true")) {
	                            warehouseInventoryBook = response;
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
            super(c, R.layout.item_warehouse_voucher_search);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_warehouse_inventorybook_detail, null);
            ((TextView) view.findViewById(R.id.textview_item_date)).setText
		            (warehouseInventoryBook.getWarehouseInventoryBookDetail().get(position)
				            .getAccountingDate());
	        ((TextView) view.findViewById(R.id.textview_item_voucher)).setText
			        (warehouseInventoryBook.getWarehouseInventoryBookDetail().get(position)
					        .getVoucherCode());
            ((TextView) view.findViewById(R.id.textview_wh_inqty)).setText
		            (warehouseInventoryBook.getWarehouseInventoryBookDetail().get(position)
				            .getInQty());
	        ((TextView) view.findViewById(R.id.textview_wh_outqty)).setText
			        (warehouseInventoryBook.getWarehouseInventoryBookDetail().get(position)
					        .getOutQty());
            ((TextView) view.findViewById(R.id.textview_wh_bqty)).setText
		            (warehouseInventoryBook.getWarehouseInventoryBookDetail().get(position).getBalanceQty());

            return view;
        }

        @Override
        public int getCount() {
            return warehouseInventoryBook.getWarehouseInventoryBookDetail().size();
        }

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listView.setItemChecked(i, true);
    }


}
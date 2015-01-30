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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.PurwhunitData;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class WarehouseVoucherMakeFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private Spinner uspinner;
    private Spinner wspinner;
    private ProgressDialog dialog;
    private PurwhunitData whUnit;
    private String unitName;
    private String whName;
    private String code;
    private int uPosition;
    private int wPosition;
    private String whID;
    private String unitID;
    private String[] widValues;
    private String[] uidValues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.warehouse_voucher_make_fragment, null);

        //listView = (ListView) view.findViewById(R.id.listview_warehouse_choose_list);
	     // Spinner wp  =(Spinner) view.findViewById(R.id.spinner_whname);
//          wp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//              @Override
//              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//              }
//          });

        ((Button) view.findViewById(R.id.button_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WarehouseVoucherConfirmFragment warehouseVoucherConfirmFragment = new WarehouseVoucherConfirmFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
               int wpositon = wspinner.getSelectedItemPosition();
               int uposition = uspinner.getSelectedItemPosition();


                whID = widValues[wpositon];
                unitID = uidValues[uposition];
                Bundle args = new Bundle();
                Bundle data = new Bundle();
                data.putString("code", code);
                data.putString("unitName", unitName);
                data.putString("whName", whName);
                data.putString("unitID", unitID);
                data.putString("whID", whID);
                args.putBundle("data", data);

                warehouseVoucherConfirmFragment.setArguments(args);
                fragmentTransaction.replace(R.id.frame_content, warehouseVoucherConfirmFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Bundle args = getArguments();
        if (args != null && args.getBundle("data") != null) {
            Bundle data = args.getBundle("data");
            code = data.getString("code");
            ((TextView) view.findViewById(R.id.textview_order_no)).setText(code);
            ((TextView) view.findViewById(R.id.textview_order_by)).setText(data.getString("preparedBy"));
            ((TextView) view.findViewById(R.id.textview_order_status)).setText(data.getString("status"));
            unitName = data.getString("unitName");
            whName = data.getString("whName");
            unitID = data.getString("unitID");
            whID = data.getString("whID");
        }
        uspinner = (Spinner) view.findViewById(R.id.spinner_unit);
        wspinner = (Spinner) view.findViewById(R.id.spinner_whname);
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

                                int size = response.getunitInfo().size();
                                String[] values = new String[size];
                                uidValues = new String[size];
                                for (int i = 0; i < size; i++) {
                                    values[i] = response.getunitInfo().get(i).unitName;
                                    uidValues[i] = response.getunitInfo().get(i).unitID;
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, values);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                uspinner.setAdapter(adapter);
                                uPosition = 0;
                                ArrayAdapter myAdap = (ArrayAdapter) uspinner.getAdapter();
                                uPosition = myAdap.getPosition(unitName);
                                uspinner.setSelection(uPosition);

                                int sizew = response.getwhInfo().size();
                                String[] valuesw = new String[sizew];
                                widValues = new String[sizew];
                                for (int i = 0; i < sizew; i++) {
                                    valuesw[i] = response.getwhInfo().get(i).whName;
                                    widValues[i] = response.getwhInfo().get(i).whID;
                                }
                                ArrayAdapter<String> adapterw = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, valuesw);
                                adapterw.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                wspinner.setAdapter(adapterw);
                                wPosition = 0;
                                ArrayAdapter wmyAdap = (ArrayAdapter) wspinner.getAdapter();
                                if (whName.equals("")) wPosition = 0;
                                else
                                    wPosition = wmyAdap.getPosition(whName);
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


        // ArrayAdapter<String> files = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, values);

        //listView.setAdapter(files);
        // listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listView.setItemChecked(i, true);
    }

}
package com.luyuan.mobile.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.function.SalesTacticalReport;
import com.luyuan.mobile.function.UploadMaterialActivity;
import com.luyuan.mobile.function.WarehouseVoucherManagerActivity;
import com.luyuan.mobile.model.FunctionData;
import com.luyuan.mobile.util.MyGlobal;

public class FunctionFragment extends Fragment implements AdapterView.OnItemClickListener {

    private LayoutInflater layoutInflater;
    private ListView listView;
    private FunctionData functionData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_function, null);
        listView = (ListView) view.findViewById(R.id.listview_function_list);

        LinearLayout layout_function_4 = (LinearLayout) view.findViewById(R.id.layout_function_4);
        layout_function_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SalesTacticalReport.class);
                startActivity(intent);
            }
        });

        LinearLayout layout_function_5 = (LinearLayout) view.findViewById(R.id.layout_function_5);
        layout_function_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WarehouseVoucherManagerActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout layout_function_6 = (LinearLayout) view.findViewById(R.id.layout_function_6);
        layout_function_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), UploadMaterialActivity.class);
                startActivity(intent);
            }
        });

        StringBuffer url = new StringBuffer(MyGlobal.API_FETCH_LOGIN);
        url.append("&stId=" + MyGlobal.getJobInfo().getStId());

//        if (MyGlobal.checkNetworkConnection(getActivity())) {
//            GsonRequest gsonObjRequest = new GsonRequest<FunctionData>(Request.Method.GET, url.toString(),
//                    FunctionData.class, new Response.Listener<FunctionData>() {
//                @Override
//                public void onResponse(FunctionData response) {
//                    if (response != null && response.getSuccess().equals("true")) {
//                        listView.setAdapter(new FunctionAdapter(getActivity()));
//                        // TODO click item
//                    } else {
//                        new AlertDialog.Builder(getActivity())
//                                .setMessage(R.string.fetch_data_error)
//                                .setTitle(R.string.dialog_hint)
//                                .setPositiveButton(R.string.dialog_confirm, null)
//                                .create()
//                                .show();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    new AlertDialog.Builder(getActivity())
//                            .setMessage(R.string.fetch_data_error)
//                            .setTitle(R.string.dialog_hint)
//                            .setPositiveButton(R.string.dialog_confirm, null)
//                            .create()
//                            .show();
//                }
//            }
//            );
//
//            RequestManager.getRequestQueue().add(gsonObjRequest);
//            gsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
//                    MyGlobal.CONNECTION_TIMEOUT_MS,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MyGlobal.getFunctionActivity(functionData.getFunctionInfos().get(i).getCode()));
        startActivity(intent);
    }

    public class FunctionAdapter extends ArrayAdapter<String> {

        public FunctionAdapter(Context c) {
            super(c, R.layout.item_function);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_function, null);

            ImageView imageview_function_icon = (ImageView) view.findViewById(R.id.imageview_function_icon);
            TextView textview_funciton_name = (TextView) view.findViewById(R.id.textview_funciton_name);
            TextView textview_funciton_desc = (TextView) view.findViewById(R.id.textview_funciton_desc);

            imageview_function_icon.setImageResource(MyGlobal.getFunctionIcon(functionData.getFunctionInfos().get(position).getCode()));
            textview_funciton_name.setText(functionData.getFunctionInfos().get(position).getName());
            textview_funciton_desc.setText(functionData.getFunctionInfos().get(position).getDesc());

            return view;
        }

        @Override
        public int getCount() {
            return functionData.getFunctionInfos().size();
        }

    }

}

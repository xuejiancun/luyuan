package com.luyuan.mobile.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.FunctionData;
import com.luyuan.mobile.util.DatabaseHelper;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

public class FunctionFragment extends Fragment implements AdapterView.OnItemClickListener {

    private LayoutInflater layoutInflater;
    private ListView listView;
    private FunctionData functionData = new FunctionData();
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_function, null);

//        List<FunctionInfo> functionInfos = new ArrayList<FunctionInfo>();
//
//        FunctionInfo functionInfo1 = new FunctionInfo();
//        functionInfo1.setCode("report_billboard");
//        functionInfo1.setName(getText(R.string.function_billboard_report).toString());
//        functionInfos.add(functionInfo1);
//
//        FunctionInfo functionInfo2 = new FunctionInfo();
//        functionInfo2.setCode("report_personal");
//        functionInfo2.setName(getText(R.string.function_personal_report).toString());
//        functionInfos.add(functionInfo2);
//
//        FunctionInfo functionInfo3 = new FunctionInfo();
//        functionInfo3.setCode("report_strategy");
//        functionInfo3.setName(getText(R.string.function_strategy_report).toString());
//        functionInfos.add(functionInfo3);
//
//        FunctionInfo functionInfo4 = new FunctionInfo();
//        functionInfo4.setCode("report_tactical");
//        functionInfo4.setName(getText(R.string.function_tactical_report).toString());
//        functionInfos.add(functionInfo4);
//
//        FunctionInfo functionInfo5 = new FunctionInfo();
//        functionInfo5.setCode("query_payroll");
//        functionInfo5.setName(getText(R.string.function_payroll_query).toString());
//        functionInfos.add(functionInfo5);
//
//        FunctionInfo functionInfo6 = new FunctionInfo();
//        functionInfo6.setCode("query_training");
//        functionInfo6.setName(getText(R.string.function_train_manage).toString());
//        functionInfos.add(functionInfo6);
//
//        FunctionInfo functionInfo7 = new FunctionInfo();
//        functionInfo7.setCode("query_manual");
//        functionInfo7.setName(getText(R.string.function_manual_manage).toString());
//        functionInfos.add(functionInfo7);
//
//        FunctionInfo functionInfo8 = new FunctionInfo();
//        functionInfo8.setCode("material_upload");
//        functionInfo8.setName(getText(R.string.function_upload_material).toString());
//        functionInfos.add(functionInfo8);
//
//        FunctionInfo functionInfo9 = new FunctionInfo();
//        functionInfo9.setCode("schedule_manage");
//        functionInfo9.setName(getText(R.string.function_schedule_manage).toString());
//        functionInfos.add(functionInfo9);
//
//        FunctionInfo functionInfo10 = new FunctionInfo();
//        functionInfo10.setCode("research_native");
//        functionInfo10.setName(getText(R.string.function_market_research_native).toString());
//        functionInfos.add(functionInfo10);
//
//        FunctionInfo functionInfo11 = new FunctionInfo();
//        functionInfo11.setCode("research");
//        functionInfo11.setName(getText(R.string.function_market_research_web).toString());
//        functionInfos.add(functionInfo11);
//
//        FunctionInfo functionInfo12 = new FunctionInfo();
//        functionInfo12.setCode("market_research");
//        functionInfo12.setName(getText(R.string.function_market_research).toString());
//        functionInfos.add(functionInfo12);
//
//        functionData.setFunctionInfos(functionInfos);

        listView = (ListView) view.findViewById(R.id.listview_function_list);

        StringBuffer url = new StringBuffer(MyGlobal.API_FETCH_FUNCTION);
        url.append("&stId=" + MyGlobal.getUser().getStId());

        functionData = MyGlobal.getFunctionData();

        if (functionData.getFunctionInfos().size() > 0) {
            listView.setAdapter(new FunctionAdapter());
            listView.setOnItemClickListener(FunctionFragment.this);
            listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                    contextMenu.add(0, 1, 1, R.string.shortcut);
                }
            });
        } else if (MyGlobal.checkNetworkConnection(getActivity())) {

            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.loading));
            dialog.setCancelable(true);
            dialog.show();

            GsonRequest gsonObjRequest = new GsonRequest<FunctionData>(Request.Method.GET, url.toString(),
                    FunctionData.class, new Response.Listener<FunctionData>() {
                @Override
                public void onResponse(FunctionData response) {
                    dialog.dismiss();
                    if (response != null && response.getSuccess().equals("true")) {
                        functionData = response;
                        MyGlobal.setFunctionData(functionData);
                        listView.setAdapter(new FunctionAdapter());
                        listView.setOnItemClickListener(FunctionFragment.this);
                        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                            @Override
                            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                                contextMenu.add(0, 1, 1, R.string.shortcut);
                            }
                        });
                    } else {
                        new AlertDialog.Builder(getActivity())
                                .setMessage(R.string.interact_data_error)
                                .setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null)
                                .create()
                                .show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.interact_data_error)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                }
            }
            );

            RequestManager.getRequestQueue().add(gsonObjRequest);
        }

        return view;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        createShortCuts(functionData.getFunctionInfos().get(info.position).getCode(), functionData.getFunctionInfos().get(info.position).getName());

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MyGlobal.getFunctionActivity(functionData.getFunctionInfos().get(i).getCode()));
        intent.putExtra("function", functionData.getFunctionInfos().get(i).getCode());
        intent.putExtra("tab", "function");

        startActivity(intent);
    }

    public class FunctionAdapter extends BaseAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_function, null);

            ImageView imageview_function_icon = (ImageView) view.findViewById(R.id.imageview_function_icon);
            TextView textview_funciton_name = (TextView) view.findViewById(R.id.textview_funciton_name);

            imageview_function_icon.setImageResource(MyGlobal.getFunctionIcon(functionData.getFunctionInfos().get(position).getCode()));
            textview_funciton_name.setText(functionData.getFunctionInfos().get(position).getName());

            return view;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return functionData.getFunctionInfos().size();
        }

    }

    private void createShortCuts(String code, String name) {
        DatabaseHelper instance = DatabaseHelper.getInstance(getActivity());
        instance.removeShortcut(code);
        instance.createShortcut(code, name);
    }

}

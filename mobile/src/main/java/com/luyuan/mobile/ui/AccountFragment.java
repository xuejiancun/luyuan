package com.luyuan.mobile.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.model.FunctionData;
import com.luyuan.mobile.model.FunctionInfo;
import com.luyuan.mobile.util.MyGlobal;

import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment implements AdapterView.OnItemClickListener {

    private LayoutInflater layoutInflater;
    private ListView listView;
    private FunctionData functionData = new FunctionData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_account, null);

        List<FunctionInfo> functionInfos = new ArrayList<FunctionInfo>();

        FunctionInfo functionInfo1 = new FunctionInfo();
        functionInfo1.setCode("about_me");
        functionInfo1.setName("关于我");
        functionInfos.add(functionInfo1);

        FunctionInfo functionInfo2 = new FunctionInfo();
        functionInfo2.setCode("login_histroy");
        functionInfo2.setName("登录历史");
        functionInfos.add(functionInfo2);

        FunctionInfo functionInfo3 = new FunctionInfo();
        functionInfo3.setCode("change_password");
        functionInfo3.setName("修改密码");
        functionInfos.add(functionInfo3);

        FunctionInfo functionInfo4 = new FunctionInfo();
        functionInfo4.setCode("notification_history");
        functionInfo4.setName("提醒历史");
        functionInfos.add(functionInfo4);

        functionData.setFunctionInfos(functionInfos);

        listView = (ListView) view.findViewById(R.id.listview_function_list);
        listView.setAdapter(new FunctionAdapter());
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), MyGlobal.getFunctionActivity(functionData.getFunctionInfos().get(i).getCode()));
//        intent.putExtra("function", functionData.getFunctionInfos().get(i).getCode());
//
//        startActivity(intent);
    }

    public class FunctionAdapter extends BaseAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_function, null);

            ImageView imageview_function_icon = (ImageView) view.findViewById(R.id.imageview_function_icon);
            TextView textview_funciton_name = (TextView) view.findViewById(R.id.textview_funciton_name);
            // TextView textview_funciton_desc = (TextView) view.findViewById(R.id.textview_funciton_desc);

            imageview_function_icon.setImageResource(MyGlobal.getFunctionIcon(functionData.getFunctionInfos().get(position).getCode()));
            textview_funciton_name.setText(functionData.getFunctionInfos().get(position).getName());
            // textview_funciton_desc.setText(functionData.getFunctionInfos().get(position).getDesc());

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
}

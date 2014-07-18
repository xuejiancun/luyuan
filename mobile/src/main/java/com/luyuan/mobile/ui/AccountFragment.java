package com.luyuan.mobile.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.function.AccountAboutMeActivity;
import com.luyuan.mobile.function.NotificationActivity;
import com.luyuan.mobile.model.FunctionData;
import com.luyuan.mobile.model.FunctionInfo;
import com.luyuan.mobile.model.User;
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
        functionInfo1.setName(getString(R.string.function_about_me));
        functionInfos.add(functionInfo1);

        FunctionInfo functionInfo2 = new FunctionInfo();
        functionInfo2.setCode("login_history");
        functionInfo2.setName(getString(R.string.function_login_histroy));
        functionInfos.add(functionInfo2);

        FunctionInfo functionInfo3 = new FunctionInfo();
        functionInfo3.setCode("change_password");
        functionInfo3.setName(getString(R.string.function_change_password));
        functionInfos.add(functionInfo3);

        FunctionInfo functionInfo4 = new FunctionInfo();
        functionInfo4.setCode("notification_history");
        functionInfo4.setName(getString(R.string.function_notification_history));
        functionInfos.add(functionInfo4);

        FunctionInfo functionInfo5 = new FunctionInfo();
        functionInfo5.setCode("check_version");
        functionInfo5.setName(getString(R.string.function_check_version));
        functionInfos.add(functionInfo5);

        functionData.setFunctionInfos(functionInfos);

        listView = (ListView) view.findViewById(R.id.listview_function_list);
        listView.setAdapter(new FunctionAdapter());
        listView.setOnItemClickListener(this);

        ((TextView) view.findViewById(R.id.textview_username)).setText(MyGlobal.getUser().getUsername());
        ((TextView) view.findViewById(R.id.textview_contact)).setText(MyGlobal.getUser().getContact());
        ((TextView) view.findViewById(R.id.textview_email)).setText(MyGlobal.getUser().getEmail());

        ((Button) view.findViewById(R.id.button_logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);

                MyGlobal.setUser(new User());

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        switch (i) {
            case 0:
                intent = new Intent();
                intent.setClass(getActivity(), AccountAboutMeActivity.class);
                intent.putExtra("tab", "account");

                startActivity(intent);
                break;
            case 1:
                intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("function", functionData.getFunctionInfos().get(i).getCode());
                intent.putExtra("tab", "account");

                startActivity(intent);
                break;
            case 2:
                intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("function", functionData.getFunctionInfos().get(i).getCode());
                intent.putExtra("tab", "account");

                startActivity(intent);
                break;
            case 3:
                intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("function", functionData.getFunctionInfos().get(i).getCode());
                intent.putExtra("tab", "account");

                startActivity(intent);
                break;
            case 4:
                intent = new Intent();
                intent.setClass(getActivity(), NotificationActivity.class);
                intent.putExtra("function", functionData.getFunctionInfos().get(i).getCode());

                startActivity(intent);
                break;
        }


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
}

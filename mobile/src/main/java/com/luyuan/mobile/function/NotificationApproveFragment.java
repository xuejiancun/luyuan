package com.luyuan.mobile.function;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.ui.LoginActivity;
import com.luyuan.mobile.ui.WebViewActivity;
import com.luyuan.mobile.util.MyGlobal;

// 审批提醒
public class NotificationApproveFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_approve_fragment, null);

        Bundle args = getArguments();
        if (args != null && args.getString("content") != null) {
            String content = args.getString("content");
            ((TextView) view.findViewById(R.id.textview_content)).setText(content);
        }

        ((Button) view.findViewById(R.id.button_enter)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MyGlobal.getUser().getSessionId().isEmpty()) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("function", "research_verify");
                    intent.putExtra("tab", "function");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        return view;
    }

}
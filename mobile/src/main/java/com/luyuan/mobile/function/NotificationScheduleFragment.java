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
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.MyGlobal;

public class NotificationScheduleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_schedule_fragment, null);

        Bundle args = getArguments();
        if (args != null && args.getString("content") != null) {
            String content = args.getString("content");
            String[] list = content.split("#");
            ((TextView) view.findViewById(R.id.textview_content)).setText(list[0]);
            ((TextView) view.findViewById(R.id.textview_starttime)).setText(list[1]);
            ((TextView) view.findViewById(R.id.textview_endtime)).setText(list[2]);
        }


        ((Button) view.findViewById(R.id.button_enter)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MyGlobal.getUser().getSessionId().isEmpty()) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("stId", MyGlobal.getUser().getStId());
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
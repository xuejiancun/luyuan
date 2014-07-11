package com.luyuan.mobile.function;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luyuan.mobile.R;
import com.luyuan.mobile.ui.LoginActivity;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.MyGlobal;

public class NotificationNewsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_news_fragment, null);

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
package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.luyuan.pad.mberp.R;

public class PostFragment extends Fragment {

    private FragmentTabHost tabHost;

    private Class fragmentArray[] = {SettingFragment.class, FunctionFragment.class, SettingFragment.class,
            SettingFragment.class, SettingFragment.class, SettingFragment.class};

    private String textViewArray[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        tabHost = (FragmentTabHost) view.findViewById(R.id.tabhost_post);
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent_post);

        textViewArray = new String[]{getString(R.string.type_luxury), getString(R.string.type_simple),
                getString(R.string.type_standard), getString(R.string.type_battery),
                getString(R.string.type_replacewalk), getString(R.string.type_special)};

        int count = fragmentArray.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(textViewArray[i]).setIndicator(getTabItemView(i, inflater));
            tabHost.addTab(tabSpec, fragmentArray[i], null);
        }

        return view;
    }

    private View getTabItemView(int index, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.tab_item_top, null);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(textViewArray[index]);

        return view;
    }

}
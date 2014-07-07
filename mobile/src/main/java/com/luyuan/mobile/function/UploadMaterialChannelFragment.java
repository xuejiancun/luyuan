package com.luyuan.mobile.function;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.luyuan.mobile.R;

public class UploadMaterialChannelFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_material_channel, null);
        listView = (ListView) view.findViewById(R.id.listview_channel_list);

        ((Button) view.findViewById(R.id.button_enter)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceTabContent(new UploadMaterialNewFragment());
            }
        });

        String[] values = new String[]{"财务上报", "质量反馈", "网店报备",
                "市场信息", "其他信息"};
        ArrayAdapter<String> files = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, values);

        listView.setAdapter(files);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this);

        return view;
    }

    private void replaceTabContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        // params channel id

        fragmentTransaction.replace(R.id.frame_content_upload_material, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listView.setItemChecked(i, true);
    }

}
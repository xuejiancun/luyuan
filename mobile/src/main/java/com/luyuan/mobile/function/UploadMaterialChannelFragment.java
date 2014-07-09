package com.luyuan.mobile.function;

import android.app.AlertDialog;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.ChannelData;
import com.luyuan.mobile.model.ChannelInfo;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class UploadMaterialChannelFragment extends Fragment {

    private ListView listView;
    private ChannelData channelData;
    private ProgressDialog dialog;
    private int channelIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_material_channel, null);
        listView = (ListView) view.findViewById(R.id.listview_channel_list);

        ((Button) view.findViewById(R.id.button_enter)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new UploadMaterialNewFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                Bundle args = new Bundle();
                args.putString("channel", channelData.getChannelInfos().get(channelIndex).getId());
                fragment.setArguments(args);

                fragmentTransaction.replace(R.id.frame_content_upload_material, fragment);
                fragmentTransaction.commit();
            }
        });

        if (MyGlobal.checkNetworkConnection(getActivity())) {

            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.loading));
            dialog.setCancelable(true);
            dialog.show();

            GsonRequest gsonObjRequest = new GsonRequest<ChannelData>(Request.Method.GET, MyGlobal.API_FETCH_CHANNEL,
                    ChannelData.class, new Response.Listener<ChannelData>() {
                @Override
                public void onResponse(ChannelData response) {
                    dialog.dismiss();

                    if (response != null && response.getSuccess().equals("true")) {
                        channelData = response;

                        ArrayList<String> channels = new ArrayList<String>();
                        List<ChannelInfo> channelInfos = channelData.getChannelInfos();
                        for (ChannelInfo channelInfo : channelInfos) {
                            channels.add(channelInfo.getName());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, channels);

                        listView.setAdapter(adapter);
                        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                        listView.setItemChecked(0, true);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                channelIndex = i;
                                listView.setItemChecked(i, true);
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

}
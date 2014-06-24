package com.luyuan.pad.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.luyuan.pad.R;
import com.luyuan.pad.model.TechData;
import com.luyuan.pad.model.TechInfo;
import com.luyuan.pad.util.GlobalConstantValues;
import com.luyuan.pad.util.GsonRequest;
import com.luyuan.pad.util.ImageCacheManager;
import com.luyuan.pad.util.RequestManager;

import java.util.ArrayList;

public class TechMainFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private LayoutInflater layoutInflater;

    private TechData techData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_tech_main, null);
        gridView = (GridView) view.findViewById(R.id.gridview_tech_list);

        if (GlobalConstantValues.checkNetworkConnection(getActivity())) {
            fetchTechIconData(GlobalConstantValues.API_TECH_DATA);
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        ImagePagerFragment imagePagerFragment = new ImagePagerFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putStringArrayList(GlobalConstantValues.PARAM_IMAGE_URL_LIST, getImageUrls());
        args.putInt(GlobalConstantValues.PARAM_IMAGE_INDEX, position);
        imagePagerFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, imagePagerFragment);
        fragmentTransaction.commit();
    }

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return techData.getTechInfos().size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.techicon_item, null);

            NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.imageview_tech_icon);
            imageView.setDefaultImageResId(R.drawable.loading_small);
            imageView.setErrorImageResId(R.drawable.error_small);
            imageView.setImageUrl(techData.getTechInfos().get(position).getIconUrl(), ImageCacheManager.getInstance().getSmallImageLoader());

            return view;
        }
    }

    public void fetchTechIconData(String url) {
        GsonRequest gsonObjRequest = new GsonRequest<TechData>(Request.Method.GET, url,
                TechData.class, new Response.Listener<TechData>() {
            @Override
            public void onResponse(TechData response) {
                if (response != null && response.getSuccess().equals("true")) {
                    techData = response;
                    gridView.setAdapter(new ImageAdapter(getActivity()));
                    gridView.setOnItemClickListener(TechMainFragment.this);
                } else {
                    Dialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.fetch_data_error)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create();
                    alertDialog.show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Dialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.fetch_data_error)
                        .setTitle(R.string.dialog_hint)
                        .setPositiveButton(R.string.dialog_confirm, null)
                        .create();
                alertDialog.show();
            }
        }
        );

        RequestManager.getRequestQueue().add(gsonObjRequest);
        gsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                GlobalConstantValues.CONNECTION_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public ArrayList<String> getImageUrls() {
        ArrayList<String> result = new ArrayList<String>();
        for (TechInfo techinfo : techData.getTechInfos()) {
            result.add(techinfo.getImageUrl().trim());
        }
        return result;
    }

}
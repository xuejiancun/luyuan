package com.luyuan.pad.mberp.ui;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.model.TechIconData;
import com.luyuan.pad.mberp.util.GlobalConstantValues;
import com.luyuan.pad.mberp.util.GsonRequest;
import com.luyuan.pad.mberp.util.ImageCacheManager;
import com.luyuan.pad.mberp.util.RequestManager;

public class TechMainFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private LayoutInflater layoutInflater;

    private TechIconData techIconData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_tech_main, null);
        gridView = (GridView) view.findViewById(R.id.gridview_tech_list);

        fetchTechIconData(GlobalConstantValues.API_TECH_ICON);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_API_URL, GlobalConstantValues.API_TECH_IMAGE);
        productDetailFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, productDetailFragment);
        fragmentTransaction.commit();
    }

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return techIconData.getImageSlides().size();
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
            imageView.setImageUrl(techIconData.getImageSlides().get(position).getUrl(), ImageCacheManager.getInstance().getSmallImageLoader());

            return view;
        }
    }

    public void fetchTechIconData(String url) {
        GsonRequest gsonObjRequest = new GsonRequest<TechIconData>(Request.Method.GET, url,
                TechIconData.class, new Response.Listener<TechIconData>() {
            @Override
            public void onResponse(TechIconData response) {
                if (response != null && response.getSuccess().equals("true")) {
                    techIconData = response;
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
    }

}
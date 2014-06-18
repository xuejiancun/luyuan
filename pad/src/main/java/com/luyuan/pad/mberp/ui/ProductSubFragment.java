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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.model.ProductThumbData;
import com.luyuan.pad.mberp.util.GlobalConstantValues;
import com.luyuan.pad.mberp.util.GsonRequest;
import com.luyuan.pad.mberp.util.ImageCacheManager;
import com.luyuan.pad.mberp.util.RequestManager;

public class ProductSubFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String type = "";

    private GridView gridView;
    private LayoutInflater layoutInflater;

    private ProductThumbData productThumbData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_product_sub, null);
        gridView = (GridView) view.findViewById(R.id.gridview_product_list);

        Bundle args = getArguments();
        if (args != null) {
            String api = "";

            if (args.getString(GlobalConstantValues.PARAM_API_URL) != null) {
                api = args.getString(GlobalConstantValues.PARAM_API_URL);
            }
            if (args.getString(GlobalConstantValues.PARAM_CAR_TYPE) != null) {
                type = args.getString(GlobalConstantValues.PARAM_CAR_TYPE);
            }

            if (!api.isEmpty() && GlobalConstantValues.checkNetworkConnection(getActivity())) {
                fetchProductThumbData(api);
            }
        } else {
            Dialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.app_param_error)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create();
            alertDialog.show();
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_CAR_MODEL, ((TextView) v.findViewById(R.id.textview_product_list_info_model)).getText().toString());
        args.putString(GlobalConstantValues.PARAM_CAR_TYPE, type);
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
            return productThumbData.getProductThumbInfos().size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View view = layoutInflater.inflate(R.layout.product_item, null);

            NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.imageview_product_list);
            imageView.setDefaultImageResId(R.drawable.loading_small);
            imageView.setErrorImageResId(R.drawable.error_small);
            imageView.setImageUrl(productThumbData.getProductThumbInfos().get(position).getUrl(), ImageCacheManager.getInstance().getSmallImageLoader());

            TextView textViewName = (TextView) view.findViewById(R.id.textview_product_list_info_name);
            textViewName.setText(productThumbData.getProductThumbInfos().get(position).getName());

            TextView textViewDesc1 = (TextView) view.findViewById(R.id.textview_product_list_info_model);
            textViewDesc1.setText(productThumbData.getProductThumbInfos().get(position).getModel());

            TextView textViewDesc2 = (TextView) view.findViewById(R.id.textview_product_list_info_battery);
            textViewDesc2.setText(productThumbData.getProductThumbInfos().get(position).getBattery());

            TextView textViewDesc3 = (TextView) view.findViewById(R.id.textview_product_list_info_endurance);
            textViewDesc3.setText(productThumbData.getProductThumbInfos().get(position).getEndurance());

            TextView textViewDesc4 = (TextView) view.findViewById(R.id.textview_product_list_info_series);
            textViewDesc4.setText(productThumbData.getProductThumbInfos().get(position).getSeries());

            return view;
        }
    }

    public void fetchProductThumbData(String url) {
        GsonRequest gsonObjRequest = new GsonRequest<ProductThumbData>(Request.Method.GET, url,
                ProductThumbData.class, new Response.Listener<ProductThumbData>() {
            @Override
            public void onResponse(ProductThumbData response) {
                if (response != null && response.getSuccess().equals("true")) {
                    productThumbData = response;
                    gridView.setAdapter(new ImageAdapter(getActivity()));
                    gridView.setOnItemClickListener(ProductSubFragment.this);
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


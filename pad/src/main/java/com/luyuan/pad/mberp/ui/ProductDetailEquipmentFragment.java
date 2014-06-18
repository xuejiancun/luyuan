package com.luyuan.pad.mberp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.model.CarEquipmentData;
import com.luyuan.pad.mberp.util.GlobalConstantValues;
import com.luyuan.pad.mberp.util.GsonRequest;
import com.luyuan.pad.mberp.util.ImageCacheManager;
import com.luyuan.pad.mberp.util.RequestManager;

public class ProductDetailEquipmentFragment extends Fragment {

    private GridView gridView;
    private LayoutInflater layoutInflater;
    private CarEquipmentData carEquipmentData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_product_detail_equipment, null);
        gridView = (GridView) view.findViewById(R.id.gridview_product_detail_equipment);

        String model = "";
        Bundle args = getArguments();
        if (args != null && args.getString(GlobalConstantValues.PARAM_CAR_MODEL) != null) {
            model = args.getString(GlobalConstantValues.PARAM_CAR_MODEL);

            if (GlobalConstantValues.checkNetworkConnection(getActivity())) {
                fetchCarEquipmentData(GlobalConstantValues.API_CAR_EQUIPMENT + "&model=" + model.trim());
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

    public class ImageAdapter extends BaseAdapter {

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return carEquipmentData.getCarEquipmentInfos().size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.equipment_item, null);

            NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.imageview_product_detail_equipment);
            imageView.setDefaultImageResId(R.drawable.loading);
            imageView.setErrorImageResId(R.drawable.error);
            imageView.setImageUrl(carEquipmentData.getCarEquipmentInfos().get(position).getUrl(), ImageCacheManager.getInstance().getSmallImageLoader());

            TextView textViewName = (TextView) view.findViewById(R.id.textview_product_detail_equipment);
            textViewName.setText(carEquipmentData.getCarEquipmentInfos().get(position).getDesc());

            return view;
        }

        private Context mContext;

    }

    public void fetchCarEquipmentData(String url) {
        GsonRequest gsonObjRequest = new GsonRequest<CarEquipmentData>(Request.Method.GET, url,
                CarEquipmentData.class, new Response.Listener<CarEquipmentData>() {
            @Override
            public void onResponse(CarEquipmentData response) {
                if (response != null && response.getSuccess().equals("true")) {
                    carEquipmentData = response;
                    gridView.setAdapter(new ImageAdapter(getActivity()));
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
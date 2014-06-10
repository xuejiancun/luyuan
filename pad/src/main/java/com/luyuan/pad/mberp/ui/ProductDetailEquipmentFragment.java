package com.luyuan.pad.mberp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.ImageCacheManager;
import com.luyuan.pad.mberp.util.ImageDownloadManager;

public class ProductDetailEquipmentFragment extends Fragment {

    private LayoutInflater layoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_product_detail_equipment, null);

        GridView g = (GridView) view.findViewById(R.id.gridview_product_detail_equipment);
        g.setAdapter(new ImageAdapter(getActivity()));

        return view;
    }

    public class ImageAdapter extends BaseAdapter {

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return ImageDownloadManager.getInstance().getProductEquipmentUrlList().length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View view;

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.equipment_item, null);

                NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.imageview_product_detail_equipment);
                imageView.setErrorImageResId(R.drawable.no_image);
                imageView.setImageUrl(ImageDownloadManager.getInstance().getProductEquipmentUrlList()[position], ImageCacheManager.getInstance().getImageLoader());

                TextView textViewName = (TextView) view.findViewById(R.id.textview_product_detail_equipment);
                textViewName.setText(productNameList[position]);

            } else {
                view = convertView;
            }

            view.refreshDrawableState();

            return view;
        }

        private Context mContext;

        private Integer[] productNameList = {
                R.string.car_equipment, R.string.car_equipment,
                R.string.car_equipment, R.string.car_equipment,
                R.string.car_equipment, R.string.car_equipment,
                R.string.car_equipment, R.string.car_equipment,
                R.string.car_equipment, R.string.car_equipment,
                R.string.car_equipment, R.string.car_equipment,
        };
    }

}
package com.luyuan.pad.mberp.ui;

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

import com.android.volley.toolbox.NetworkImageView;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.model.ProductThumbData;
import com.luyuan.pad.mberp.util.GlobalConstantValues;
import com.luyuan.pad.mberp.util.ImageCacheManager;
import com.luyuan.pad.mberp.util.ImageDownloadManager;

public class ProductSubBatteryFragment extends Fragment implements AdapterView.OnItemClickListener {

    private LayoutInflater layoutInflater;
    private ProductThumbData productThumbData;
    private GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_product_sub_battery, null);

        gridView = (GridView) view.findViewById(R.id.gridview_product_list_luxury);
        fetchProductThumbData();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.PARAM_CAR_TYPE, getArguments().getString(GlobalConstantValues.PARAM_CAR_TYPE));
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
            return ImageDownloadManager.getInstance().getProductThumbData().getProductThumbInfos().size();
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
                view = layoutInflater.inflate(R.layout.product_item, null);

                NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.imageview_product_list);
                imageView.setDefaultImageResId(R.drawable.no_image);
//                imageView.setErrorImageResId(R.drawable.no_image);
                imageView.setImageUrl(ImageDownloadManager.getInstance().getProductThumbData().getProductThumbInfos().get(position).getUrl(), ImageCacheManager.getInstance().getImageLoader());

                TextView textViewName = (TextView) view.findViewById(R.id.textview_product_list_info_name);
                textViewName.setText(ImageDownloadManager.getInstance().getProductThumbData().getProductThumbInfos().get(position).getName());

                TextView textViewDesc1 = (TextView) view.findViewById(R.id.textview_product_list_info_model);
                textViewDesc1.setText(ImageDownloadManager.getInstance().getProductThumbData().getProductThumbInfos().get(position).getModel());

                TextView textViewDesc2 = (TextView) view.findViewById(R.id.textview_product_list_info_battery);
                textViewDesc2.setText(ImageDownloadManager.getInstance().getProductThumbData().getProductThumbInfos().get(position).getBattery());

                TextView textViewDesc3 = (TextView) view.findViewById(R.id.textview_product_list_info_endurance);
                textViewDesc3.setText(ImageDownloadManager.getInstance().getProductThumbData().getProductThumbInfos().get(position).getEndurance());

                TextView textViewDesc4 = (TextView) view.findViewById(R.id.textview_product_list_info_series);
                textViewDesc4.setText(ImageDownloadManager.getInstance().getProductThumbData().getProductThumbInfos().get(position).getSeries());

            } else {
                view = convertView;
            }

            view.refreshDrawableState();

            return view;
        }
    }

}


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
import com.luyuan.pad.mberp.util.GlobalConstantValues;
import com.luyuan.pad.mberp.util.ImageCacheManager;
import com.luyuan.pad.mberp.util.ImageDownloadManager;

public class ProductSubSimpleFragment extends Fragment implements AdapterView.OnItemClickListener {

    private LayoutInflater layoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_product_sub_simple, null);

        GridView g = (GridView) view.findViewById(R.id.gridview_product_list_simple);
        g.setAdapter(new ImageAdapter(getActivity()));
        g.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(GlobalConstantValues.CAR_TYPE, getArguments().getString(GlobalConstantValues.CAR_TYPE));
        productDetailFragment.setArguments(args);

        fragmentTransaction.replace(R.id.frame_content, productDetailFragment);
        fragmentTransaction.commit();
    }

    public class ImageAdapter extends BaseAdapter {

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return ImageDownloadManager.getInstance().getProductThumbUrlList().length;
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
                imageView.setErrorImageResId(R.drawable.no_image);
                imageView.setImageUrl(ImageDownloadManager.getInstance().getProductThumbUrlList()[position], ImageCacheManager.getInstance().getImageLoader());

                TextView textViewName = (TextView) view.findViewById(R.id.textview_product_list_name);
                textViewName.setText(productNameList[position]);

                TextView textViewDesc1 = (TextView) view.findViewById(R.id.textview_product_list_desc1);
                textViewDesc1.setText(productDesc1List[position]);

                TextView textViewDesc2 = (TextView) view.findViewById(R.id.textview_product_list_desc2);
                textViewDesc2.setText(productDesc2List[position]);

                TextView textViewDesc3 = (TextView) view.findViewById(R.id.textview_product_list_desc3);
                textViewDesc3.setText(productDesc3List[position]);

                TextView textViewDesc4 = (TextView) view.findViewById(R.id.textview_product_list_desc4);
                textViewDesc4.setText(productDesc4List[position]);

            } else {
                view = convertView;
            }

            view.refreshDrawableState();

            return view;
        }

        private Context mContext;

        private Integer[] productNameList = {
                R.string.product_name, R.string.product_name,
                R.string.product_name, R.string.product_name,
                R.string.product_name, R.string.product_name,
                R.string.product_name, R.string.product_name,
                R.string.product_name, R.string.product_name,
                R.string.product_name, R.string.product_name,
                R.string.product_name, R.string.product_name,
                R.string.product_name, R.string.product_name,
                R.string.product_name, R.string.product_name,
                R.string.product_name, R.string.product_name,
                R.string.product_name, R.string.product_name,
                R.string.product_name, R.string.product_name,
        };

        private Integer[] productDesc1List = {
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
                R.string.product_desc1, R.string.product_desc1,
        };

        private Integer[] productDesc2List = {
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
                R.string.product_desc2, R.string.product_desc2,
        };

        private Integer[] productDesc3List = {
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
                R.string.product_desc3, R.string.product_desc3,
        };

        private Integer[] productDesc4List = {
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
                R.string.product_desc4, R.string.product_desc4,
        };
    }

}


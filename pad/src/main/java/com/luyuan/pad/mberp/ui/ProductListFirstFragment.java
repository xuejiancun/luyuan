package com.luyuan.pad.mberp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.ImageCacheManager;

import java.util.ArrayList;

public class ProductListFirstFragment extends Fragment {

    private LayoutInflater layoutInflater;

    private ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();

    private String[] urlList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_product_list_first, null);

        fetchImageList();

        GridView g = (GridView) view.findViewById(R.id.gridview_product_list_first);
        g.setAdapter(new ImageAdapter(getActivity()));

        return view;
    }

    private void fetchImageList() {

        if (imageViewList.size() > 0) {
            return;
        }

        urlList = new String[]{"http://www.luyuan.cn/UploadPhoto/2014/3/21/2874bfec-e924-4e0e-bf3f-eaa2b581c351.png",
                "http://www.luyuan.cn/UploadPhoto/2014/3/23/0c6940b3-03da-4102-a03c-1417137388b1.png",
                "http://www.luyuan.cn/UploadPhoto/2014/4/14/4f54abd0-4051-4b86-a3bb-87e1a05b73ec.png",
                "http://www.luyuan.cn/UploadPhoto/2014/4/14/8c235d65-39de-4c4c-88bf-d6a0bfa7d0f2.png",
                "http://www.luyuan.cn/UploadPhoto/2014/4/29/ca42f614-4680-4099-af05-72609fd4f59a.png",
                "http://www.luyuan.cn/UploadPhoto/2014/4/29/f4509f98-9d73-4004-b8e2-ed472044e53d.png",
                "http://www.luyuan.cn/UploadPhoto/2014/4/29/959b3f16-d975-458b-889b-7d67c23d1901.png",
                "http://www.luyuan.cn/UploadPhoto/2014/4/29/dd89a158-e6c2-4810-907f-30cdcdb1c1f6.png",
                "http://www.luyuan.cn/UploadPhoto/2014/5/12/e041d643-f253-4db5-86ab-03908b38eeaf.png",
                "http://www.luyuan.cn/UploadPhoto/2014/5/12/c302cb1f-0cc4-495c-af34-4fe7d40aba24.png",
                "http://www.luyuan.cn/UploadPhoto/2014/5/16/5c5a4f94-dd4f-46ec-87dd-65398abdfb45.png",
                "http://www.luyuan.cn/UploadPhoto/2014/5/24/da618a81-bf41-4edb-8bc4-d3c175ae1ecb.png",
        };

        for (String url : urlList) {
            ImageView imageView = new ImageView(getActivity());
            ImageLoader imageLoader = ImageCacheManager.getInstance().getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(imageView, R.drawable.no_image, R.drawable.no_image));
            imageViewList.add(imageView);
        }
    }

    public class ImageAdapter extends BaseAdapter {

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return imageViewList.size();
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

                ImageView imageView = (ImageView) view.findViewById(R.id.imageview_product_list);
                imageView.setImageDrawable(imageViewList.get(position).getDrawable());

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


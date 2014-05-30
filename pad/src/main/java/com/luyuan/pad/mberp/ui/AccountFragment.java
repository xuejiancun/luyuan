package com.luyuan.pad.mberp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.android.volley.toolbox.ImageLoader;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.ImageCacheManager;

import java.util.ArrayList;

public class AccountFragment extends Fragment implements AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {

    private ImageSwitcher imageSwitcher;

    private ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();

    private String[] urlList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.image_switcher, null);

        fetchImageList();

        imageSwitcher = (ImageSwitcher) view.findViewById(R.id.switcher);
        imageSwitcher.setFactory(this);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));

        Gallery g = (Gallery) view.findViewById(R.id.gallery);
        g.setAdapter(new ImageAdapter(getActivity()));
        g.setOnItemSelectedListener(this);

        return view;
    }

    private void fetchImageList() {

        if (imageViewList.size() > 0) {
            return;
        }

        urlList = new String[]{"http://luyuan.cn/WEBMANAGE/Upload//6353651589845312507035132.jpg",
                "http://www.luyuan.cn/WebManage/Upload/image/MQE-CS6020-G2/XQ/1.jpg",
                "http://www.luyuan.cn/WebManage/Upload/image/MS-CS6020-G1/XQ/1.jpg",
                "http://luyuan.cn/webmanage/Upload//6353307865712500004300104.jpg",
                "http://luyuan.cn/WEBMANAGE/Upload//6353332397400000008618787.jpg"};

        for (String url : urlList) {
            ImageView imageView = new ImageView(getActivity());
            ImageLoader imageLoader = ImageCacheManager.getInstance().getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(imageView, R.drawable.no_image, R.drawable.error_image));
            imageViewList.add(imageView);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        imageSwitcher.setImageDrawable(imageViewList.get(position).getDrawable());
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    public View makeView() {
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundColor(0xFF000000);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        return imageView;
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
            ImageView imageView = imageViewList.get(position);
            imageView.setAdjustViewBounds(true);
            imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            imageView.setBackgroundResource(R.drawable.picture_frame);
            return imageView;
        }

        private Context mContext;

    }


}

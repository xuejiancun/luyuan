package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.GlobalConstantValues;
import com.luyuan.pad.mberp.util.ImageCacheManager;

public class ImageSlideFragment extends Fragment {

    private int index;
    private String url;

    public static ImageSlideFragment create(int imageIndex, String url) {
        ImageSlideFragment fragment = new ImageSlideFragment();
        Bundle args = new Bundle();
        args.putInt(GlobalConstantValues.PARAM_IMAGE_INDEX, imageIndex);
        args.putString(GlobalConstantValues.PARAM_IMAGE_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageSlideFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt(GlobalConstantValues.PARAM_IMAGE_INDEX);
        url = getArguments().getString(GlobalConstantValues.PARAM_IMAGE_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_image_slide, null);
        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.imageview_slide);
        imageView.setDefaultImageResId(R.drawable.loading);
        imageView.setErrorImageResId(R.drawable.error);
        imageView.setImageUrl(url, ImageCacheManager.getInstance().getImageLoader());
        return view;
    }

    public int getIndex() {
        return index;
    }

}

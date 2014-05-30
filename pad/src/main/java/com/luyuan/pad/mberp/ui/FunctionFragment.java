package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.ImageCacheManager;

public class FunctionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function, container, false);

        NetworkImageView networkImageView = (NetworkImageView) view.findViewById(R.id.networkimg_product);
        networkImageView.setImageUrl("http://luyuan.cn/WEBMANAGE/Upload//6353651589845312507035132.jpg", ImageCacheManager.getInstance().getImageLoader());

        return view;
    }
}

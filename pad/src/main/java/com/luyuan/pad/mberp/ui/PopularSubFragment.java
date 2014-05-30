package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.ImageCacheManager;

public class PopularSubFragment extends Fragment {

    public static final String ARG_PAGE = "page";

    private int mPageNumber;

    private String[] urlList;

    public static PopularSubFragment create(int pageNumber) {
        PopularSubFragment fragment = new PopularSubFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PopularSubFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);

        urlList = new String[]{"http://luyuan.cn/WEBMANAGE/Upload//6353651589845312507035132.jpg",
                "http://www.luyuan.cn/WebManage/Upload/image/MQE-CS6020-G2/XQ/1.jpg",
                "http://www.luyuan.cn/WebManage/Upload/image/MS-CS6020-G1/XQ/1.jpg",
                "http://luyuan.cn/webmanage/Upload//6353307865712500004300104.jpg",
                "http://luyuan.cn/WEBMANAGE/Upload//6353332397400000008618787.jpg"};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_popular_slide_page, container, false);

        NetworkImageView networkImageView = (NetworkImageView) view.findViewById(R.id.networkimg_popular);
        networkImageView.setImageUrl(urlList[getPageNumber()], ImageCacheManager.getInstance().getImageLoader());

        return view;
    }

    public int getPageNumber() {
        return mPageNumber;
    }
}

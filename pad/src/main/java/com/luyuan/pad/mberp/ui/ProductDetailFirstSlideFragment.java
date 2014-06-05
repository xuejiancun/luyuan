package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.util.ImageCacheManager;
import com.luyuan.pad.mberp.util.ImageDownloadManager;

public class ProductDetailFirstSlideFragment extends Fragment {

    public static final String ARG_PAGE = "page";

    private int pageNumber;

    public static ProductDetailFirstSlideFragment create(int pageNumber) {
        ProductDetailFirstSlideFragment fragment = new ProductDetailFirstSlideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductDetailFirstSlideFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_product_detail_first_slide_page, container, false);

        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.imageview_product_detail);
        imageView.setImageUrl(ImageDownloadManager.getInstance().getProductDetailUrlList()[getPageNumber()], ImageCacheManager.getInstance().getImageLoader());

        return view;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}

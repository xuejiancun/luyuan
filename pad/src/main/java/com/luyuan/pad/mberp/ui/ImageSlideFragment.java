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
import com.luyuan.pad.mberp.util.ImageDownloadManager;

public class ImageSlideFragment extends Fragment {

    private int imageIndex;
    private String imageType;

    public static ImageSlideFragment create(int imageIndex, String imageType) {
        ImageSlideFragment fragment = new ImageSlideFragment();
        Bundle args = new Bundle();
        args.putInt(GlobalConstantValues.IMAGE_INDEX, imageIndex);
        args.putString(GlobalConstantValues.IMAGE_TYPE, imageType);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageSlideFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageIndex = getArguments().getInt(GlobalConstantValues.IMAGE_INDEX);
        imageType = getArguments().getString(GlobalConstantValues.IMAGE_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_image_slide, null);

        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.imageview_slide);
        imageView.setErrorImageResId(R.drawable.no_image);
        if (imageType.equals(GlobalConstantValues.IMAGE_HONOR)) {
            imageView.setImageUrl(ImageDownloadManager.getInstance().getHonorUrlList()[getImageIndex()], ImageCacheManager.getInstance().getImageLoader());
        } else {
            imageView.setImageUrl(ImageDownloadManager.getInstance().getProductDetailUrlList()[getImageIndex()], ImageCacheManager.getInstance().getImageLoader());
        }

        return view;
    }

    public int getImageIndex() {
        return imageIndex;
    }
    
}

package com.luyuan.pad.mberp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
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
    private String url = "";

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

        Bundle args = getArguments();
        if (args != null) {
            index = args.getInt(GlobalConstantValues.PARAM_IMAGE_INDEX);
            if (args.getString(GlobalConstantValues.PARAM_IMAGE_URL) != null) {
                url = getArguments().getString(GlobalConstantValues.PARAM_IMAGE_URL);
            }
        } else {
            Dialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.app_param_error)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create();
            alertDialog.show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = (View) inflater.inflate(R.layout.fragment_image_slide, null);

        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.imageview_slide);
        imageView.setDefaultImageResId(R.drawable.loading_large);
        imageView.setErrorImageResId(R.drawable.error_large);
        imageView.setImageUrl(url, ImageCacheManager.getInstance().getLargeImageLoader());

        return view;
    }

}

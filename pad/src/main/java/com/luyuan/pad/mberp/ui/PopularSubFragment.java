package com.luyuan.pad.mberp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.luyuan.pad.mberp.R;

public class PopularSubFragment extends Fragment {

    public static final String ARG_PAGE = "page";

    private int pageNumber;

    private int[] imageList = new int[]{R.drawable.popular_fx,
            R.drawable.popular_fy, R.drawable.popular_hko,
            R.drawable.popular_hqc, R.drawable.popular_jfq,
            R.drawable.popular_jfu, R.drawable.popular_jfz,
            R.drawable.popular_jjk2, R.drawable.popular_lds,
            R.drawable.popular_mg, R.drawable.popular_mk,
            R.drawable.popular_mna, R.drawable.popular_mu,
            R.drawable.popular_mv, R.drawable.popular_qo,
    };

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
        pageNumber = getArguments().getInt(ARG_PAGE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_popular_slide_page, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview_popular);
        imageView.setImageResource(imageList[getPageNumber()]);

        return view;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}

package com.luyuan.mobile.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.luyuan.mobile.R;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private LayoutInflater layoutInflater;

    private String[] shortcuts = new String[]{"Shortcut", "Shortcut", "Shortcut",
            "Shortcut", "Shortcut", "Shortcut", "Shortcut", "Shortcut",
            "Shortcut", "Shortcut", "Shortcut", "Shortcut", "Shortcut",
            "Shortcut", "Shortcut", "Shortcut", "Shortcut", "Shortcut",
            "Shortcut", "Shortcut", "Shortcut", "Shortcut", "Shortcut",
            "Shortcut", "Shortcut", "Shortcut", "Shortcut", "Shortcut",
            "Shortcut", "Shortcut", "Shortcut", "Shortcut", "Shortcut",
            "Shortcut", "Shortcut", "Shortcut", "Shortcut"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_home, null);
        gridView = (GridView) view.findViewById(R.id.gridview_shortcut_list);
        gridView.setAdapter(new ShortCutAdapter(getActivity()));

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public class ShortCutAdapter extends BaseAdapter {

        private Context mContext;

        public ShortCutAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return shortcuts.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View view = layoutInflater.inflate(R.layout.item_shortcut, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.imageview_shortcut);
            imageView.setImageResource(R.drawable.function_sales);

            TextView textView = (TextView) view.findViewById(R.id.textview_shortcut);
            textView.setText(shortcuts[position]);

            return view;
        }
    }


}
package com.luyuan.mobile.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.luyuan.mobile.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private GridView gridView;
    private LayoutInflater layoutInflater;

    private ArrayList<String> shortcuts = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < 28; i++) {
            shortcuts.add(i, "六个名称" + i);
        }

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_home, null);
        gridView = (GridView) view.findViewById(R.id.gridview_shortcut_list);
        gridView.setAdapter(new ShortCutAdapter(getActivity()));
        registerForContextMenu(gridView);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 1, R.string.top);
        menu.add(0, 2, 2, R.string.delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == 1) {
            String shortcut = shortcuts.get(info.position);
            shortcuts.remove(info.position);
            shortcuts.add(0, shortcut);
        } else {
            shortcuts.remove(info.position);
        }
        gridView.setAdapter(new ShortCutAdapter(getActivity()));

        return true;
    }

    public class ShortCutAdapter extends BaseAdapter {

        private Context mContext;

        public ShortCutAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return shortcuts.size();
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
            imageView.setImageResource(R.drawable.function_item_sales);

            TextView textView = (TextView) view.findViewById(R.id.textview_shortcut);
            textView.setTextSize(12);
            textView.setText(shortcuts.get(position));

            view.setPadding(10, 5, 10, 5);

            return view;
        }
    }


}
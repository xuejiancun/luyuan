package com.luyuan.mobile.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import com.luyuan.mobile.model.Shortcut;
import com.luyuan.mobile.util.DatabaseHelper;
import com.luyuan.mobile.util.MyGlobal;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private GridView gridView;
    private LayoutInflater layoutInflater;

    private List<Shortcut> shortcuts = new ArrayList<Shortcut>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadShortCuts();

        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_home, null);
        gridView = (GridView) view.findViewById(R.id.gridview_shortcut_list);
        gridView.setAdapter(new ShortCutAdapter(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyGlobal.getFunctionActivity(shortcuts.get(i).getCode()));
                intent.putExtra("function", shortcuts.get(i).getCode());
                intent.putExtra("tab", "home");

                startActivity(intent);
            }
        });
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
            Shortcut shortcut = shortcuts.get(info.position);
            shortcuts.remove(info.position);
            shortcuts.add(0, shortcut);
        } else {
            shortcuts.remove(info.position);
        }
        gridView.setAdapter(new ShortCutAdapter(getActivity()));
        updateShortCuts();

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
            textView.setText(shortcuts.get(position).getName());

            view.setPadding(10, 5, 10, 5);

            return view;
        }
    }

    private void updateShortCuts() {
        removeShortcuts();
        DatabaseHelper instance = DatabaseHelper.getInstance(getActivity());
        for (Shortcut shortcut : this.shortcuts) {
            instance.createShortcut(shortcut.getCode(), shortcut.getName());
        }
    }

    private void loadShortCuts() {
        DatabaseHelper instance = DatabaseHelper.getInstance(getActivity());
        List<Shortcut> shortcuts = instance.loadShortcuts();
        this.shortcuts = shortcuts;
    }

    private void removeShortcuts() {
        DatabaseHelper instance = DatabaseHelper.getInstance(getActivity());
        instance.removeShortcuts();
    }

}
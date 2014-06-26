package com.luyuan.mobile.function;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.luyuan.mobile.R;

public class WarehouseVoucherMakeFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_warehouse_voucher_make, null);
        listView = (ListView) view.findViewById(R.id.listview_warehouse_choose_list);
        ((Button) view.findViewById(R.id.button_next_warehouse_voucher_make)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WarehouseVoucherConfirmFragment warehouseVoucherConfirmFragment = new WarehouseVoucherConfirmFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                // TODO
                // pass the args to warehouseVoucherConfirmFragment

                fragmentTransaction.replace(R.id.frame_content_warehouse_voucher_manager, warehouseVoucherConfirmFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Bundle args = getArguments();
        if (args != null && args.getBundle("data") != null) {
            Bundle data = args.getBundle("data");
            ((TextView) view.findViewById(R.id.textview_order_no_warehouse_voucher_make)).setText(data.getString("code"));
            ((TextView) view.findViewById(R.id.textview_order_by_warehouse_voucher_make)).setText(data.getString("preparedBy"));
            ((TextView) view.findViewById(R.id.textview_order_unit_warehouse_voucher_make)).setText(data.getString("unitName"));
        }

        String[] values = new String[]{"Warehouse 1", "Warehouse 2", "Warehouse 3",
                "Warehouse 4", "Warehouse 5", "Warehouse 6", "Warehouse 7", "Warehouse 8"};

        ArrayAdapter<String> files = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, values);

        listView.setAdapter(files);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listView.setItemChecked(i, true);
    }

}
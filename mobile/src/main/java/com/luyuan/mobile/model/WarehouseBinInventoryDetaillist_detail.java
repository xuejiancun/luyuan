package com.luyuan.mobile.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luyuan.mobile.R;

import java.util.List;
import java.util.Map;

public class WarehouseBinInventoryDetaillist_detail extends BaseAdapter {
    @SuppressWarnings("unused")
    private Context context;                        //运行上下文
    private List<Map<String, Object>> listItems;    //商品信息集合
    private LayoutInflater listContainer;           //视图容器
    @SuppressWarnings("unused")
    private boolean[] hasChecked;                   //记录商品选中状态

    public final class ListItemView {                //自定义控件集合
        public TextView ProductCode;
        public TextView PrefixName;
        public TextView itemSpec;
        public TextView Qty;
        public TextView ActualQty;
    }


    public WarehouseBinInventoryDetaillist_detail(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文
        this.listItems = listItems;
        hasChecked = new boolean[getCount()];
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return listItems.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }


    /**
     * ListView Item设置
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        //自定义视图
        ListItemView listItemView = null;
        if (convertView == null) {
            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout
                    .warehouse_whbininventorychecklist_activity, null);
            //获取控件对象
            listItemView.ProductCode = (TextView) convertView.findViewById(R.id.ProductCodeItem1);
            listItemView.PrefixName = (TextView) convertView.findViewById(R.id.PrefixNameItem1);
            listItemView.itemSpec = (TextView) convertView.findViewById(R.id.itemSpecItem1);
            listItemView.Qty = (TextView) convertView.findViewById(R.id.QtyItem1);
            listItemView.ActualQty = (TextView) convertView.findViewById(R.id.ActualQtyItem1);
            //设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }

        listItemView.ProductCode.setText((String) listItems.get(position).get("ProductCode"));
        listItemView.PrefixName.setText((String) listItems.get(position).get("PrefixName"));
        listItemView.itemSpec.setText((String) listItems.get(position).get("itemSpec"));
        listItemView.Qty.setText((String) listItems.get(position).get("Qty"));
        listItemView.ActualQty.setText((String) listItems.get(position).get("ActualQty"));


        return convertView;
    }
}

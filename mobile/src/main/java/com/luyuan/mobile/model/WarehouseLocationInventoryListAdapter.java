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

public class WarehouseLocationInventoryListAdapter extends BaseAdapter{

	@SuppressWarnings("unused")
	private Context context;                        //运行上下文
	private List<Map<String, Object>> listItems;    //商品信息集合
	private LayoutInflater listContainer;           //视图容器
	@SuppressWarnings("unused")
	private boolean[] hasChecked;                   //记录商品选中状态
	public final class ListItemView{                //自定义控件集合
		public TextView wbCode;
		public TextView wbName;
		public TextView ProductCode;
		public TextView PrefixName;
		public TextView SpecType;
		public TextView Qty;
		public TextView FreezeQty;
	}


	public WarehouseLocationInventoryListAdapter(Context context, List<Map<String, Object>> listItems) {
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
		// TODO Auto-generated method stub
		//Log.e("method", "getView");
		//final int selectID = position;
		//自定义视图
		ListItemView  listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			//获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.warehouse_locationinventory_package_locationinventorylist, null);
			//获取控件对象
			listItemView.wbCode = (TextView)convertView.findViewById(R.id.wbCodeItem1);
			listItemView.wbName = (TextView)convertView.findViewById(R.id.wbNameItem1);
			listItemView.ProductCode = (TextView)convertView.findViewById(R.id.ProductCodeItem1);
			listItemView.PrefixName = (TextView)convertView.findViewById(R.id.PrefixNameItem1);
			listItemView.SpecType = (TextView)convertView.findViewById(R.id.SpecTypeItem1);
			listItemView.Qty = (TextView)convertView.findViewById(R.id.QtyItem1);
			listItemView.FreezeQty = (TextView)convertView.findViewById(R.id.FreezeQtyItem1);
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}
		//Log.e("image", (String) listItems.get(position).get("title"));  //测试
		//Log.e("image", (String) listItems.get(position).get("info"));

		//设置文字和图片
		//listItemView.image.setBackgroundResource((Integer) listItems.get(
		//      position).get("image"));
		listItemView.wbCode.setText((String) listItems.get(position).get("wbCode"));
		listItemView.wbName.setText((String) listItems.get(position).get("wbName"));
		listItemView.ProductCode.setText((String) listItems.get(position).get("ProductCode"));
		listItemView.PrefixName.setText((String) listItems.get(position).get("PrefixName"));
		listItemView.SpecType.setText((String) listItems.get(position).get("SpecType"));
		listItemView.Qty.setText((String) listItems.get(position).get("Qty"));
		listItemView.FreezeQty.setText((String) listItems.get(position).get("FreezeQty"));



		return convertView;
	}
}

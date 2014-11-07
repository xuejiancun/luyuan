package com.luyuan.mobile.production;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class CustomLinearLayout extends LinearLayout {
	private BaseAdapter mAdapter;
	public void setCustomAdapter(BaseAdapter adapter){
		this.mAdapter = adapter;
		if(mAdapter == null)
			return;
		fillLinearLayout();
	}
	/**
	 * 根据适配器来画出布局
	 */
	public void fillLinearLayout() {
		int count = mAdapter.getCount();
		for (int i = 0; i < count; i++) {
			View v = mAdapter.getView(i, null, null);
			addView(v, i);
		}
	}

	public CustomLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomLinearLayout(Context context) {
		super(context);
	}
}
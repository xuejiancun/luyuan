package com.luyuan.mobile.production;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.luyuan.mobile.R;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.MyGlobal;

import java.util.Calendar;

public class WarehouseBinManagerActivity extends Activity  {

    private String tab = "home";
	private Button pickDate_s = null;
	private Button pickTime_s = null;
	private Button pickDate_e = null;
	private Button pickTime_e = null;
	private Button button_search=null;
	private static final int SHOW_DATAPICK = 0;
	private static final int DATE_DIALOG_ID = 1;
	private static final int SHOW_TIMEPICK = 2;
	private static final int TIME_DIALOG_ID = 3;
	private static final int DATE_DIALOG_ID2 = 4;
	private static final int TIME_DIALOG_ID2 = 5;
	private static final int SHOW_DATAPICK2 = 6;
	private static final int SHOW_TIMEPICK2 = 7;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_warehouse_whbininfo_search);

        setContentView(R.layout.warehouse_bininfosearch_fragment);
	    initializeViews();
	    final Calendar c = Calendar.getInstance();
	    mYear = c.get(Calendar.YEAR);
	    mMonth = c.get(Calendar.MONTH);
	    mDay = c.get(Calendar.DAY_OF_MONTH);

	    mHour = c.get(Calendar.HOUR_OF_DAY);
	    mMinute = c.get(Calendar.MINUTE);

	    setDateTime();
	    setTimeOfDay();
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("tab") != null) {
            tab = intent.getStringExtra("tab");
        }
	    button_search = (Button)findViewById(R.id.button_search);
	    button_search.setOnClickListener(new View.OnClickListener() {

		    @Override
		    public void onClick(View v) {
			//    WarehouseBinSearchDetailFragment warehouseBinSearchDetailFragment = new
			//		    WarehouseBinSearchDetailFragment();
		//	    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

			    String wbcode=((EditText) findViewById(R.id.edittext_bincode)).getText().toString
					    ().trim();
			    String date_s=((Button) findViewById(R.id.pickdate_start)).getText()
					    .toString().trim();
			    String time_s=((Button) findViewById(R.id.picktime_start))
					    .getText().toString().trim();
			    String date_e=((Button) findViewById(R.id.pickdate_end)).getText()
					    .toString().trim();
			    String time_e=((Button) findViewById(R.id.picktime_end))
					    .getText().toString().trim();

			    if(wbcode.isEmpty())
			    {
				    new AlertDialog.Builder(WarehouseBinManagerActivity.this)
						    .setMessage(R.string.wbcode_empty)
						    .setTitle(R.string.dialog_hint)
						    .setPositiveButton(R.string.dialog_confirm, null)
						    .create()
						    .show();

				    return;
			    }
			    if(time_s.isEmpty())
			    {
				    new AlertDialog.Builder(WarehouseBinManagerActivity.this)
						    .setMessage(R.string.starttime_empty)
						    .setTitle(R.string.dialog_hint)
						    .setPositiveButton(R.string.dialog_confirm, null)
						    .create()
						    .show();

				    return;
			    }
			    if(time_e.isEmpty())
			    {
				    new AlertDialog.Builder(WarehouseBinManagerActivity.this)
						    .setMessage(R.string.endtime_empty)
						    .setTitle(R.string.dialog_hint)
						    .setPositiveButton(R.string.dialog_confirm, null)
						    .create()
						    .show();

				    return;
			    }
//			    Bundle args = new Bundle();
//			    Bundle data = new Bundle();
//			    data.putString("time_s", time_s);
//			    data.putString("time_e", time_e);
//			    data.putString("wbcode", wbcode);
//			    args.putBundle("data", data);
//			    warehouseBinSearchDetailFragment.setArguments(args);
//			    fragmentTransaction.replace(R.id.frame_content, warehouseBinSearchDetailFragment);
//			    fragmentTransaction.addToBackStack(null);
//			    fragmentTransaction.commit();
			    Intent intent = new Intent(getApplicationContext(), WarehouseBinSearchDetailActivity
					    .class);
			    intent.putExtra("time_s", time_s);
			    intent.putExtra("time_e", time_e);
			    intent.putExtra("date_s", date_s);
			    intent.putExtra("date_e", date_e);
			    intent.putExtra("wbcode", wbcode);
			    intent.putExtra("tab", tab);
			    startActivity(intent);
		    }
	    });
    }
	/**
	 * 初始化控件和UI视图
	 */
	private void initializeViews(){
		pickDate_s = (Button) findViewById(R.id.pickdate_start);
		pickTime_s = (Button)findViewById(R.id.picktime_start);
		pickDate_e = (Button) findViewById(R.id.pickdate_end);
		pickTime_e = (Button)findViewById(R.id.picktime_end);
		pickDate_s.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				if (pickDate_s.equals((Button) v)) {
					msg.what = WarehouseBinManagerActivity.SHOW_DATAPICK;
				}
				WarehouseBinManagerActivity.this.dateandtimeHandler.sendMessage(msg);
			}
		});

		pickDate_e.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				if (pickDate_e.equals((Button) v)) {
					msg.what = WarehouseBinManagerActivity.SHOW_DATAPICK2;
				}
				WarehouseBinManagerActivity.this.dateandtimeHandler.sendMessage(msg);
			}
		});
		pickTime_s.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				if (pickTime_s.equals((Button) v)) {
					msg.what = WarehouseBinManagerActivity.SHOW_TIMEPICK;
				}
				WarehouseBinManagerActivity.this.dateandtimeHandler.sendMessage(msg);
			}
		});
		pickTime_e.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				if (pickTime_e.equals((Button) v)) {
					msg.what = WarehouseBinManagerActivity.SHOW_TIMEPICK2;
				}
				WarehouseBinManagerActivity.this.dateandtimeHandler.sendMessage(msg);
			}
		});
	}
	/**
	 * 设置日期
	 */
	private void setDateTime(){
		final Calendar c = Calendar.getInstance();

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		updateDateDisplay();
		updateDateDisplay2();
	}

	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay(){
		pickDate_s.setText(new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
				.append((mDay < 10) ? "0" + mDay : mDay));
	}
	private void updateDateDisplay2(){
		pickDate_e.setText(new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
				.append((mDay < 10) ? "0" + mDay : mDay));
	}
	/**
	 * 日期控件的事件
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
		                      int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDateDisplay();
		}
	};
	private DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog
			.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
		                      int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDateDisplay2();
		}
	};
	/**
	 * 设置时间
	 */
	private void setTimeOfDay(){
		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		updateTimeDisplay();
		updateTimeDisplay2();
	}

	/**
	 * 更新时间显示
	 */
	private void updateTimeDisplay(){
		pickTime_s.setText(new StringBuilder().append(mHour).append(":")
				.append((mMinute < 10) ? "0" + mMinute : mMinute));
	}
	private void updateTimeDisplay2(){
		pickTime_e.setText(new StringBuilder().append(mHour).append(":")
				.append((mMinute < 10) ? "0" + mMinute : mMinute));
	}
	/**
	 * 时间控件事件
	 */
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;

			updateTimeDisplay();
		}
	};
	private TimePickerDialog.OnTimeSetListener mTimeSetListener2 = new TimePickerDialog
			.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;

			updateTimeDisplay2();
		}
	};
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
						mDay);
			case TIME_DIALOG_ID:
				return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
			case DATE_DIALOG_ID2:
				return new DatePickerDialog(this, mDateSetListener2, mYear, mMonth,
						mDay);
			case TIME_DIALOG_ID2:
				return new TimePickerDialog(this, mTimeSetListener2, mHour, mMinute, true);
		}

		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
			case DATE_DIALOG_ID:
				((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
				break;
			case TIME_DIALOG_ID:
				((TimePickerDialog) dialog).updateTime(mHour, mMinute);
				break;
			case DATE_DIALOG_ID2:
				((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
				break;
			case TIME_DIALOG_ID2:
				((TimePickerDialog) dialog).updateTime(mHour, mMinute);
				break;
		}
	}

	/**
	 * 处理日期和时间控件的Handler
	 */
	Handler dateandtimeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case WarehouseBinManagerActivity.SHOW_DATAPICK:
					showDialog(DATE_DIALOG_ID);
					break;
				case WarehouseBinManagerActivity.SHOW_TIMEPICK:
					showDialog(TIME_DIALOG_ID);
					break;
				case WarehouseBinManagerActivity.SHOW_DATAPICK2:
					showDialog(DATE_DIALOG_ID2);
					break;
				case WarehouseBinManagerActivity.SHOW_TIMEPICK2:
					showDialog(TIME_DIALOG_ID2);
					break;
			}
		}

	};
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("stId", MyGlobal.getUser().getStId());
            intent.putExtra("tab", tab);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

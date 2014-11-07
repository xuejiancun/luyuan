package com.luyuan.mobile.production;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import com.luyuan.mobile.R;
import com.luyuan.mobile.model.ScheduleInfo;
import com.luyuan.mobile.util.MyGlobal;

import java.util.Calendar;
import java.util.Date;

public class WarehouseBinInfoSearchFragment extends Fragment{

	private static String flag;
	private static Date date;
	private static ScheduleInfo scheduleInfo;
	private Calendar calendar;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int min;

	private ProgressDialog dialog;
	private Button buttonStartDate;
	private Button buttonEndDate;
	private Button buttonStartTime;
	private Button buttonEndTime;
	private EditText editTextContent;
	private Switch switchPush;

	private Date startDate = new Date();
	private Date endDate = new Date();
	private DatePickerDialog.OnDateSetListener onStartDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
			year = i;
			month = i2;
			day = i3;

			buttonStartDate.setText(MyGlobal.SIMPLE_DATE_FORMAT_WITHOUT_TIME.format(new Date(i - 1900, i2, i3)));
			startDate.setYear(i - 1900);
			startDate.setMonth(i2);
			startDate.setDate(i3);
		}
	};
	private DatePickerDialog.OnDateSetListener onEndDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
			year = i;
			month = i2;
			day = i3;

			buttonEndDate.setText(MyGlobal.SIMPLE_DATE_FORMAT_WITHOUT_TIME.format(new Date(i - 1900, i2, i3)));
			endDate.setYear(i - 1900);
			endDate.setMonth(i2);
			endDate.setDate(i3);
		}
	};
	private TimePickerDialog.OnTimeSetListener onStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker timePicker, int i, int i2) {
			hour = i;
			min = i2;

			buttonStartTime.setText((i < 10 ? "0" : "") + String.valueOf(i) + getText(R.string.colon) + (i2 < 10 ? "0" : "") + String.valueOf(i2));
			startDate.setHours(i);
			startDate.setMinutes(i2);
		}
	};
	private TimePickerDialog.OnTimeSetListener onEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker timePicker, int i, int i2) {
			hour = i;
			min = i2;

			buttonEndTime.setText((i < 10 ? "0" : "") + String.valueOf(i) + getText(R.string.colon) + (i2 < 10 ? "0" : "") + String.valueOf(i2));
			endDate.setHours(i);
			endDate.setMinutes(i2);
		}
	};

	@Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.warehouse_bininfosearch_fragment, null);
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hour = 12;
		min = 0;
		buttonStartDate = (Button) view.findViewById(R.id.button_start_date);
		buttonStartTime = (Button) view.findViewById(R.id.button_start_time);
		buttonEndDate = (Button) view.findViewById(R.id.button_end_date);
		buttonEndTime = (Button) view.findViewById(R.id.button_end_time);


		buttonStartDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), onStartDateSetListener, year, month, day);
				datePickerDialog.show();
			}
		});

		buttonStartTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onStartTimeSetListener, hour, min, true);
				timePickerDialog.show();
			}
		});

		buttonEndDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), onEndDateSetListener, year, month, day);
				datePickerDialog.show();
			}
		});

		buttonEndTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onEndTimeSetListener, hour, min, true);
				timePickerDialog.show();
			}
		});









	    return view;
    }



}
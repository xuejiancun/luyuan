package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.component.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class ScheduleManagerActivity extends Activity implements SearchView.OnQueryTextListener {

    private CalendarPickerView calendarPickerView;
    private AlertDialog theDialog;
    private CalendarPickerView dialogView;
    private Button buttonChooseDate;

    private Calendar calendar = Calendar.getInstance();

    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_schedule_manager);

        setContentView(R.layout.calendar_picker);

        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        buttonChooseDate = (Button) findViewById(R.id.button_choose_date);
        buttonChooseDate.setText(new StringBuilder()
                .append(mYear).append("-")
                .append(mMonth + 1).append("月")); // TODO

        buttonChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ScheduleManagerActivity.this, mDateSetListener, mYear, mMonth, mDay);
                DatePicker dp = datePickerDialog.getDatePicker();

                if (dp != null) {
                    ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
                }
                datePickerDialog.show();
            }
        });

        calendarPickerView = (CalendarPickerView) findViewById(R.id.calendar_view);
        calendarPickerView.init(new Date(mYear - 1900, mMonth, mDay))
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(new Date(mYear - 1900, mMonth, mDay));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.hint_enter_query));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        // rePlaceTabContentForSearch(MyGlobal.API_WAREHOUSE_VOUCHER_SEARCH + "&query=" + query);

        return true;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            buttonChooseDate.setText(new StringBuilder()
                    .append(mYear).append("-")
                    .append(mMonth + 1).append("月")); // TODO

            calendarPickerView.init(new Date(mYear - 1900, mMonth, mDay))
                    .inMode(CalendarPickerView.SelectionMode.SINGLE)
                    .withSelectedDate(new Date(mYear - 1900, mMonth, mDay));
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        boolean applyFixes = theDialog != null && theDialog.isShowing();
        if (applyFixes) {
            dialogView.unfixDialogDimens();
        }
        super.onConfigurationChanged(newConfig);
        if (applyFixes) {
            dialogView.post(new Runnable() {
                @Override
                public void run() {
                    dialogView.fixDialogDimens();
                }
            });
        }
    }

}

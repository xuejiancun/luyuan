package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.luyuan.mobile.R;
import com.luyuan.mobile.component.CalendarPickerView;
import com.luyuan.mobile.component.CalendarPickerView.SelectionMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class ScheduleManagerActivity extends Activity {
    private CalendarPickerView calendar;
    private AlertDialog theDialog;
    private CalendarPickerView dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_schedule_manager);

        setContentView(R.layout.sample_calendar_picker);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        calendar.init(lastYear.getTime(), nextYear.getTime())
                .inMode(SelectionMode.SINGLE)
                .withSelectedDate(new Date());

        final Button single = (Button) findViewById(R.id.button_single);
        final Button multi = (Button) findViewById(R.id.button_multi);
        final Button range = (Button) findViewById(R.id.button_range);
        final Button displayOnly = (Button) findViewById(R.id.button_display_only);
        final Button dialog = (Button) findViewById(R.id.button_dialog);
        final Button customized = (Button) findViewById(R.id.button_customized);
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single.setEnabled(false);
                multi.setEnabled(true);
                range.setEnabled(true);
                displayOnly.setEnabled(true);

                calendar.init(lastYear.getTime(), nextYear.getTime()) //
                        .inMode(SelectionMode.SINGLE) //
                        .withSelectedDate(new Date());
            }
        });

        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single.setEnabled(true);
                multi.setEnabled(false);
                range.setEnabled(true);
                displayOnly.setEnabled(true);

                Calendar today = Calendar.getInstance();
                ArrayList<Date> dates = new ArrayList<Date>();
                for (int i = 0; i < 5; i++) {
                    today.add(Calendar.DAY_OF_MONTH, 3);
                    dates.add(today.getTime());
                }
                calendar.init(new Date(), nextYear.getTime()) //
                        .inMode(SelectionMode.MULTIPLE) //
                        .withSelectedDates(dates);
            }
        });

        range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single.setEnabled(true);
                multi.setEnabled(true);
                range.setEnabled(false);
                displayOnly.setEnabled(true);

                Calendar today = Calendar.getInstance();
                ArrayList<Date> dates = new ArrayList<Date>();
                today.add(Calendar.DATE, 3);
                dates.add(today.getTime());
                today.add(Calendar.DATE, 5);
                dates.add(today.getTime());
                calendar.init(new Date(), nextYear.getTime()) //
                        .inMode(SelectionMode.RANGE) //
                        .withSelectedDates(dates);
            }
        });

        displayOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single.setEnabled(true);
                multi.setEnabled(true);
                range.setEnabled(true);
                displayOnly.setEnabled(false);

                calendar.init(new Date(), nextYear.getTime()) //
                        .inMode(SelectionMode.SINGLE) //
                        .withSelectedDate(new Date())
                        .displayOnly();
            }
        });

        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = (CalendarPickerView) getLayoutInflater().inflate(R.layout.dialog, null, false);
                dialogView.init(lastYear.getTime(), nextYear.getTime()) //
                        .withSelectedDate(new Date());
                theDialog =
                        new AlertDialog.Builder(ScheduleManagerActivity.this).setTitle("I'm a dialog!")
                                .setView(dialogView)
                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create();
                theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialogView.fixDialogDimens();
                    }
                });
                theDialog.show();
            }
        });

        customized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = (CalendarPickerView) getLayoutInflater() //
                        .inflate(R.layout.dialog_customized, null, false);
                dialogView.init(lastYear.getTime(), nextYear.getTime()).withSelectedDate(new Date());
                theDialog =
                        new AlertDialog.Builder(ScheduleManagerActivity.this).setTitle("Pimp my calendar !")
                                .setView(dialogView)
                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).create();
                theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialogView.fixDialogDimens();
                    }
                });
                theDialog.show();
            }
        });

        findViewById(R.id.done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toast = "Selected: " + calendar.getSelectedDate().getTime();
                Toast.makeText(ScheduleManagerActivity.this, toast, LENGTH_SHORT).show();
            }
        });

        Spinner s1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        showToast("Spinner1: position=" + position + " id=" + id);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showToast("Spinner1: unselected");
                    }
                });
        
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, null, mYear, mMonth, mDay);
        DatePicker dp = datePickerDialog.getDatePicker();
        if (dp != null) {
            ((ViewGroup)((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);

        }
        datePickerDialog.show();
    }

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

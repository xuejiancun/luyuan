package com.luyuan.mobile.function;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.luyuan.mobile.R;
import com.luyuan.mobile.component.CalendarPickerView;
import com.luyuan.mobile.model.DayData;
import com.luyuan.mobile.model.DayInfo;
import com.luyuan.mobile.model.LocationData;
import com.luyuan.mobile.model.ScheduleData;
import com.luyuan.mobile.model.ScheduleInfo;
import com.luyuan.mobile.model.SubordinateData;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.ui.MainActivity;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// 日程管理页面
public class ScheduleManagerActivity extends Activity implements SearchView.OnQueryTextListener {

    private CalendarPickerView calendarPickerView;
    private AlertDialog theDialog;
    private CalendarPickerView dialogView;
    private Button buttonChooseDate;
    private ProgressDialog dialog;
    private Calendar calendar = Calendar.getInstance();

    private int mYear;
    private int mMonth;
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (mYear == year && mMonth == monthOfYear) {
                return;
            }

            mYear = year;
            mMonth = monthOfYear;

            buttonChooseDate.setText(new StringBuilder()
                    .append(mYear).append(getText(R.string.cross))
                    .append(mMonth + 1).append(getText(R.string.month)));

            initCalendar();
        }
    };
    private int mDay;
    private int subordinatesIndex;
    private SubordinateData subordinateData;
    private String tab = "home";

    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.function_schedule_manage);

        setContentView(R.layout.schedule_manager_activity);

        // 定位
        mLocationClient = new LocationClient(ScheduleManagerActivity.this);
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                dialog.dismiss();
                mLocationClient.stop();
                String latitude = String.valueOf(bdLocation.getLatitude());
                String longitude = String.valueOf(bdLocation.getLongitude());
                String mLocationResult = bdLocation.getAddrStr();

                StringBuilder url = new StringBuilder();
                url.append(MyGlobal.API_ADD_LOCATION);
                url.append("&userId=" + MyGlobal.getUser().getId());
                url.append("&latitude=" + latitude);
                url.append("&longitude=" + longitude);
                try {
                    url.append("&location=" + URLEncoder.encode(mLocationResult, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                }

                if (MyGlobal.checkNetworkConnection(ScheduleManagerActivity.this)) {

                    GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url.toString(),
                            SuccessData.class, new Response.Listener<SuccessData>() {

                        @Override
                        public void onResponse(SuccessData response) {
                            dialog.dismiss();

                            if (response != null && response.getSuccess().equals("true")) {
                                new AlertDialog.Builder(ScheduleManagerActivity.this)
                                        .setMessage(R.string.located_success)
                                        .setTitle(R.string.dialog_hint)
                                        .setPositiveButton(R.string.dialog_confirm, null)
                                        .create()
                                        .show();

                            } else {
                                new AlertDialog.Builder(ScheduleManagerActivity.this)
                                        .setMessage(R.string.interact_data_error)
                                        .setTitle(R.string.dialog_hint)
                                        .setPositiveButton(R.string.dialog_confirm, null)
                                        .create()
                                        .show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();

                            new AlertDialog.Builder(ScheduleManagerActivity.this)
                                    .setMessage(R.string.interact_data_error)
                                    .setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null)
                                    .create()
                                    .show();
                        }
                    }
                    );

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("tab") != null) {
            tab = intent.getStringExtra("tab");
        }

        calendarPickerView = (CalendarPickerView) findViewById(R.id.calendar_view);

        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        buttonChooseDate = (Button) findViewById(R.id.button_choose_date);
        buttonChooseDate.setText(new StringBuilder()
                .append(mYear).append(getText(R.string.cross))
                .append(mMonth + 1).append(getText(R.string.month)));

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

        initCalendar();

        // 考勤
        ((Button) findViewById(R.id.button_add_location)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(ScheduleManagerActivity.this);
                dialog.setMessage(getText(R.string.locating2));
                dialog.setCancelable(true);
                dialog.show();

                // 开启定位
                InitLocation();
                mLocationClient.start();
            }
        });

        // 我的日程
        ((Button) findViewById(R.id.button_my_schedule)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                DialogFragment newFragment = ScheduleDialogFragment.newInstance(calendarPickerView.getSelectedDate(), MyGlobal.getUser().getId(), "");
                newFragment.show(fragmentTransaction, "dialog");
            }

        });

        // 下属日程
        ((Button) findViewById(R.id.button_view_others_schedule)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyGlobal.checkNetworkConnection(ScheduleManagerActivity.this)) {

                    GsonRequest gsonObjRequest = new GsonRequest<SubordinateData>(Request.Method.GET, MyGlobal.API_FETCH_SUBORDINATE + "&userId=" + MyGlobal.getUser().getId(),
                            SubordinateData.class, new Response.Listener<SubordinateData>() {

                        @Override
                        public void onResponse(SubordinateData response) {

                            if (response != null && response.getSuccess().equals("true")) {
                                subordinateData = response;
                                int count = response.getSubordinateInfos().size();
                                if (count == 1) {
                                    subordinatesIndex = 0;
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    DialogFragment newFragment = ScheduleDialogFragment.newInstance(calendarPickerView.getSelectedDate(), subordinateData.getSubordinateInfos().get(subordinatesIndex).getUserId(), "");
                                    newFragment.show(fragmentTransaction, "dialog");

                                } else if (count > 1) {
                                    CharSequence[] list = new CharSequence[response.getSubordinateInfos().size()];
                                    for (int i = 0; i < count; i++) {
                                        list[i] = response.getSubordinateInfos().get(i).getUsername();
                                    }

                                    new AlertDialog.Builder(ScheduleManagerActivity.this)
                                            .setTitle(R.string.dialog_choose_material)
                                            .setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    subordinatesIndex = which;
                                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                                    DialogFragment newFragment = ScheduleDialogFragment.newInstance(calendarPickerView.getSelectedDate(), subordinateData.getSubordinateInfos().get(subordinatesIndex).getUserId(), "");
                                                    newFragment.show(fragmentTransaction, "dialog");
                                                }
                                            })
                                            .setPositiveButton(R.string.cancel, null)
                                            .create()
                                            .show();
                                } else {
                                    new AlertDialog.Builder(ScheduleManagerActivity.this)
                                            .setMessage(R.string.fetch_data_empty)
                                            .setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null)
                                            .create()
                                            .show();
                                }
                            } else {
                                new AlertDialog.Builder(ScheduleManagerActivity.this)
                                        .setMessage(R.string.interact_data_error)
                                        .setTitle(R.string.dialog_hint)
                                        .setPositiveButton(R.string.dialog_confirm, null)
                                        .create()
                                        .show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            new AlertDialog.Builder(ScheduleManagerActivity.this)
                                    .setMessage(R.string.interact_data_error)
                                    .setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null)
                                    .create()
                                    .show();
                        }
                    }
                    );

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }
            }
        });

        // 新增日程
        ((Button) findViewById(R.id.button_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                DialogFragment newFragment = UpdateScheduleDialogFragment.newInstance("add", calendarPickerView.getSelectedDate());
                newFragment.show(fragmentTransaction, "dialog");
            }
        });
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

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        DialogFragment newFragment = ScheduleDialogFragment.newInstance(calendarPickerView.getSelectedDate(), MyGlobal.getUser().getId(), query);
        newFragment.show(fragmentTransaction, "dialog");

        return true;
    }

    // 初始化日历组件
    public void initCalendar() {
        if (MyGlobal.checkNetworkConnection(ScheduleManagerActivity.this)) {

            dialog = new ProgressDialog(ScheduleManagerActivity.this);
            dialog.setMessage(getText(R.string.loading));
            dialog.setCancelable(true);
            dialog.show();

            StringBuilder url = new StringBuilder();
            url.append(MyGlobal.API_FETCH_DAYS);
            url.append("&userId=" + MyGlobal.getUser().getId());
            url.append("&date=" + MyGlobal.SIMPLE_DATE_FORMAT_WITHOUT_TIME.format(new Date(mYear - 1900, mMonth, 1)));

            GsonRequest gsonObjRequest = new GsonRequest<DayData>(Request.Method.GET, url.toString(),
                    DayData.class, new Response.Listener<DayData>() {

                @Override
                public void onResponse(DayData response) {
                    dialog.dismiss();
                    if (response != null && response.getSuccess().equals("true")) {
                        List<DayInfo> dayInfos = response.getDayInfos();
                        ArrayList<Date> selectedDates = new ArrayList<Date>();
                        for (DayInfo day : dayInfos) {
                            selectedDates.add(new Date(mYear - 1900, mMonth, day.getDay()));
                        }

                        calendarPickerView.init(new Date(mYear - 1900, mMonth, mDay))
                                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                                .withSelectedDate(new Date(mYear - 1900, mMonth, mDay))
                                .withHighlightedDates(selectedDates);
                    } else {
                        new AlertDialog.Builder(ScheduleManagerActivity.this)
                                .setMessage(R.string.interact_data_error)
                                .setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null)
                                .create()
                                .show();

                        calendarPickerView.init(new Date(mYear - 1900, mMonth, mDay))
                                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                                .withSelectedDate(new Date());

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    new AlertDialog.Builder(ScheduleManagerActivity.this)
                            .setMessage(R.string.interact_data_error)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();

                    calendarPickerView.init(new Date(mYear - 1900, mMonth, mDay))
                            .inMode(CalendarPickerView.SelectionMode.SINGLE)
                            .withSelectedDate(new Date());
                }
            }
            );

            RequestManager.getRequestQueue().add(gsonObjRequest);
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    public void onStop() {
        mLocationClient.stop();
        super.onDestroy();
    }

    // 初始化定位设定
    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option);
    }

    public static class ScheduleDialogFragment extends DialogFragment {

        private static Date date;
        private static String userId;
        private static String query;
        private ScheduleListAdapter scheduleListAdapter;
        private LayoutInflater layoutInflater;
        private ListView listViewSchedule;
        private ListView listViewLocation;
        private ScheduleData scheduleData;
        private LocationData locationData;
        private ProgressDialog dialog;

        public static ScheduleDialogFragment newInstance(Date date, String userId, String query) {
            ScheduleDialogFragment fragment = new ScheduleDialogFragment();
            ScheduleDialogFragment.date = date;
            ScheduleDialogFragment.userId = userId;
            ScheduleDialogFragment.query = query;
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            layoutInflater = inflater;
            View view = inflater.inflate(R.layout.schedule_manager_info_fragment_dialog, container, false);

            listViewSchedule = ((ListView) view.findViewById(R.id.listview_schedule_list));
            listViewLocation = ((ListView) view.findViewById(R.id.listview_location_list));

            dialog = new ProgressDialog(getActivity());
            loadScheduleData();

            ((Button) view.findViewById(R.id.button_refresh)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadScheduleData();
                }
            });

            ((Button) view.findViewById(R.id.button_back)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScheduleDialogFragment.this.dismiss();
                }
            });

            return view;
        }

        private void loadScheduleData() {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog.setMessage(getText(R.string.loading));
            dialog.setCancelable(true);
            dialog.show();

            if (MyGlobal.checkNetworkConnection(getActivity())) {

                StringBuilder url = new StringBuilder();
                url.append(MyGlobal.API_FETCH_SCHEDULE);
                url.append("&userId=" + userId);
                url.append("&date=" + MyGlobal.SIMPLE_DATE_FORMAT_WITHOUT_TIME.format(date));
                if (query != null && !query.isEmpty()) {
                    try {
                        url.append("&query=" + URLEncoder.encode(query, "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                    }
                }

                GsonRequest gsonObjRequest = new GsonRequest<ScheduleData>(Request.Method.GET, url.toString(),
                        ScheduleData.class, new Response.Listener<ScheduleData>() {

                    @Override
                    public void onResponse(ScheduleData response) {
                        dialog.dismiss();
                        if (response != null && response.getSuccess().equals("true")) {
                            scheduleData = response;
                            locationData = new LocationData();
                            locationData.setLocationInfos(scheduleData.getLocationInfos());
                            scheduleListAdapter = new ScheduleListAdapter(getActivity());
                            listViewSchedule.setAdapter(scheduleListAdapter);
                            listViewSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    scheduleListAdapter.toggle(i);
                                }
                            });
                            listViewSchedule.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    DialogFragment newFragment = UpdateScheduleDialogFragment.newInstance("update", scheduleData.getScheduleInfos().get(i));
                                    newFragment.show(fragmentTransaction, "dialog");
                                    return true;
                                }
                            });
                            listViewLocation.setAdapter(new LocationListAdapter(getActivity()));

                        } else {
                            new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.interact_data_error)
                                    .setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null)
                                    .create()
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        new AlertDialog.Builder(getActivity())
                                .setMessage(R.string.interact_data_error)
                                .setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null)
                                .create()
                                .show();
                    }
                }
                );

                RequestManager.getRequestQueue().add(gsonObjRequest);
            }
        }

        private class ScheduleListAdapter extends BaseAdapter {

            private Context context;

            public ScheduleListAdapter(Context context) {
                this.context = context;
            }

            public int getCount() {
                return scheduleData.getScheduleInfos().size();
            }

            public Object getItem(int position) {
                return position;
            }

            public long getItemId(int position) {
                return position;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                String startTime = scheduleData.getScheduleInfos().get(position).getStartTime();
                String endTime = scheduleData.getScheduleInfos().get(position).getEndTime();
                String content = scheduleData.getScheduleInfos().get(position).getContent();
                Boolean expanded = scheduleData.getScheduleInfos().get(position).getExpanded();

                String title = "";
                if (query != null && !query.isEmpty()) {
                    title = startTime + " - " + endTime;
                    expanded = true;
                } else {
                    title = startTime + " - " + endTime + "   " + (content.length() > 12 ? content.substring(0, 12) + "..." : content);
                }

                ScheduleView sv;
                if (convertView == null) {
                    sv = new ScheduleView(context, title, content, expanded);
                } else {
                    sv = (ScheduleView) convertView;
                    sv.setTitle(title);
                    sv.setDialogue(content);
                    sv.setExpanded(expanded);
                }

                return sv;
            }

            public void toggle(int position) {
                scheduleData.getScheduleInfos().get(position).setExpanded(!scheduleData.getScheduleInfos().get(position).getExpanded());
                notifyDataSetChanged();
            }

        }

        private class ScheduleView extends LinearLayout {

            private TextView title;
            private TextView content;

            public ScheduleView(Context context, String title, String dialogue, boolean expanded) {
                super(context);

                this.setOrientation(VERTICAL);

                this.title = new TextView(context);
                this.title.setText(title);
                this.title.setPadding(0, 15, 0, 0);
                addView(this.title, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

                content = new TextView(context);
                content.setText(dialogue);
                addView(content, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

                content.setVisibility(expanded ? VISIBLE : GONE);
            }

            public void setTitle(String title) {
                this.title.setText(title);
            }

            public void setDialogue(String words) {
                content.setText(words);
            }

            public void setExpanded(boolean expanded) {
                content.setVisibility(expanded ? VISIBLE : GONE);
            }
        }

        private class LocationListAdapter extends BaseAdapter {

            private Context mContext;

            public LocationListAdapter(Context context) {
                mContext = context;
            }

            public int getCount() {
                return locationData.getLocationInfos().size();
            }

            public Object getItem(int position) {
                return position;
            }

            public long getItemId(int position) {
                return position;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                View view = layoutInflater.inflate(R.layout.item_location, null);

                TextView textViewDateTime = (TextView) view.findViewById(R.id.textview_datetime);
                TextView textViewLocation = (TextView) view.findViewById(R.id.textview_location);

                textViewDateTime.setText(locationData.getLocationInfos().get(position).getDatetime());
                textViewLocation.setText(locationData.getLocationInfos().get(position).getLocation());

                return view;
            }
        }
    }

    public static class UpdateScheduleDialogFragment extends DialogFragment {

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

        static UpdateScheduleDialogFragment newInstance(String flag, Date date) {
            UpdateScheduleDialogFragment.flag = flag;
            UpdateScheduleDialogFragment.date = date;
            return new UpdateScheduleDialogFragment();
        }

        static UpdateScheduleDialogFragment newInstance(String flag, ScheduleInfo scheduleInfo) {
            UpdateScheduleDialogFragment.flag = flag;
            UpdateScheduleDialogFragment.date = new Date();
            UpdateScheduleDialogFragment.scheduleInfo = scheduleInfo;
            return new UpdateScheduleDialogFragment();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.schedule_manager_update_fragment_dialog, container, false);

            calendar = Calendar.getInstance();
            calendar.setTime(date);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hour = 12;
            min = 0;

            editTextContent = (EditText) view.findViewById(R.id.edittext_content);
            switchPush = (Switch) view.findViewById(R.id.notification_switch);
            buttonStartDate = (Button) view.findViewById(R.id.button_start_date);
            buttonStartTime = (Button) view.findViewById(R.id.button_start_time);
            buttonEndDate = (Button) view.findViewById(R.id.button_end_date);
            buttonEndTime = (Button) view.findViewById(R.id.button_end_time);

            if (flag.equals("add")) {
                buttonStartDate.setText(MyGlobal.SIMPLE_DATE_FORMAT_WITHOUT_TIME.format(date));
                buttonStartTime.setText("12" + getText(R.string.colon) + "00");
                buttonEndDate.setText(MyGlobal.SIMPLE_DATE_FORMAT_WITHOUT_TIME.format(date));
                buttonEndTime.setText("12" + getText(R.string.colon) + "00");
            } else if (flag.equals("update")) {
                buttonStartDate.setText(scheduleInfo.getStartDate().substring(0, 10));
                buttonStartTime.setText(scheduleInfo.getStartDate().substring(11, 16));
                buttonEndDate.setText(scheduleInfo.getEndDate().substring(0, 10));
                buttonEndTime.setText(scheduleInfo.getEndDate().substring(11, 16));
                editTextContent.setText(scheduleInfo.getContent());
                switchPush.setChecked(scheduleInfo.getPush().equals("yes"));
            }

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

            ((Button) view.findViewById(R.id.button_update)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (startDate.after(endDate)) {
                        new AlertDialog.Builder(getActivity())
                                .setMessage(R.string.hint_enddate_after_startdate)
                                .setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null)
                                .create()
                                .show();

                        return;
                    }

                    if (editTextContent.getText().toString().trim().isEmpty()) {
                        new AlertDialog.Builder(getActivity())
                                .setMessage(R.string.hint_schedule_empty)
                                .setTitle(R.string.dialog_hint)
                                .setPositiveButton(R.string.dialog_confirm, null)
                                .create()
                                .show();

                        return;
                    }

                    if (MyGlobal.checkNetworkConnection(getActivity())) {

                        dialog = new ProgressDialog(getActivity());
                        dialog.setMessage(getText(R.string.submitting));
                        dialog.setCancelable(true);
                        dialog.show();

                        StringBuilder url = new StringBuilder();
                        url.append(MyGlobal.API_UPDATE_SCHEDULE);
                        if (flag.equals("add")) {
                            url.append("&id=");
                        } else if (flag.equals("update")) {
                            url.append("&id=" + scheduleInfo.getId());
                        }
                        url.append("&userId=" + MyGlobal.getUser().getId());
                        try {
                            url.append("&startTime=" + buttonStartDate.getText().toString().trim() + URLEncoder.encode(" ", "utf-8") + buttonStartTime.getText().toString().trim());
                            url.append("&endTime=" + buttonEndDate.getText().toString().trim() + URLEncoder.encode(" ", "utf-8") + buttonEndTime.getText().toString().trim());
                            url.append("&content=" + URLEncoder.encode(editTextContent.getText().toString().trim(), "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                        }
                        url.append("&push=" + (switchPush.isChecked() ? "yes" : "no"));
                        GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url.toString(),
                                SuccessData.class, new Response.Listener<SuccessData>() {

                            @Override
                            public void onResponse(SuccessData response) {
                                dialog.dismiss();
                                if (response != null && response.getSuccess().equals("true")) {

                                    if (flag.equals("add")) {
                                        editTextContent.setText("");
                                        switchPush.setChecked(false);
                                        buttonStartDate.setText(MyGlobal.SIMPLE_DATE_FORMAT_WITHOUT_TIME.format(date));
                                        buttonStartTime.setText("00" + getText(R.string.colon) + "00");
                                        buttonEndDate.setText(MyGlobal.SIMPLE_DATE_FORMAT_WITHOUT_TIME.format(date));
                                        buttonEndTime.setText("00" + getText(R.string.colon) + "00");
                                    } else if (flag.equals("update")) {
                                        UpdateScheduleDialogFragment.this.dismiss();
                                    }

                                    new AlertDialog.Builder(getActivity())
                                            .setMessage(R.string.submitted_success)
                                            .setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null)
                                            .create()
                                            .show();

                                } else {
                                    new AlertDialog.Builder(getActivity())
                                            .setMessage(R.string.interact_data_error)
                                            .setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null)
                                            .create()
                                            .show();

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.dismiss();
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(R.string.interact_data_error)
                                        .setTitle(R.string.dialog_hint)
                                        .setPositiveButton(R.string.dialog_confirm, null)
                                        .create()
                                        .show();

                            }
                        }
                        );

                        RequestManager.getRequestQueue().add(gsonObjRequest);
                    }
                }
            });

            ((Button) view.findViewById(R.id.button_delete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (MyGlobal.checkNetworkConnection(getActivity())) {

                        dialog = new ProgressDialog(getActivity());
                        dialog.setMessage(getText(R.string.submitting));
                        dialog.setCancelable(true);
                        dialog.show();

                        GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, MyGlobal.API_DELETE_SCHEDULE + "&id=" + scheduleInfo.getId() + "&userid=" + MyGlobal.getUser().getId(),
                                SuccessData.class, new Response.Listener<SuccessData>() {

                            @Override
                            public void onResponse(SuccessData response) {
                                dialog.dismiss();
                                if (response != null && response.getSuccess().equals("true")) {
                                    UpdateScheduleDialogFragment.this.dismiss();
                                    new AlertDialog.Builder(getActivity())
                                            .setMessage(R.string.deleted_success)
                                            .setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null)
                                            .create()
                                            .show();

                                } else {
                                    new AlertDialog.Builder(getActivity())
                                            .setMessage(R.string.interact_data_error)
                                            .setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null)
                                            .create()
                                            .show();

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.dismiss();
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(R.string.interact_data_error)
                                        .setTitle(R.string.dialog_hint)
                                        .setPositiveButton(R.string.dialog_confirm, null)
                                        .create()
                                        .show();

                            }
                        }
                        );

                        RequestManager.getRequestQueue().add(gsonObjRequest);
                    }
                }
            });

            if (flag.equals("add")) {
                ((Button) view.findViewById(R.id.button_update)).setVisibility(View.VISIBLE);
                ((Button) view.findViewById(R.id.button_delete)).setVisibility(View.INVISIBLE);
            } else if (flag.equals("update")) {
                ((Button) view.findViewById(R.id.button_update)).setVisibility(View.VISIBLE);
                ((Button) view.findViewById(R.id.button_delete)).setVisibility(View.VISIBLE);
            }

            return view;
        }
    }

}

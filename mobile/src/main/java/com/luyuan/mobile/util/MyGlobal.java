package com.luyuan.mobile.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.luyuan.mobile.R;
import com.luyuan.mobile.function.ScheduleManagerActivity;
import com.luyuan.mobile.function.UploadMaterialActivity;
import com.luyuan.mobile.function.WarehouseVoucherManagerActivity;
import com.luyuan.mobile.model.JobInfo;
import com.luyuan.mobile.model.User;
import com.luyuan.mobile.ui.WebViewActivity;

public class MyGlobal {

    public static final String COLOR_BOTTOM_TAB_SELECTED = "#00CC00";
    public static final String COLOR_BOTTOM_TAB_UNSELECTED = "#000000";

    public static final String SERVER_URL_PREFIX = "http://192.168.10.60:801";

    public static final String API_FETCH_LOGIN = "http://192.168.10.60:801/modules/An.Systems.Web/Ajax/Login.ashx?fn=login4app";
    public static final String API_POST_JOB = "http://192.168.10.60:801/modules/An.Systems.Web/Ajax/Login.ashx?fn=chooserole4app";
    public static final String API_FETCH_FUNCTION = "http://192.168.10.60:801/modules/An.Systems.Web/Ajax/Login.ashx?fn=fetchfunction4app";
    public static final String API_UPLOAD_MATERIAL = "http://pic2.luyuan.cn/upload4app.ashx";
    public static final String API_WAREHOUSE_VOUCHER_SEARCH = "https://erp.luyuan.cn/modules/An.Warehouse.Web/Ajax/ArrivalChkQuery.ashx?fn=getlist";

    public static final String WEBVIEW_URL_PAYROLL = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/PayRoll.aspx";
    public static final String WEBVIEW_URL_BILLBOARD = SERVER_URL_PREFIX + "/modules/An.SaleReport.Web/Billboard.aspx";
    public static final String WEBVIEW_URL_PERSONAL = SERVER_URL_PREFIX + "/modules/An.SaleReport.Web/Personal.aspx";
    public static final String WEBVIEW_URL_STRATEGY = SERVER_URL_PREFIX + "/modules/An.SaleReport.Web/Strategy.aspx";
    public static final String WEBVIEW_URL_TACTICAL = SERVER_URL_PREFIX + "/modules/An.SaleReport.Web/Tactical.aspx";
    public static final String WEBVIEW_URL_TRAIN = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/HRTrain/Train.aspx";

    public static final String CAMERA_PATH = Environment.getExternalStorageDirectory() + "/luyuan/camera/";
    public static final String COMPRESS_PATH = Environment.getExternalStorageDirectory() + "/luyuan/compress/";

    public static final int CONNECTION_TIMEOUT_MS = 1500;

    private static boolean wifiConnected = false;
    private static boolean mobileConnected = false;

    public static boolean checkNetworkConnection(Context context) {
        boolean result = true;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            Dialog alertDialog = new AlertDialog.Builder(context)
                    .setMessage(R.string.network_disconnected)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create();
            alertDialog.show();
            result = false;
        }
        return result;
    }

    public static Class getFunctionActivity(String functionCode) {
        Class clz = null;

        if (functionCode.equals("billboard")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("personal")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("strategy")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("tactical")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("payroll")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("train")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("voucher")) {
            clz = WarehouseVoucherManagerActivity.class;
        } else if (functionCode.equals("upload")) {
            clz = UploadMaterialActivity.class;
        } else if (functionCode.equals("schedule")) {
            clz = ScheduleManagerActivity.class;
        }

        return clz;
    }

    public static int getFunctionIcon(String functionCode) {
        int resId = R.drawable.function_item_sales;

        if (functionCode.equals("billboard")) {
            resId = R.drawable.function_item_sales;
        } else {
            // TODO
        }

        return resId;
    }

    private static User user = new User();
    private static JobInfo jobInfo = new JobInfo();

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MyGlobal.user = user;
    }

    public static JobInfo getJobInfo() {
        return jobInfo;
    }

    public static void setJobInfo(JobInfo jobInfo) {
        MyGlobal.jobInfo = jobInfo;
    }

}

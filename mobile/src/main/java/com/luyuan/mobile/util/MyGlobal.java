package com.luyuan.mobile.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.luyuan.mobile.R;
import com.luyuan.mobile.function.WarehouseVoucherManagerActivity;
import com.luyuan.mobile.model.JobInfo;
import com.luyuan.mobile.model.User;

public class MyGlobal {

    public static final String COLOR_BOTTOM_TAB_SELECTED = "#00CC00";
    public static final String COLOR_BOTTOM_TAB_UNSELECTED = "#000000";

    public static final String PARAM_API_URL = "PARAM_API_URL";


    public static final String API_FETCH_LOGIN = "http://192.168.10.60:8080/modules/An.Systems.Web/Ajax/Login.ashx?fn=login4app";
    public static final String API_FETCH_FUNCTION = "http://192.168.10.60:8080/modules/An.Systems.Web/Ajax/Login.ashx?fn=fetchfunction4app";

    public static final String API_WAREHOUSE_VOUCHER_SEARCH = "https://erp.luyuan.cn/modules/An.Warehouse.Web/Ajax/ArrivalChkQuery.ashx?fn=getlist";

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

        if (functionCode.equals("WarehouseVoucherManager")) {
            clz = WarehouseVoucherManagerActivity.class;
        } else {
            // TODO
        }

        return clz;
    }

    public static int getFunctionIcon(String functionCode) {
        int resId = 0;

        if (functionCode.equals("WarehouseVoucherManager")) {
            resId = R.drawable.ic_tab_function;
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

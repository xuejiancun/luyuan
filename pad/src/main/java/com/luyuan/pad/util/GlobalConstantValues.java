package com.luyuan.pad.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.luyuan.pad.R;

public class GlobalConstantValues {

    public static final String COLOR_TOP_TAB_SELECTED = "#4C7F20";
    public static final String COLOR_TOP_TAB_UNSELECTED = "#99C741";

    public static final String COLOR_BOTTOM_TAB_SELECTED = "#00CC00";
    public static final String COLOR_BOTTOM_TAB_UNSELECTED = "#FFFFFF";

    public static final String PARAM_CAR_TYPE = "PARAM_CAR_TYPE";
    public static final String PARAM_CAR_MODEL = "PARAM_CAR_MODEL";
    public static final String PARAM_WEBVIEW_URL = "PARAM_WEBVIEW_URL";
    public static final String PARAM_API_URL = "PARAM_API_URL";
    public static final String PARAM_IMAGE_INDEX = "PARAM_IMAGE_INDEX";
    public static final String PARAM_IMAGE_URL = "PARAM_IMAGE_URL";

    public static final String TAB_LUXURY_CAR = "TAB_LUXURY_CAR";
    public static final String TAB_SIMPLE_CAR = "TAB_SIMPLE_CAR";
    public static final String TAB_STANDARD_CAR = "TAB_STANDARD_CAR";
    public static final String TAB_BATTERY_CAR = "TAB_BATTERY_CAR";
    public static final String TAB_REPLACEWALK_CAR = "TAB_REPLACEWALK_CAR";
    public static final String TAB_SPECIAL_CAR = "TAB_SPECIAL_CAR";
    public static final String TAB_QUERY_CAR = "TAB_QUERY_CAR";

    public static final String TAB_POPULAR_CAR = "TAB_POPULAR_CAR";
    public static final String TAB_PRODUCT_APPRECIATE = "TAB_PRODUCT_APPRECIATE";
    public static final String TAB_TECH_EMBODIED = "TAB_TECH_EMBODIED";
    public static final String TAB_LUYUAN_CULTURE = "TAB_LUYUAN_CULTURE";

    public static final String INTENT_HOME_TO_MAIN = "com.luyuan.pad.mberp.home2main";

    public static final String URL_ABOUT_LUYUAN = "http://luyuan.cn/aboot_brand.html";
    public static final String URL_BRAND_HISTROY = "http://luyuan.cn/history/index.html";
    public static final String URL_LUYUAN_NEWS = "http://luyuan.cn/lyzs.aspx";

    public static final String API_POPULAR_CAR = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=popularslide";
    public static final String API_TECH_IMAGE = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=techslide";
    public static final String API_TECH_ICON = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=techicon";
    public static final String API_BRAND_HONOR = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=brandhonorslides";

    public static final String API_PRODUCT_THUMB_LUXURY = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=productthumb&typeid=22";
    public static final String API_PRODUCT_THUMB_SIMPLE = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=productthumb&typeid=23";
    public static final String API_PRODUCT_THUMB_STANDARD = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=productthumb&typeid=24";
    public static final String API_PRODUCT_THUMB_BATTERY = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=productthumb&typeid=25";
    public static final String API_PRODUCT_THUMB_REPLACEWALK = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=productthumb&typeid=26";
    public static final String API_PRODUCT_THUMB_SPECIAL = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=productthumb&typeid=27";

    public static final String API_PRODUCT_DETAIL = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=productdetail";
    public static final String API_CAR_APPEARANCE = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=carappearance";
    public static final String API_CAR_DETAIL = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=cardetail";
    public static final String API_CAR_COLOR = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=carcolor";
    public static final String API_CAR_EQUIPMENT = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=carequipment";

    public static final String API_SEARCH_DATA = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=searchdata";
    public static final String API_CHECK_VERSION = "http://www.luyuan.cn/LuyuanAPI/Ajax/action.ashx?fn=getversion";

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

}

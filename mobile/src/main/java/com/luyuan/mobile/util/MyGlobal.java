package com.luyuan.mobile.util;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.luyuan.mobile.R;
import com.luyuan.mobile.function.PointReportActivity;
import com.luyuan.mobile.function.ScheduleManagerActivity;
import com.luyuan.mobile.function.UploadMaterialActivity;
import com.luyuan.mobile.function.UploadMaterialDedicatedActivity;
import com.luyuan.mobile.function.WarrantManageActivity;
import com.luyuan.mobile.model.FunctionData;
import com.luyuan.mobile.model.User;
import com.luyuan.mobile.production.WarehouseAutomaticScanActivity;
import com.luyuan.mobile.production.WarehouseBinExchangectivity;
import com.luyuan.mobile.production.WarehouseBinInventoryCheckActivity;
import com.luyuan.mobile.production.WarehouseBinLackManagerActivity;
import com.luyuan.mobile.production.WarehouseBinManagerActivity;
import com.luyuan.mobile.production.WarehouseBinSearchDetailActivity;
import com.luyuan.mobile.production.WarehouseInventoryManagerActivity;
import com.luyuan.mobile.production.WarehouseLocationInventoryActivity;
import com.luyuan.mobile.production.WarehouseLocationInventoryAddActivity;
import com.luyuan.mobile.production.WarehousePurOrderExamineActivity;
import com.luyuan.mobile.production.WarehouseVoucherCreateManagerActivity;
import com.luyuan.mobile.production.WarehouseVoucherExamineActivity;
import com.luyuan.mobile.production.WarehouseVoucherExamineItemCreateActivity;
import com.luyuan.mobile.production.WarehouseVoucherManagerActivity;
import com.luyuan.mobile.ui.WebViewActivity;

import java.text.SimpleDateFormat;

public class MyGlobal {

    public static final String COLOR_BOTTOM_TAB_SELECTED = "#00CC00";
    public static final String COLOR_BOTTOM_TAB_UNSELECTED = "#000000";
//    public static final String SERVER_URL_PREFIX = "http://192.168.100.230";    // Develop Server
    public static final String SERVER_URL_PREFIX = "http://122.226.37.242:8230/";    // Develop Server
//    public static final String SERVER_URL_PREFIX = "http://192.168.10.60:801";
//    public static final String SERVER_URL_PREFIX = "http://192.168.10.141";
//    public static final String SERVER_URL_PREFIX = "https://erp.luyuan.cn";
    public static final String API_FETCH_LOGIN = SERVER_URL_PREFIX + "/modules/An.Systems.Web/Ajax/Login.ashx?fn=login4app";
    public static final String API_FETCH_ROLE = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=chooserole";
    public static final String API_CHECK_SESSION = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=checksession";
    public static final String API_FETCH_FUNCTION = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=fetchfunctions4app";
    public static final String API_QUERY_MATERIAL = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=querymaterials";
    public static final String API_QUERY_DEDICATED = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=querydedicatedmaterial";
    public static final String API_FETCH_CHANNEL = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=fetchchannels";
    public static final String API_UPLOAD_MATERIAL = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=uploadmaterial";
    public static final String API_SUBMIT_MATERIAL = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=submitmaterial";
    public static final String API_CHECK_DEDICATED = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=checkstoreexists";
    public static final String API_SUBMIT_DEDICATED = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=submitdedicatedmaterial";
    public static final String API_CANCEL_UDF_MATERIAL = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=cancelmaterial";
    public static final String API_CANCEL_DEDICATED_MATERIAL = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=canceldedicatedmaterial";
    public static final String API_FETCH_DAYS = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=fetchdays";
    public static final String API_FETCH_SCHEDULE = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=fetchschedules";
    public static final String API_UPDATE_SCHEDULE = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=addschedule";
    public static final String API_DELETE_SCHEDULE = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=delschedule";
    public static final String API_FETCH_SUBORDINATE = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=fetchsubordinate";
    public static final String API_ADD_LOCATION = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=addlocation";
    public static final String API_CHECK_VERSION = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=fetchversion";
    public static final String API_CHECK_VERSION_NEW = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=fetchversionnew";
    public static final String API_MODIFY_CONTACT = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=modifycontract";
    public static final String API_FETCH_WARRANT_DETAIL = SERVER_URL_PREFIX + "/modules/An.App.Web/Ajax/AppService.ashx?fn=getwarrantdetail";
    public static final String API_FETCH_PROVINCES = SERVER_URL_PREFIX + "/modules/An.App.Web/Ajax/AppService.ashx?fn=fetchprovince";
    public static final String API_FETCH_CITYS = SERVER_URL_PREFIX + "/modules/An.App.Web/Ajax/AppService.ashx?fn=fetchcity";
    public static final String API_FETCH_DEALERS = SERVER_URL_PREFIX + "/modules/An.App.Web/Ajax/AppService.ashx?fn=fetchdealer";
    public static final String API_FETCH_DEALER_ACCOUNT = SERVER_URL_PREFIX + "/modules/An.App.Web/Ajax/AppService.ashx?fn=fetchdealeraccount";
    public static final String API_FETCH_CITY_CHART = SERVER_URL_PREFIX + "/modules/An.App.Web/Ajax/AppService.ashx?fn=fetchcitychart";
    public static final String API_FETCH_DEALER_CITY_CHART = SERVER_URL_PREFIX + "/modules/An.App.Web/Ajax/AppService.ashx?fn=fetchdealercitychart";
    public static final String API_FETCH_CITY_DEALER_BY_USER = SERVER_URL_PREFIX + "/modules/An.App.Web/Ajax/AppService.ashx?fn=fetchdealerbyuser";
    public static final String API_SUBMIT_POINT_REPORT= SERVER_URL_PREFIX + "/modules/An.App.Web/Ajax/AppService.ashx?fn=branchsave";
    public static final String API_SUBMIT_WARRANT = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/AppService.ashx?fn=addwarrant";

    // Added by Fangyi  -- Start
    public static final String API_WHINVENTORY = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/whInventoryBook.ashx?fn=getwhinventory";
    public static final String API_WHINVENTORYDETAIL = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/whInventoryBook.ashx?fn=getwhinventorydetail";
    public static final String API_WAREHOUSE_PURCHASEORDEREXAMINE_QUERY = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/whPurchaseOrderQuery.ashx?fn=getwhpsubmit";
    public static final String API_WHPURCHASEDETAIL = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/whPurchaseOrderQuery.ashx?fn=getwhpdetail";
    public static final String API_WHPUREXAMINE = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/ArrivalChkQuery.ashx?fn=finishexamin";
    public static final String API_QUAPURCREATE = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/whPurchaseOrderQuery.ashx?fn=getquapursave";
    public static final String API_EXAMINEITEM_QUERY = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/whPurchaseOrderQuery.ashx?fn=getexamineitem";
    public static final String API_WAREHOUSE_LOCATIONINVENTORY_QUERY = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/wbProductCheckHandler.ashx?fn=getdetaillist";
    public static final String API_WHLOCATIONINVENTORYDEL = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/wbProductCheckHandler.ashx?fn=ondel2";
    public static final String API_WHGETPRODUCTDETAIL = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/wbProductCheckHandler.ashx?fn=getproductlist";
    public static final String API_WHLOCATIONINVENTORYSAVE = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/wbProductCheckHandler.ashx?fn=onsave";
    public static final String API_WHGETBIN = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/wbProductCheckHandler.ashx?fn=getwhbinlist";
    public static final String API_WAREHOUSE_BINDETAILSEASRCH_QUERY = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/whBinInventoryCheckHandler.ashx?fn=getsearchlist";
    public static final String API_WAREHOUSE_BINLACKDETAILSEASRCH_QUERY = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/whBinInventoryCheckHandler.ashx?fn=getnochecklist";
    public static final String API_WAREHOUSE_BININVENTORYCHECK_DETAIL = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/whBinInventoryCheckHandler.ashx?fn=getlist";
    public static final String API_WAREHOUSE_PURCHASEORDERSUBMIT_QUERY = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/whPurchaseOrderQuery.ashx?fn=getwhpsubmit";
    public static final String API_PURORDER_ADD = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/ArrivalChkQuery.ashx?fn=addnew";
    public static final String API_PURORDER_QUERY = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/whPurchaseOrderQuery.ashx?fn=getordercode";
    public static final String API_WHPURSUBMIT = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/ArrivalChkQuery.ashx?fn=submit";
    public static final String API_WHPURSAVE = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/ArrivalChkQuery.ashx?fn=save";
    public static final String API_WHPCODEITEMLIST_QUERY = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/whPurchaseOrderQuery.ashx?fn=getlist";
    public static final String API_UNITNAME_QUERY = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/whPurchaseOrderQuery.ashx?fn=getunitname";
    public static final String API_WAREHOUSE_PURCHASEORDER_QUERY = SERVER_URL_PREFIX + "/modules/An.APP.Web/Ajax/whPurchaseOrderQuery.ashx?fn=purchaseorderquery";
    public static final String API_WAREHOUSE_AUTOMATIC_SCAN = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/ProductApprovedHandler.ashx?fn=updateproductbarcode2gson";
    public static final String API_WAREHOUSE_BININFO = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/whBinInventoryCheckHandler.ashx?fn=actualqtychange4lwp";
    public static final String API_WAREHOUSE_BINSAVE = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/whBinInventoryCheckHandler.ashx?fn=savedata4lwp2gson";
    public static final String API_WAREHOUSE_BININFOMODIFY = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/wbProductCheckHandler.ashx?fn=getdetaillist";
    public static final String API_WAREHOUSE_BINEXCHANGE_QUERY = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/whBinAdjustHandler.ashx?fn=onscancheck";
    public static final String API_WAREHOUSE_BINEXCHANGE_SAVE = SERVER_URL_PREFIX + "/modules/An.Warehouse.Web/Ajax/whBinAdjustHandler.ashx?fn=onsave";
    // Added by Fangyi  -- End

    public static final String WEBVIEW_URL_QUERY_AUTH = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/QueryAuth.aspx";
    public static final String WEBVIEW_URL_BILLBOARD = SERVER_URL_PREFIX + "/modules/An.SaleReport.Web/Billboard.aspx";
    public static final String WEBVIEW_URL_BILLBOARD_MAN = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/SalesReport/Billboard.html";
    public static final String WEBVIEW_URL_MY_PERFORMANCE = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/SalesReport/MyPerformanceComplete.html";
    public static final String WEBVIEW_URL_DEALER_PERFORMANCE = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/SalesReport/DealerPerformanceMonth.html";
    public static final String WEBVIEW_URL_PERSONAL = SERVER_URL_PREFIX + "/modules/An.SaleReport.Web/Personal.aspx";
    public static final String WEBVIEW_URL_STRATEGY = SERVER_URL_PREFIX + "/modules/An.SaleReport.Web/Strategy.aspx";
    public static final String WEBVIEW_URL_TACTICAL = SERVER_URL_PREFIX + "/modules/An.SaleReport.Web/Tactical.aspx";
    public static final String WEBVIEW_URL_TRAIN = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/HRTrain/Train.html";
    public static final String WEBVIEW_URL_MANUAL = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/HRTrain/TrainManual.html";
    public static final String WEBVIEW_URL_MARKET_STRATEGY = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/Strategy/Index.html";
    public static final String WEBVIEW_URL_MARKET_RESEARCH = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/Research/Index.html";
    public static final String WEBVIEW_URL_RESEARCH_VERIFY = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/ResearchMag/verify.html";
    public static final String WEBVIEW_URL_VERIFICATION_DETAIL = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/WarrantManage/verificationDetail.html";
    public static final String WEBVIEW_URL_MARKET_PAPER_1 = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/WhitePaper/Paper_1.html";
    public static final String WEBVIEW_URL_MARKET_PAPER_2 = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/WhitePaper/Paper_2.html";
    public static final String WEBVIEW_URL_MARKET_PAPER_3 = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/WhitePaper/Paper_3.html";
    public static final String WEBVIEW_URL_QUERY_WARRANT = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/WarrantManage/warrantDetail.html";
    public static final String WEBVIEW_URL_FOLLOWING_SOMEONE = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/Account/attention.html";
    public static final String WEBVIEW_URL_CHANGE_PASSWORD = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/Account/modifyPwd.html";
    public static final String WEBVIEW_URL_NOTIFICATION_HISTORY = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/Account/noticeHistory.html";
    public static final String WEBVIEW_URL_QUERY_PAYROLL = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/PayRoll.aspx";
    public static final String WEBVIEW_URL_EXPRESS = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/EnemyExpress/EnemyExpress.html";
    public static final String WEBVIEW_URL_PRODUCT = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/NewProduct/NewProduct.html";
    public static final String WEBVIEW_URL_NOTIFICATION = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/ImportantNotification/ImportantNotification.html";
    public static final String WEBVIEW_URL_SCORE = SERVER_URL_PREFIX + "/modules/An.APP.Web/view/Credit/list.html";
    public static final String CAMERA_PATH = Environment.getExternalStorageDirectory() + "/luyuan/camera/";
    public static final String COMPRESS_PATH = Environment.getExternalStorageDirectory() + "/luyuan/compress/";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_WITH_TIME = new SimpleDateFormat("yyyyMMdd_HHmmss");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_WITHOUT_TIME = new SimpleDateFormat("yyyy-MM-dd");

    private static User user = new User();
    private static FunctionData functionData = new FunctionData();

    public static boolean checkNetworkConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected() && activeInfo.isAvailable()) {
            return true;
        } else {
            new AlertDialog.Builder(context)
                    .setMessage(R.string.network_disconnected)
                    .setTitle(R.string.dialog_hint)
                    .setPositiveButton(R.string.dialog_confirm, null)
                    .create()
                    .show();
            return false;
        }
    }

    public static Class getFunctionActivity(String functionCode) {
        Class clz = null;

        if (functionCode.equals("report_billboard")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("report_billboard_man")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("report_by_performance")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("report_dealer_performance")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("report_personal")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("report_strategy")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("report_tactical")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("query_payroll")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("query_training")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("query_manual")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("query_express")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("query_product")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("query_notification")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("query_score")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("query_authorization")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("market_research")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("market_strategy")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("research_verify")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("verification_detail")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("market_paper_1")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("market_paper_2")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("market_paper_3")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("material_upload")) {
            clz = UploadMaterialActivity.class;
        } else if (functionCode.equals("material_upload_dedicated")) {
            clz = UploadMaterialDedicatedActivity.class;
        } else if (functionCode.equals("schedule_manage")) {
            clz = ScheduleManagerActivity.class;
        } else if (functionCode.equals("query_warrant")) {
            clz = WebViewActivity.class;
        } else if (functionCode.equals("warrant_manage")) {
            clz = WarrantManageActivity.class;
        } else if (functionCode.equals("warehouse_purvoucher_search")) {
            clz = WarehouseVoucherManagerActivity.class;
        } else if (functionCode.equals("warehouse_purvoucher_create")) {
            clz = WarehouseVoucherCreateManagerActivity.class;
        } else if (functionCode.equals("warehouse_inventorybook_search")) {
            clz = WarehouseInventoryManagerActivity.class;
        } else if (functionCode.equals("warehouse_voucher_examine_search")) {
            clz = WarehouseVoucherExamineActivity.class;
        } else if (functionCode.equals("warehouse_voucher_examineitem_create")) {
            clz = WarehouseVoucherExamineItemCreateActivity.class;
        } else if (functionCode.equals("warehouse_locationinventory_search")) {
            clz = WarehouseLocationInventoryActivity.class;
        } else if (functionCode.equals("warehouse_locationinventory_add")) {
            clz = WarehouseLocationInventoryAddActivity.class;
        } else if (functionCode.equals("warehouse_whbininfo_search")) {
            clz = WarehouseBinManagerActivity.class;
        } else if (functionCode.equals("warehouse_whbinlackinfo_search")) {
            clz = WarehouseBinLackManagerActivity.class;
        } else if (functionCode.equals("warehouse_automaticscan")) {
            clz = WarehouseAutomaticScanActivity.class;
        } else if (functionCode.equals("warehouse_bininventorycheck")) {
            clz = WarehouseBinInventoryCheckActivity.class;
        } else if (functionCode.equals("warehouse_puroederexamine_search")) {
            clz = WarehousePurOrderExamineActivity.class;
        } else if (functionCode.equals("warehouse_whbininfo_search")) {
            clz = WarehouseBinSearchDetailActivity.class;
        } else if (functionCode.equals("warehouse_whbinexchange")) {
            clz = WarehouseBinExchangectivity.class;
        } else if (functionCode.equals("point_report")) {
            clz = PointReportActivity.class;
        }
        return clz;
    }

    public static int getFunctionIcon(String functionCode) {
        int resId = R.drawable.function_item_sales;

        if (functionCode.equals("report_billboard")) {
            resId = R.drawable.icon_report_billboard;
        } else if (functionCode.equals("report_billboard_man")) {
            resId = R.drawable.icon_report_billboard;
        } else if (functionCode.equals("report_by_performance")) {
            resId = R.drawable.function_item_sales;
        } else if (functionCode.equals("report_dealer_performance")) {
            resId = R.drawable.function_item_sales;
        } else if (functionCode.equals("report_personal")) {
            resId = R.drawable.icon_report_personal;
        } else if (functionCode.equals("report_strategy")) {
            resId = R.drawable.icon_report_strategy;
        } else if (functionCode.equals("report_tactical")) {
            resId = R.drawable.icon_report_tactical;
        } else if (functionCode.equals("query_payroll")) {
            resId = R.drawable.icon_query_payroll;
        } else if (functionCode.equals("query_training")) {
            resId = R.drawable.icon_query_training;
        } else if (functionCode.equals("query_manual")) {
            resId = R.drawable.icon_query_manual;
        } else if (functionCode.equals("query_express")) {
            resId = R.drawable.icon_query_express;
        } else if (functionCode.equals("query_product")) {
            resId = R.drawable.icon_query_product;
        } else if (functionCode.equals("query_notification")) {
            resId = R.drawable.icon_query_notification;
        } else if (functionCode.equals("query_score")) {
            resId = R.drawable.icon_query_score;
        } else if (functionCode.equals("query_authorization")) {
            resId = R.drawable.icon_query_authorization;
        } else if (functionCode.equals("market_research")) {
            resId = R.drawable.icon_market_research;
        } else if (functionCode.equals("market_strategy")) {
            resId = R.drawable.icon_market_strategy;
        } else if (functionCode.equals("research_verify")) {
            resId = R.drawable.icon_research_verify;
        } else if (functionCode.equals("verification_detail")) {
            resId = R.drawable.icon_verification_detail;
        } else if (functionCode.equals("market_paper_1")) {
            resId = R.drawable.icon_market_paper_1;
        } else if (functionCode.equals("market_paper_2")) {
            resId = R.drawable.icon_market_paper_2;
        } else if (functionCode.equals("market_paper_3")) {
            resId = R.drawable.icon_market_paper_3;
        } else if (functionCode.equals("material_upload")) {
            resId = R.drawable.icon_material_upload;
        } else if (functionCode.equals("material_upload_dedicated")) {
            resId = R.drawable.icon_material_upload_dedicated;
        } else if (functionCode.equals("schedule_manage")) {
            resId = R.drawable.icon_schedule_manage;
        } else if (functionCode.equals("query_warrant")) {
            resId = R.drawable.icon_query_warrant;
        } else if (functionCode.equals("point_report")) {
            resId = R.drawable.function_item_sales;
        }

        return resId;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MyGlobal.user = user;
    }

    public static FunctionData getFunctionData() {
//        FunctionInfo functionInfo = new FunctionInfo();
//        functionInfo.setName("网点报备");
//        functionInfo.setCode("point_report");
//        ArrayList<FunctionInfo> functionInfos =  new ArrayList<FunctionInfo>();
//        functionInfos.add(functionInfo);
//        functionData.setFunctionInfos(functionInfos);
        return functionData;
    }

    public static void setFunctionData(FunctionData functionData) {
        MyGlobal.functionData = functionData;
    }

    public static boolean isBackground = false;
}

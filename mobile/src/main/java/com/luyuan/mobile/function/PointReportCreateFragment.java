package com.luyuan.mobile.function;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.CityChart;
import com.luyuan.mobile.model.CityChartData;
import com.luyuan.mobile.model.DealerNewData;
import com.luyuan.mobile.ui.ImagePreviewActivity;
import com.luyuan.mobile.util.FileUtilities;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.HttpMultipartPost;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PointReportCreateFragment extends Fragment {
    private final ArrayList<String> filePaths = new ArrayList<String>();
    private final ArrayList<Bitmap> fileThumbs = new ArrayList<Bitmap>();
    private final int FROM_CAMERA = 1;
    private final int FROM_PHOTO = 2;
    private final int FROM_VIDEO = 3;
    private final int FROM_AUDIO = 4;
    private HttpMultipartPost post;
    private List<BasicNameValuePair> pairs;

    private EditText editSalesAddress;
    private EditText editShopAddress;
    private EditText editPointName;
    private EditText editContractNum;
    private EditText editManName;
    private EditText editManPhone;

    private LayoutInflater layoutInflater;
    private Spinner spinnerProvinceSales;
    private Spinner spinnerCitySales;
    private Spinner spinnerProvinceShop;
    private Spinner spinnerCityShop;
    private Spinner spinnerCountyShop;
    private Spinner spinnerTownShop;
    private Spinner spinnerDealer;
    private Gallery gallery;

    private Uri mCapturedImageURI;
    private CityChartData provinceSalesData;
    private CityChartData provinceShopData;
    private CityChartData citySalesData;
    private CityChartData cityShopData;
    private CityChartData countyData;
    private CityChartData townData;
    private DealerNewData dealerData;

    private int provinceSalesIndex = 0;
    private int citySalesIndex = 0;
    private int provinceShopIndex = 0;
    private int cityShopIndex = 0;
    private int countyIndex = 0;
    private int townIndex = 0;
    private int dealerIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCapturedImageURI = savedInstanceState.getParcelable("imageURI");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("imageURI", mCapturedImageURI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        View view = inflater.inflate(R.layout.point_report_create_fragment, null);
        editSalesAddress = (EditText) view.findViewById(R.id.edit_sales_address);
        editShopAddress = (EditText) view.findViewById(R.id.edit_shop_address);
        editPointName = (EditText) view.findViewById(R.id.edit_point_name);
        editContractNum = (EditText) view.findViewById(R.id.edit_contract_num);
        editManName = (EditText) view.findViewById(R.id.edit_man_name);
        editManPhone = (EditText) view.findViewById(R.id.edit_man_phone);

        spinnerProvinceSales = (Spinner) view.findViewById(R.id.spin_province_sales);
        spinnerCitySales = (Spinner) view.findViewById(R.id.spin_city_sales);
        spinnerProvinceShop = (Spinner) view.findViewById(R.id.spin_province_shop);
        spinnerCityShop = (Spinner) view.findViewById(R.id.spin_city_shop);
        spinnerCountyShop = (Spinner) view.findViewById(R.id.spin_county_shop);
        spinnerTownShop = (Spinner) view.findViewById(R.id.spin_town_shop);
        spinnerDealer = (Spinner) view.findViewById(R.id.spin_parent_dealer);

        if (MyGlobal.checkNetworkConnection(getActivity())) {

            GsonRequest gsonObjRequest = new GsonRequest<DealerNewData>(Request.Method.GET, MyGlobal.API_FETCH_CITY_DEALER_BY_USER, DealerNewData.class,

                    new Response.Listener<DealerNewData>() {
                        @Override
                        public void onResponse(DealerNewData response) {
                            if (response != null && response.getSuccess().equals("true")) {
                                dealerData = response;
                                String[] data = new String[dealerData.getDealers().size()];
                                for(int i = 0; i< dealerData.getDealers().size(); i++){
                                    data[i] = dealerData.getDealers().get(i).getDealerName();
                                }
                                spinnerDealer.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data));

                                GsonRequest gsonObjRequest = new GsonRequest<CityChartData>(Request.Method.GET, MyGlobal.API_FETCH_DEALER_CITY_CHART + "&pid=10" , CityChartData.class,

                                        new Response.Listener<CityChartData>() {
                                            @Override
                                            public void onResponse(CityChartData response) {
                                                if (response != null && response.getSuccess().equals("true")) {
                                                    provinceSalesData = response;
                                                    String[] data = new String[provinceSalesData.getCityCharts().size()];
                                                    for(int i = 0; i< provinceSalesData.getCityCharts().size(); i++){
                                                        data[i] = provinceSalesData.getCityCharts().get(i).getName();
                                                    }
                                                    spinnerProvinceSales.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data));

                                                    GsonRequest gsonObjRequest = new GsonRequest<CityChartData>(Request.Method.GET, MyGlobal.API_FETCH_CITY_CHART + "&pid=10" , CityChartData.class,

                                                            new Response.Listener<CityChartData>() {
                                                                @Override
                                                                public void onResponse(CityChartData response) {
                                                                    if (response != null && response.getSuccess().equals("true")) {
                                                                        provinceShopData = response;
                                                                        String[] data = new String[provinceShopData.getCityCharts().size()];
                                                                        for(int i = 0; i< provinceShopData.getCityCharts().size(); i++){
                                                                            data[i] = provinceShopData.getCityCharts().get(i).getName();
                                                                        }
                                                                        spinnerProvinceShop.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data));
                                                                    }
                                                                }
                                                            },
                                                            new Response.ErrorListener() {
                                                                @Override
                                                                public void onErrorResponse(VolleyError error) {
                                                                    new AlertDialog.Builder(getActivity())
                                                                            .setMessage(R.string.interact_data_error)
                                                                            .setTitle(R.string.dialog_hint)
                                                                            .setPositiveButton(R.string.dialog_confirm, null)
                                                                            .create()
                                                                            .show();
                                                                }
                                                            });

                                                    RequestManager.getRequestQueue().add(gsonObjRequest);

                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                new AlertDialog.Builder(getActivity())
                                                        .setMessage(R.string.interact_data_error)
                                                        .setTitle(R.string.dialog_hint)
                                                        .setPositiveButton(R.string.dialog_confirm, null)
                                                        .create()
                                                        .show();
                                            }
                                        });

                                RequestManager.getRequestQueue().add(gsonObjRequest);

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.interact_data_error)
                                    .setTitle(R.string.dialog_hint)
                                    .setPositiveButton(R.string.dialog_confirm, null)
                                    .create()
                                    .show();
                        }
                    });

            RequestManager.getRequestQueue().add(gsonObjRequest);

        }

        ((Spinner) view.findViewById(R.id.spin_province_sales)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provinceSalesIndex = position;
                citySalesIndex = 0;

                if (MyGlobal.checkNetworkConnection(getActivity())) {

                    GsonRequest gsonObjRequest = new GsonRequest<CityChartData>(Request.Method.GET, MyGlobal.API_FETCH_DEALER_CITY_CHART + "&pid=" + provinceSalesData.getCityCharts().get(provinceSalesIndex).getId(), CityChartData.class,

                            new Response.Listener<CityChartData>() {
                                @Override
                                public void onResponse(CityChartData response) {

                                    if (response != null && response.getSuccess().equals("true")) {
                                        citySalesData = response;
                                        CityChart cityChart = new CityChart();
                                        citySalesData.getCityCharts().add(cityChart);
                                        String[] data = new String[citySalesData.getCityCharts().size()];
                                        for(int i = 0; i< citySalesData.getCityCharts().size(); i++){
                                            data[i] = citySalesData.getCityCharts().get(i).getName();
                                        }
                                        spinnerCitySales.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data));
                                        provinceShopIndex = getCityChartIndex(provinceShopData, provinceSalesData.getCityCharts().get(provinceSalesIndex).getName());
                                        spinnerProvinceShop.setSelection(provinceShopIndex, true);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    new AlertDialog.Builder(getActivity())
                                            .setMessage(R.string.interact_data_error)
                                            .setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null)
                                            .create()
                                            .show();
                                }
                            });

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Spinner) view.findViewById(R.id.spin_city_sales)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                citySalesIndex = position;
                if(cityShopData != null && !citySalesData.getCityCharts().get(citySalesIndex).getName().isEmpty()
                        && provinceSalesData.getCityCharts().get(provinceSalesIndex).getName().equals(provinceShopData.getCityCharts().get(provinceShopIndex).getName())){
                    cityShopIndex = getCityChartIndex(cityShopData, citySalesData.getCityCharts().get(citySalesIndex).getName());
                    spinnerCityShop.setSelection(cityShopIndex, true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Spinner) view.findViewById(R.id.spin_province_shop)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provinceShopIndex = position;

                if (MyGlobal.checkNetworkConnection(getActivity())) {
                    GsonRequest gsonObjRequest = new GsonRequest<CityChartData>(Request.Method.GET, MyGlobal.API_FETCH_CITY_CHART + "&pid=" + provinceShopData.getCityCharts().get(provinceShopIndex).getId(), CityChartData.class,

                            new Response.Listener<CityChartData>() {
                                @Override
                                public void onResponse(CityChartData response) {

                                    if (response != null && response.getSuccess().equals("true")) {
                                        cityShopData = response;
                                        String[] data = new String[cityShopData.getCityCharts().size()];
                                        for(int i = 0; i< cityShopData.getCityCharts().size(); i++){
                                            data[i] = cityShopData.getCityCharts().get(i).getName();
                                        }
                                        spinnerCityShop.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data));
                                        cityShopIndex = (citySalesData == null ? 0 : getCityChartIndex(cityShopData, citySalesData.getCityCharts().get(citySalesIndex).getName()));
                                        spinnerCityShop.setSelection(cityShopIndex, true);

                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    new AlertDialog.Builder(getActivity())
                                            .setMessage(R.string.interact_data_error)
                                            .setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null)
                                            .create()
                                            .show();
                                }
                            });

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Spinner) view.findViewById(R.id.spin_city_shop)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityShopIndex = position;
                countyIndex =0;

                if (MyGlobal.checkNetworkConnection(getActivity())) {
                    GsonRequest gsonObjRequest = new GsonRequest<CityChartData>(Request.Method.GET, MyGlobal.API_FETCH_CITY_CHART + "&pid=" + cityShopData.getCityCharts().get(cityShopIndex).getId(), CityChartData.class,

                            new Response.Listener<CityChartData>() {
                                @Override
                                public void onResponse(CityChartData response) {

                                    if (response != null && response.getSuccess().equals("true")) {
                                        countyData = response;
                                        String[] data = new String[countyData.getCityCharts().size()];
                                        for(int i = 0; i< countyData.getCityCharts().size(); i++){
                                            data[i] = countyData.getCityCharts().get(i).getName();
                                        }
                                        spinnerCountyShop.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data));
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    new AlertDialog.Builder(getActivity())
                                            .setMessage(R.string.interact_data_error)
                                            .setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null)
                                            .create()
                                            .show();
                                }
                            });

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Spinner) view.findViewById(R.id.spin_county_shop)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countyIndex = position;
                townIndex =0;

                if (MyGlobal.checkNetworkConnection(getActivity())) {
                    GsonRequest gsonObjRequest = new GsonRequest<CityChartData>(Request.Method.GET, MyGlobal.API_FETCH_CITY_CHART + "&pid=" + countyData.getCityCharts().get(countyIndex).getId(), CityChartData.class,

                            new Response.Listener<CityChartData>() {
                                @Override
                                public void onResponse(CityChartData response) {

                                    if (response != null && response.getSuccess().equals("true")) {
                                        townData = response;
                                        CityChart cityChart = new CityChart();
                                        townData.getCityCharts().add(cityChart);
                                        String[] data = new String[townData.getCityCharts().size()];
                                        for(int i = 0; i< townData.getCityCharts().size(); i++){
                                            data[i] = townData.getCityCharts().get(i).getName();
                                        }
                                        spinnerTownShop.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data));
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    new AlertDialog.Builder(getActivity())
                                            .setMessage(R.string.interact_data_error)
                                            .setTitle(R.string.dialog_hint)
                                            .setPositiveButton(R.string.dialog_confirm, null)
                                            .create()
                                            .show();
                                }
                            });

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Spinner) view.findViewById(R.id.spin_town_shop)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                townIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 添加附件
        ((Button) view.findViewById(R.id.button_attach_file)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = new CharSequence[]{getText(R.string.from_camera), getText(R.string.from_photo)
                        , getText(R.string.from_video), getText(R.string.from_audio)};

                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.choose_file)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    String name = "IMG_" + MyGlobal.SIMPLE_DATE_FORMAT_WITH_TIME.format(new Date()) + ".jpg";
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (hasImageCaptureBug()) {
                                        File file = new File(MyGlobal.CAMERA_PATH + name);
                                        try {
                                            if (!file.exists()) {
                                                file.getParentFile().mkdirs();
                                                file.createNewFile();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        mCapturedImageURI = Uri.fromFile(file);
                                    } else {
                                        ContentValues values = new ContentValues();
                                        values.put(MediaStore.Images.Media.TITLE, name);
                                        mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                    }

                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                                    startActivityForResult(intent, FROM_CAMERA);

                                } else if (which == 1) {
                                    if (Build.VERSION.SDK_INT < 19) {
                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent.setType("image/*");
                                        startActivityForResult(Intent.createChooser(intent, "Select image"), FROM_PHOTO);
                                    } else {
                                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(intent, FROM_PHOTO);
                                    }

                                } else if (which == 2) {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("video/*");
                                    startActivityForResult(Intent.createChooser(intent, "Select video"), FROM_VIDEO);
                                } else if (which == 3) {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("audio/*");
                                    startActivityForResult(Intent.createChooser(intent, "Select audio"), FROM_AUDIO);
                                }
                            }
                        })
                        .create()
                        .show();
            }
        });

        ((Button) view.findViewById(R.id.button_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String provinceSalesId = provinceSalesData.getCityCharts().get(provinceSalesIndex).getId();
                String citySalesId = citySalesData.getCityCharts().get(citySalesIndex).getId();
                String areaSales = provinceSalesId + "-" + (citySalesId.isEmpty() ? "0" : citySalesId) + "-0-0-0" ;
                String addressSales = editSalesAddress.getText().toString();

                String provinceShopId = provinceShopData.getCityCharts().get(provinceShopIndex).getId();
                String cityShopId = cityShopData.getCityCharts().get(cityShopIndex).getId();
                String countyId = countyData.getCityCharts().get(countyIndex).getId();
                String townId = countyData.getCityCharts().get(townIndex).getId();
                String areaShop = provinceShopId + "-" + cityShopId + "-" + countyId + "-" + (townId.isEmpty() ? "0" : townId) + "-0" ;
                String addressShop = editShopAddress.getText().toString();
                String dealerId = dealerData.getDealers().get(dealerIndex).getDealerID();
                String pointName = editPointName.getText().toString();
                String contractNum = editContractNum.getText().toString();
                String manName = editManName.getText().toString();
                String manPhone = editManPhone.getText().toString();

                if (addressShop.isEmpty()) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.hint_address_shop_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }
                if (pointName.isEmpty()) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.hint_point_name_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }
                if (contractNum.isEmpty()) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.hint_contract_num_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }
                if (manName.isEmpty()) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.hint_man_name_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }
                if (manPhone.isEmpty()) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.hint_man_phone_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }

                pairs = new ArrayList<BasicNameValuePair>();
                pairs.add(new BasicNameValuePair("dealer_cityChartID", areaSales));
                pairs.add(new BasicNameValuePair("dealer_address", addressSales));
                pairs.add(new BasicNameValuePair("shop_cityChartID", areaShop));
                pairs.add(new BasicNameValuePair("shop_address", addressShop));
                pairs.add(new BasicNameValuePair("distributor", dealerId));
                pairs.add(new BasicNameValuePair("branch_name", pointName));
                pairs.add(new BasicNameValuePair("sig_qty", contractNum));
                pairs.add(new BasicNameValuePair("chargeperson_name", manName));
                pairs.add(new BasicNameValuePair("chargeperson_tel", manPhone));

                post = new HttpMultipartPost(getActivity(), MyGlobal.API_SUBMIT_POINT_REPORT, pairs, filePaths, getText(R.string.submitting).toString());
                post.execute();

            }
        });

        gallery = (Gallery) view.findViewById(R.id.gallery_attach_file);
        gallery.setAdapter(new ImageAdapter(getActivity()));

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String filePath = filePaths.get(position).toString();
                String fileType = FileUtilities.getFileType(filePath);
                if (fileType.equals("jpg") || fileType.equals("png") || fileType.equals("bmp") || fileType.equals("gif")) {
                    Intent intent = new Intent(getActivity(), ImagePreviewActivity.class);
                    intent.putExtra("filePath", filePaths.get(position).toString());
                    startActivity(intent);
                }
            }
        });

        registerForContextMenu(gallery);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(R.string.delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        fileThumbs.remove(info.position);
        filePaths.remove(info.position);
        gallery.setAdapter(new ImageAdapter(getActivity()));

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_OK) {
            return;
        }
        Uri uri = null;
        String filePath = "";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = null;

        switch (requestCode) {
            case FROM_CAMERA:
                if (mCapturedImageURI == null) {
                    return;
                }
                if (hasImageCaptureBug()) {
                    filePath = mCapturedImageURI.getPath();
                    bitmap = BitmapFactory.decodeFile(filePath, options);
                } else {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().managedQuery(mCapturedImageURI, projection, null, null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    filePath = cursor.getString(column_index_data);
                    bitmap = BitmapFactory.decodeFile(filePath, options);
                }
                break;

            case FROM_PHOTO:
                if (data == null) {
                    return;
                }
                uri = data.getData();

                try {
                    filePath = FileUtilities.getPath(getActivity(), uri);
                } catch (URISyntaxException e) {
                }
                bitmap = BitmapFactory.decodeFile(filePath, options);
                break;

            case FROM_VIDEO:
                uri = data.getData();
                if (uri == null) {
                    return;
                }
                try {
                    filePath = FileUtilities.getPath(getActivity(), uri);
                } catch (URISyntaxException e) {
                }
                bitmap = ((BitmapDrawable) getActivity().getResources().getDrawable(R.drawable.upload_video)).getBitmap();

                break;

            case FROM_AUDIO:
                uri = data.getData();
                if (uri == null) {
                    return;
                }
                try {
                    filePath = FileUtilities.getPath(getActivity(), uri);
                } catch (URISyntaxException e) {
                }
                bitmap = ((BitmapDrawable) getActivity().getResources().getDrawable(R.drawable.upload_audio)).getBitmap();

                break;
        }
        if (!filePath.isEmpty() && !filePaths.contains(filePath)) {
            filePaths.add(0, filePath);
            fileThumbs.add(0, bitmap);
        }
        gallery.setAdapter(new ImageAdapter(getActivity()));
    }

    @Override
    public void onStop() {
        super.onDestroy();
    }

    public boolean hasImageCaptureBug() {
        // list of known devices that have the bug
        ArrayList<String> devices = new ArrayList<String>();
        devices.add("android-devphone1/dream_devphone/dream");
        devices.add("generic/sdk/generic");
        devices.add("vodafone/vfpioneer/sapphire");
        devices.add("tmobile/kila/dream");
        devices.add("verizon/voles/sholes");
        devices.add("google_ion/google_ion/sapphire");

        return devices.contains(Build.BRAND + "/" + Build.PRODUCT + "/" + Build.DEVICE);
    }

    public class ImageAdapter extends BaseAdapter {
        private static final int ITEM_WIDTH = 100;
        private static final int ITEM_HEIGHT = 100;

        private final int mGalleryItemBackground;
        private final Context mContext;

        private final float mDensity;

        public ImageAdapter(Context c) {
            mContext = c;
            TypedArray a = getActivity().obtainStyledAttributes(R.styleable.Gallery1);
            mGalleryItemBackground = a.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 0);
            a.recycle();

            mDensity = c.getResources().getDisplayMetrics().density;
        }

        public int getCount() {
            return fileThumbs.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                convertView = new ImageView(mContext);

                imageView = (ImageView) convertView;
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new Gallery.LayoutParams(
                        (int) (ITEM_WIDTH * mDensity + 0.5f),
                        (int) (ITEM_HEIGHT * mDensity + 0.5f)));

                imageView.setBackgroundResource(mGalleryItemBackground);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageBitmap(fileThumbs.get(position));

            return imageView;
        }
    }

    public int getCityChartIndex(CityChartData data, String selectedItemName){
        for(int i = 0; i < data.getCityCharts().size(); i++){
            if(data.getCityCharts().get(i).getName().equals(selectedItemName)){
                return i;
            }
        }
        return 0;
    }

}
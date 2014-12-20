package com.luyuan.mobile.function;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.CityData;
import com.luyuan.mobile.model.DealerAccount;
import com.luyuan.mobile.model.DealerAccountData;
import com.luyuan.mobile.model.DealerData;
import com.luyuan.mobile.model.ProvinceData;
import com.luyuan.mobile.model.WarrantData;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WarrantNewFragment extends Fragment {
    private final ArrayList<String> filePaths = new ArrayList<String>();
    private final ArrayList<Bitmap> fileThumbs = new ArrayList<Bitmap>();
    private final int FROM_CAMERA = 1;
    private final int FROM_PHOTO = 2;
    private final int FROM_VIDEO = 3;
    private final int FROM_AUDIO = 4;
    private HttpMultipartPost post;
    private List<BasicNameValuePair> pairs;

    private EditText editWarrantMax;
    private EditText editWarrantUsed;
    private EditText editWarrantAvailable;
    private EditText editWarrantAmount;

    private LayoutInflater layoutInflater;
    private ProgressDialog dialog;
    private Spinner spinnerProvince;
    private Spinner spinnerCity;
    private Spinner spinnerDealer;
    private ListView listDealerAccount;
    private Gallery gallery;

    private Uri mCapturedImageURI;
    private WarrantData warrantData;
    private ProvinceData provinceData;
    private CityData cityData;
    private DealerData dealerData;
    private DealerAccountData dealerAccountData;

    private int provinceIndex = 0;
    private int cityIndex = 0;
    private int dealerIndex = 0;

    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private Button buttonDueDate;
    private Date dueDate = new Date();

    private DatePickerDialog.OnDateSetListener onDueDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            year = i;
            month = i2;
            day = i3;

            buttonDueDate.setText(MyGlobal.SIMPLE_DATE_FORMAT_WITHOUT_TIME.format(new Date(i - 1900, i2, i3)));
            dueDate.setYear(i - 1900);
            dueDate.setMonth(i2);
            dueDate.setDate(i3);
        }
    };


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
        View view = inflater.inflate(R.layout.warrant_manage_create_fragment, null);
        editWarrantMax = (EditText) view.findViewById(R.id.edit_warrant_max);
        editWarrantUsed = (EditText) view.findViewById(R.id.edit_warrant_used);
        editWarrantAvailable = (EditText) view.findViewById(R.id.edit_warrant_available);
        editWarrantAmount = (EditText) view.findViewById(R.id.edit_warrant_amount);
        editWarrantAmount.setText("0");
        spinnerProvince = (Spinner) view.findViewById(R.id.spin_province);
        spinnerCity = (Spinner) view.findViewById(R.id.spin_city);
        spinnerDealer = (Spinner) view.findViewById(R.id.spin_dealer);
        listDealerAccount = (ListView) view.findViewById(R.id.list_dealer_account);
        buttonDueDate = (Button) view.findViewById(R.id.button_due_date);

        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        buttonDueDate.setText(MyGlobal.SIMPLE_DATE_FORMAT_WITHOUT_TIME.format(Calendar.getInstance().getTime()));

        buttonDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), onDueDateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        if (MyGlobal.checkNetworkConnection(getActivity())) {

            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getText(R.string.loading));
            dialog.setCancelable(true);
            dialog.show();

            GsonRequest gsonObjRequest = new GsonRequest<WarrantData>(Request.Method.GET, MyGlobal.API_FETCH_WARRANT_DETAIL, WarrantData.class,

                    new Response.Listener<WarrantData>() {
                        @Override
                        public void onResponse(WarrantData response) {
                            dialog.dismiss();

                            if (response != null) {
                                warrantData = response;

                                editWarrantMax.setText(warrantData.getWarrantTotalAmount());
                                editWarrantUsed.setText(warrantData.getWarrantNowAmount());
                                editWarrantAvailable.setText(warrantData.getSubAmount());

                                if (MyGlobal.checkNetworkConnection(getActivity())) {
                                    dialog = new ProgressDialog(getActivity());
                                    dialog.setMessage(getText(R.string.loading));
                                    dialog.setCancelable(true);
                                    dialog.show();

                                    GsonRequest gsonObjRequest = new GsonRequest<ProvinceData>(Request.Method.GET, MyGlobal.API_FETCH_PROVINCES, ProvinceData.class,

                                            new Response.Listener<ProvinceData>() {
                                                @Override
                                                public void onResponse(ProvinceData response) {
                                                    dialog.dismiss();

                                                    if (response != null && response.getSuccess().equals("true")) {
                                                        provinceData = response;
                                                        String[] data = new String[provinceData.getProvinces().size()];
                                                        for(int i = 0; i< provinceData.getProvinces().size();i++){
                                                            data[i] = provinceData.getProvinces().get(i).getName();
                                                        }
                                                        spinnerProvince.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data));
                                                        spinnerProvince.setSelection(provinceIndex, true);
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
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
                                            });

                                    RequestManager.getRequestQueue().add(gsonObjRequest);
                                }

                            }
                        }
                    },
                    new Response.ErrorListener() {
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
                    });

            RequestManager.getRequestQueue().add(gsonObjRequest);
        }

        ((Spinner) view.findViewById(R.id.spin_province)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provinceIndex = position;
                cityIndex = 0;
                if (MyGlobal.checkNetworkConnection(getActivity())) {

                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage(getText(R.string.loading));
                    dialog.setCancelable(true);
                    dialog.show();

                    GsonRequest gsonObjRequest = new GsonRequest<CityData>(Request.Method.GET, MyGlobal.API_FETCH_CITYS + "&province=" + provinceData.getProvinces().get(provinceIndex).getId(), CityData.class,

                            new Response.Listener<CityData>() {
                                @Override
                                public void onResponse(CityData response) {
                                    dialog.dismiss();

                                    if (response != null && response.getSuccess().equals("true")) {
                                        cityData = response;
                                        String[] data = new String[cityData.getCitys().size()];
                                        for(int i = 0; i< cityData.getCitys().size();i++){
                                            data[i] = cityData.getCitys().get(i).getName();
                                        }
                                        spinnerCity.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data));
                                        spinnerCity.setSelection(cityIndex, true);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
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
                            });

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Spinner) view.findViewById(R.id.spin_city)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityIndex = position;
                dealerIndex = 0;
                if (MyGlobal.checkNetworkConnection(getActivity())) {

                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage(getText(R.string.loading));
                    dialog.setCancelable(true);
                    dialog.show();

                    String api = MyGlobal.API_FETCH_DEALERS + "&province=" + provinceData.getProvinces().get(provinceIndex).getId() + "&city=" + cityData.getCitys().get(cityIndex).getId();
                    GsonRequest gsonObjRequest = new GsonRequest<DealerData>(Request.Method.GET, api, DealerData.class,

                            new Response.Listener<DealerData>() {
                                @Override
                                public void onResponse(DealerData response) {
                                    dialog.dismiss();

                                    if (response != null && response.getSuccess().equals("true")) {
                                        dealerData = response;
                                        String[] data = new String[dealerData.getDealers().size()];
                                        for(int i = 0; i< dealerData.getDealers().size();i++){
                                            data[i] = dealerData.getDealers().get(i).getName();
                                        }
                                        spinnerDealer.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data));
                                        spinnerDealer.setSelection(dealerIndex, true);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
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
                            });

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Spinner) view.findViewById(R.id.spin_dealer)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dealerIndex = position;
                if (MyGlobal.checkNetworkConnection(getActivity())) {

                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage(getText(R.string.loading));
                    dialog.setCancelable(true);
                    dialog.show();

                    String api = MyGlobal.API_FETCH_DEALER_ACCOUNT + "&dealerid=" + dealerData.getDealers().get(dealerIndex).getId();
                    GsonRequest gsonObjRequest = new GsonRequest<DealerAccountData>(Request.Method.GET, api, DealerAccountData.class,

                            new Response.Listener<DealerAccountData>() {
                                @Override
                                public void onResponse(DealerAccountData response) {
                                    dialog.dismiss();

                                    if (response != null && response.getSuccess().equals("true")) {
                                        dealerAccountData = response;
                                        DealerAccount dealerAccount = new DealerAccount();
                                        dealerAccount.setDealerCode("账户名称");
                                        dealerAccount.setBalance("账户余额");
                                        dealerAccount.setCreditLimit("信用额度");
                                        dealerAccount.setAmount("已担保");
                                        dealerAccount.setMoreTimes("逾期");
                                        dealerAccountData.getDealeraccounts().add(0, dealerAccount);
                                        listDealerAccount.setAdapter(new DealerAccountAdapter());
                                    }
                                }
                            },
                            new Response.ErrorListener() {
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
                            });

                    RequestManager.getRequestQueue().add(gsonObjRequest);
                }
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

        ((Button) view.findViewById(R.id.button_warrant_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dealerId = dealerData.getDealers().get(dealerIndex).getId();
                String warrantAmount = editWarrantAmount.getText().toString();
                String dueDate = MyGlobal.SIMPLE_DATE_FORMAT_WITHOUT_TIME.format(new Date(year - 1900, month, day));
                if (dealerId.isEmpty()) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.hint_dealer_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }
                if (warrantAmount.isEmpty() || Double.valueOf(warrantAmount) <= 0) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.hint_amount_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }
                if (dueDate.isEmpty()) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.hint_due_date_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }
                if ((new Date(year - 1900, month, day, 23, 59, 59)).before(new Date())) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.hint_due_date_invalid)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }

                pairs = new ArrayList<BasicNameValuePair>();
                pairs.add(new BasicNameValuePair("dealerid", dealerId));
                pairs.add(new BasicNameValuePair("enddate", dueDate));
                pairs.add(new BasicNameValuePair("amount", editWarrantAmount.getText().toString()));

                post = new HttpMultipartPost(getActivity(), MyGlobal.API_SUBMIT_WARRANT, pairs, filePaths, getText(R.string.submitting).toString());
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

    public class DealerAccountAdapter extends BaseAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_dealer_account, null);
            TextView text_account_name = (TextView) view.findViewById(R.id.text_account_name);
            TextView text_account_amount = (TextView) view.findViewById(R.id.text_account_amount);
            TextView text_account_credit = (TextView) view.findViewById(R.id.text_account_credit);
            TextView text_account_used = (TextView) view.findViewById(R.id.text_account_used);
            TextView text_account_times = (TextView) view.findViewById(R.id.text_account_times);

            text_account_name.setText(dealerAccountData.getDealeraccounts().get(position).getDealerCode());
            text_account_amount.setText(dealerAccountData.getDealeraccounts().get(position).getBalance());
            text_account_credit.setText(dealerAccountData.getDealeraccounts().get(position).getCreditLimit());
            text_account_used.setText(dealerAccountData.getDealeraccounts().get(position).getAmount());
            text_account_times.setText(dealerAccountData.getDealeraccounts().get(position).getMoreTimes());

            return view;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return dealerAccountData.getDealeraccounts().size();
        }

    }

}
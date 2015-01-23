package com.luyuan.mobile.function;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.luyuan.mobile.R;
import com.luyuan.mobile.model.SuccessData;
import com.luyuan.mobile.ui.ImagePreviewActivity;
import com.luyuan.mobile.util.FileUtilities;
import com.luyuan.mobile.util.GsonRequest;
import com.luyuan.mobile.util.HttpMultipartPost;
import com.luyuan.mobile.util.MyGlobal;
import com.luyuan.mobile.util.RequestManager;

import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 门店打点页面
public class UploadMaterialDedicatedFragment extends Fragment {
    private final ArrayList<String> filePaths = new ArrayList<String>();
    private final ArrayList<Bitmap> fileThumbs = new ArrayList<Bitmap>();
    private final int FROM_CAMERA = 1;
    private final int FROM_PHOTO = 2;
    private final int FROM_VIDEO = 3;
    private final int FROM_AUDIO = 4;
    private LocationClient mLocationClient;
    private HttpMultipartPost post;
    private List<BasicNameValuePair> pairs;
    private Gallery gallery;
    private Uri mCapturedImageURI;
    private EditText editTextLocation;
    private EditText editTextArea;
    private EditText editTextUdf;
    private Spinner spinnerBrand;
    private ProgressDialog dialog;
    private double latitude;
    private double longitude;
    private String province = "";
    private String city = "";
    private String district = "";

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
        View view = inflater.inflate(R.layout.upload_material_dedicated_fragment, null);

        // 定位
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                dialog.dismiss();
                mLocationClient.stop();
                latitude = bdLocation.getLatitude();
                longitude = bdLocation.getLongitude();
                province = bdLocation.getProvince();
                city = bdLocation.getCity();
                district = bdLocation.getDistrict();
                editTextLocation.setText(bdLocation.getAddrStr());
            }
        });

        editTextLocation = (EditText) view.findViewById(R.id.edittext_location_place);
        editTextArea = (EditText) view.findViewById(R.id.edittext_area);
        editTextUdf = (EditText) view.findViewById(R.id.edittext_brand);

        spinnerBrand = (Spinner) view.findViewById(R.id.spin_brand);
        String[] spinnerData = new String[]{"", "绿源", "爱玛", "雅迪", "自定义"};
        spinnerBrand.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerData));
        spinnerBrand.setSelection(0, true);

        // 添加附件
        ((Button) view.findViewById(R.id.button_attach)).setOnClickListener(new View.OnClickListener() {
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
                                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

        // 上传门店资料
        ((Button) view.findViewById(R.id.button_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String location = editTextLocation.getText().toString().trim();
                String area = editTextArea.getText().toString().trim();
                String brand = spinnerBrand.getSelectedItem().toString().trim();
                String udf = editTextUdf.getText().toString().trim();
                if (location.isEmpty()) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.location_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }
                if (area.isEmpty()) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.area_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }
                if (brand.isEmpty() || (brand.equals("自定义") && udf.isEmpty())) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.brand_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }
                if (filePaths.isEmpty()) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.material_files_empty)
                            .setTitle(R.string.dialog_hint)
                            .setPositiveButton(R.string.dialog_confirm, null)
                            .create()
                            .show();
                    return;
                }

                pairs = new ArrayList<BasicNameValuePair>();
                pairs.add(new BasicNameValuePair("location", location));
                pairs.add(new BasicNameValuePair("province", province));
                pairs.add(new BasicNameValuePair("city", city));
                pairs.add(new BasicNameValuePair("district", district));
                pairs.add(new BasicNameValuePair("latitude", String.valueOf(latitude)));
                pairs.add(new BasicNameValuePair("longitude", String.valueOf(longitude)));
                pairs.add(new BasicNameValuePair("area", area));
                pairs.add(new BasicNameValuePair("brand", brand));
                pairs.add(new BasicNameValuePair("udf", udf));

                // 检查是否100m以内有上传过相同的资料
                if (MyGlobal.checkNetworkConnection(getActivity())) {

                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage(getText(R.string.loading));
                    dialog.setCancelable(true);
                    dialog.show();

                    String url = "";
                    try {
                        url = MyGlobal.API_CHECK_DEDICATED
                                + "&brand=" + URLEncoder.encode(brand, "utf-8")
                                + "&udf=" + URLEncoder.encode(udf, "utf-8")
                                + "&latitude=" + String.valueOf(latitude)
                                + "&longitude=" + String.valueOf(longitude);

                    } catch (UnsupportedEncodingException e) {
                    }

                    GsonRequest gsonObjRequest = new GsonRequest<SuccessData>(Request.Method.GET, url,
                            SuccessData.class, new Response.Listener<SuccessData>() {

                        @Override
                        public void onResponse(SuccessData response) {
                            dialog.dismiss();

                            if (response != null && response.getSuccess().equals("true")) {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(R.string.check_material_exists)
                                        .setTitle(R.string.dialog_hint)
                                        .setNegativeButton(R.string.cancel, null)
                                        .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                post = new HttpMultipartPost(getActivity(), MyGlobal.API_SUBMIT_DEDICATED, pairs, filePaths, getText(R.string.submitting).toString());
                                                post.execute();
                                            }
                                        })
                                        .create()
                                        .show();
                            } else {
                                post = new HttpMultipartPost(getActivity(), MyGlobal.API_SUBMIT_DEDICATED, pairs, filePaths, getText(R.string.submitting).toString());
                                post.execute();
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

        // 定位
        ((Button) view.findViewById(R.id.button_location)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage(getText(R.string.locating2));
                dialog.setCancelable(true);
                dialog.show();

                // 开启定位
                InitLocation();
                mLocationClient.start();
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
        mLocationClient.stop();
        super.onDestroy();
    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option);
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

        return devices.contains(android.os.Build.BRAND + "/" + android.os.Build.PRODUCT + "/"
                + android.os.Build.DEVICE);
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

}
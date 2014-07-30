package com.luyuan.mobile.function;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.ui.ImagePreviewActivity;
import com.luyuan.mobile.util.FileUtilities;
import com.luyuan.mobile.util.HttpMultipartPost;
import com.luyuan.mobile.util.MyGlobal;

import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UploadMaterialNewFragment extends Fragment {

    private final ArrayList<String> filePaths = new ArrayList<String>();
    private final ArrayList<Bitmap> fileThumbs = new ArrayList<Bitmap>();
    private final int FROM_CAMERA = 1;
    private final int FROM_PHOTO = 2;
    private final int FROM_VIDEO = 3;
    private final int FROM_AUDIO = 4;
    private HttpMultipartPost post;
    private Gallery gallery;
    private Uri imageUri;
    private String channel = "";
    private EditText editTextName;
    private EditText editTextRemark;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_material_new_fragment, null);

        editTextName = (EditText) view.findViewById(R.id.edittext_material_name);
        editTextRemark = (EditText) view.findViewById(R.id.edittext_material_remark);

        Bundle args = getArguments();
        if (args != null && args.getString("channel") != null) {
            channel = args.getString("channel");
        }

        // add attachment
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
                                    File dir = new File(MyGlobal.CAMERA_PATH);
                                    if (!dir.exists()) {
                                        dir.mkdirs();
                                    }
                                    File file = new File(dir, name);
                                    imageUri = Uri.fromFile(file);
                                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                    startActivityForResult(intent, FROM_CAMERA);
                                } else if (which == 1) {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("image/*");
                                    startActivityForResult(Intent.createChooser(intent, "Select image"), FROM_PHOTO);
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

        // submit material
        ((Button) view.findViewById(R.id.button_submit_material)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // create material
                String name = editTextName.getText().toString().trim();
                String remark = editTextRemark.getText().toString().trim();
                if (name.isEmpty()) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.material_name_empty)
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

                List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
                pairs.add(new BasicNameValuePair("channel", channel));
                pairs.add(new BasicNameValuePair("name", name));
                pairs.add(new BasicNameValuePair("remark", remark));

                post = new HttpMultipartPost(getActivity(), MyGlobal.API_SUBMIT_MATERIAL, pairs, filePaths, getText(R.string.submitting).toString());
                post.execute();

            }
        });

        // back to channel
        ((Button) view.findViewById(R.id.button_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_content, new UploadMaterialChannelFragment());
                fragmentTransaction.commit();
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
                filePath = imageUri.getPath();
                bitmap = BitmapFactory.decodeFile(filePath, options);
                break;

            case FROM_PHOTO:
                uri = data.getData();
                if (uri == null) {
                    return;
                }
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

        super.onActivityResult(requestCode, resultCode, data);
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
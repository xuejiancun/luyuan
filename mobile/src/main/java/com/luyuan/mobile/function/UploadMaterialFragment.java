package com.luyuan.mobile.function;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import com.luyuan.mobile.R;
import com.luyuan.mobile.util.FileUtilities;
import com.luyuan.mobile.util.HttpMultipartPost;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UploadMaterialFragment extends Fragment {

    private HttpMultipartPost post;

    private Gallery gallery;
    private Uri imageUri;
    private final ArrayList<String> imagePaths = new ArrayList<String>();
    private final ArrayList<Bitmap> images = new ArrayList<Bitmap>();

    private final int FROM_CAMERA = 1;
    private final int FROM_PHOTO = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_material, null);

        // choose files
        ((Button) view.findViewById(R.id.button_attach_file_upload_material)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = new CharSequence[]{getText(R.string.from_camera), getText(R.string.from_photo)
                        , getText(R.string.from_camera), getText(R.string.from_photo)};

                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.choose_file)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                                    String name = "IMG_" + sdf.format(new Date()) + ".jpg";
                                    File dir = new File(Environment.getExternalStorageDirectory() + "/luyuan/camera/");
                                    dir.mkdirs();
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
                                    startActivityForResult(Intent.createChooser(intent, "Select video"), FROM_PHOTO);
                                } else if (which == 3) {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("audio/*");
                                    startActivityForResult(Intent.createChooser(intent, "Select audio"), FROM_PHOTO);
                                }
                            }
                        })
                        .create()
                        .show();
            }
        });

        // upload files
        ((Button) view.findViewById(R.id.button_submit_material_upload_material)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (String path : imagePaths) {
                    File file = new File(path);
                    if (file.exists()) {
                        post = new HttpMultipartPost(getActivity(), path);
                        post.execute();
                    } else {
                        // Toast.makeText(getActivity(), "file not exists", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        gallery = (Gallery) view.findViewById(R.id.gallery_attach_file_upload_material);
        gallery.setAdapter(new ImageAdapter(getActivity()));

        // Set a item click listener, and just Toast the clicked position
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // TODO preview image
                // Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
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
        images.remove(info.position);
        imagePaths.remove(info.position);
        gallery.setAdapter(new ImageAdapter(getActivity()));
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_OK) {
            return;
        }
        String path = "";
        switch (requestCode) {
            case FROM_CAMERA:
                path = imageUri.getPath();

                break;
            case FROM_PHOTO:
                Uri uri = data.getData();
                if (uri == null) {
                    return;
                }
                try {
                    path = FileUtilities.getPath(getActivity(), uri);
                } catch (URISyntaxException e) {
                }
                break;
        }
        if (!path.isEmpty()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bm = BitmapFactory.decodeFile(path, options);
            images.add(0, bm);
            imagePaths.add(0, path);
            gallery.setAdapter(new ImageAdapter(getActivity()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class ImageAdapter extends BaseAdapter {
        private static final int ITEM_WIDTH = 140;
        private static final int ITEM_HEIGHT = 90;

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
            return images.size();
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

                // The preferred Gallery item background
                imageView.setBackgroundResource(mGalleryItemBackground);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageBitmap(images.get(position));

            return imageView;
        }
    }

}
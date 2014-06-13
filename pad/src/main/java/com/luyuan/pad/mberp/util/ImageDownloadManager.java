package com.luyuan.pad.mberp.util;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luyuan.pad.mberp.model.ImagePager;
import com.luyuan.pad.mberp.model.ProductThumbData;

public class ImageDownloadManager {

    String[] productDetailUrlList = new String[]{
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/8f06ab37-ff4d-4d8c-9e15-10c8450940b5.jpg",
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/20a7b27f-a02f-4035-8ab4-586ff9d124e9.jpg",
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/31daad89-1532-4b57-98bd-5324eae57b1e.jpg",
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/f32b88a7-d05a-4d9a-9f32-308d3da48237.jpg",
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/d28f0729-948b-41ff-a316-5cc7a5a3c761.jpg",
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/cddf4102-1fb1-4ed7-8ae8-e1546a5fbd9b.jpg",
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/af21d9d7-320e-4fb2-882b-c8e3fd764a0a.jpg",
    };

    String[] productEquipmentUrlList = new String[]{
            "http://www.luyuan.cn/UploadPhoto/Function/3.jpg",
            "http://www.luyuan.cn/UploadPhoto/Function/10.jpg",
            "http://www.luyuan.cn/UploadPhoto/Function/11.jpg",
            "http://www.luyuan.cn/UploadPhoto/Function/12.jpg",
            "http://www.luyuan.cn/UploadPhoto/Function/3.jpg",
            "http://www.luyuan.cn/UploadPhoto/Function/10.jpg",
            "http://www.luyuan.cn/UploadPhoto/Function/11.jpg",
            "http://www.luyuan.cn/UploadPhoto/Function/12.jpg",
            "http://www.luyuan.cn/UploadPhoto/Function/3.jpg",
            "http://www.luyuan.cn/UploadPhoto/Function/10.jpg",
            "http://www.luyuan.cn/UploadPhoto/Function/11.jpg",
            "http://www.luyuan.cn/UploadPhoto/Function/12.jpg",
    };

    private static ImageDownloadManager mInstance;

    public static ImageDownloadManager getInstance() {
        if (mInstance == null)
            mInstance = new ImageDownloadManager();

        return mInstance;
    }

    private ImagePager imagePager;
    private ProductThumbData productThumbData;

    public ImagePager getImagePager() {
        return imagePager;
    }

    public ProductThumbData getProductThumbData() {
        return productThumbData;
    }

    public String[] getProductDetailUrlList() {
        return productDetailUrlList;
    }

    public String[] getProductEquipmentUrlList() {
        return productEquipmentUrlList;
    }

    private ImageView imageView;

    public void downloadEverything(Context context) {
        imageView = new ImageView(context);
        // fetchBrandHonorData();
        //  fetchProductThumbData();
    }

    public void fetchBrandHonorData() {
        GsonRequest gsonObjRequest = new GsonRequest<ImagePager>(Request.Method.GET, GlobalConstantValues.API_BRAND_HONOR,
                ImagePager.class, new Response.Listener<ImagePager>() {
            @Override
            public void onResponse(ImagePager response) {
                imagePager = response;
//                for (ImageSlide brandHonorSlide : imagePager.getImageSlides()) {
//                    ImageLoader imageLoader = ImageCacheManager.getInstance().getImageLoader();
//                    imageLoader.get(brandHonorSlide.getUrl(), ImageLoader.getImageListener(imageView, R.drawable.no_image, R.drawable.no_image));
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        RequestManager.getRequestQueue().add(gsonObjRequest);
    }

}


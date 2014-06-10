package com.luyuan.pad.mberp.util;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.luyuan.pad.mberp.R;
import com.luyuan.pad.mberp.model.BrandHonorData;
import com.luyuan.pad.mberp.model.BrandHonorSlide;
import com.luyuan.pad.mberp.model.PopularCarData;
import com.luyuan.pad.mberp.model.PopularCarSlide;
import com.luyuan.pad.mberp.model.ProductThumbData;
import com.luyuan.pad.mberp.model.TechImageData;
import com.luyuan.pad.mberp.model.TechImageSlide;

public class ImageDownloadManager {

    String[] productThumbUrlList = new String[]{
            "http://www.luyuan.cn/UploadPhoto/2014/3/21/2874bfec-e924-4e0e-bf3f-eaa2b581c351.png",
            "shttp://www.luyuan.cn/UploadPhoto/2014/3/23/0c6940b3-03da-4102-a03c-1417137388b1.png",
            "http://www.luyuan.cn/UploadPhoto/2014/4/14/4f54abd0-4051-4b86-a3bb-87e1a05b73ec.png",
            "http://www.luyuan.cn/UploadPhoto/2014/4/14/8c235d65-39de-4c4c-88bf-d6a0bfa7d0f2.png",
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/ca42f614-4680-4099-af05-72609fd4f59a.png",
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/f4509f98-9d73-4004-b8e2-ed472044e53d.png",
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/959b3f16-d975-458b-889b-7d67c23d1901.png",
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/dd89a158-e6c2-4810-907f-30cdcdb1c1f6.png",
            "http://www.luyuan.cn/UploadPhoto/2014/5/12/e041d643-f253-4db5-86ab-03908b38eeaf.png",
            "http://www.luyuan.cn/UploadPhoto/2014/5/12/c302cb1f-0cc4-495c-af34-4fe7d40aba24.png",
            "http://www.luyuan.cn/UploadPhoto/2014/5/16/5c5a4f94-dd4f-46ec-87dd-65398abdfb45.png",
            "http://www.luyuan.cn/UploadPhoto/2014/5/24/da618a81-bf41-4edb-8bc4-d3c175ae1ecb.png",
    };

    String[] productDetailUrlList = new String[]{
            "http://www.luyuan.cn/UploadPhoto/2014/4/29/8f06ab37-ff4d-4d8c-9e15-10c8450940b5.jpg",
            "shttp://www.luyuan.cn/UploadPhoto/2014/4/29/20a7b27f-a02f-4035-8ab4-586ff9d124e9.jpg",
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

    private PopularCarData popularCarData;
    private TechImageData techImageData;
    private BrandHonorData brandHonorData;
    private ProductThumbData productThumbData;

    public PopularCarData getPopularCarData() {
        return popularCarData;
    }

    public TechImageData getTechImageData() {
        return techImageData;
    }

    public BrandHonorData getBrandHonorData() {
        return brandHonorData;
    }

    public ProductThumbData getProductThumbData() {
        return productThumbData;
    }

    public String[] getProductThumbUrlList() {
        return productThumbUrlList;
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
        fetchPopularCarData();
        fetchTechImageData();
        // fetchBrandHonorData();
    }

    public void fetchPopularCarData() {
//        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,
//                POPULAR_CAR_URL, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        if (response != null) {
//                            popularCarData = JSON.parseObject(response.toString(), PopularCarData.class);
//                            for (PopularCarSlide popularSlide : popularCarData.getPopularCarSlides()) {
//                                ImageLoader imageLoader = ImageCacheManager.getInstance().getImageLoader();
//                                imageLoader.get(popularSlide.getUrl(), ImageLoader.getImageListener(imageView, R.drawable.no_image, R.drawable.no_image));
//                            }
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }
//        );

        GsonRequest gsonObjRequest = new GsonRequest<PopularCarData>(Request.Method.GET, GlobalConstantValues.API_POPULAR_CAR,
                PopularCarData.class, new Response.Listener<PopularCarData>() {
            @Override
            public void onResponse(PopularCarData response) {
                popularCarData = response;
                for (PopularCarSlide popularSlide : popularCarData.getPopularCarSlides()) {
                    ImageLoader imageLoader = ImageCacheManager.getInstance().getImageLoader();
                    imageLoader.get(popularSlide.getUrl(), ImageLoader.getImageListener(imageView, R.drawable.no_image, R.drawable.no_image));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        RequestManager.getRequestQueue().add(gsonObjRequest);
    }

    public void fetchTechImageData() {
        GsonRequest gsonObjRequest = new GsonRequest<TechImageData>(Request.Method.GET, GlobalConstantValues.API_TECH_IMAGE,
                TechImageData.class, new Response.Listener<TechImageData>() {
            @Override
            public void onResponse(TechImageData response) {
                techImageData = response;
                for (TechImageSlide techImageSlide : techImageData.getTechImageSlides()) {
                    ImageLoader imageLoader = ImageCacheManager.getInstance().getImageLoader();
                    imageLoader.get(techImageSlide.getUrl(), ImageLoader.getImageListener(imageView, R.drawable.no_image, R.drawable.no_image));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        RequestManager.getRequestQueue().add(gsonObjRequest);
    }

    public void fetchBrandHonorData() {
        GsonRequest gsonObjRequest = new GsonRequest<BrandHonorData>(Request.Method.GET, GlobalConstantValues.API_BRAND_HONOR,
                BrandHonorData.class, new Response.Listener<BrandHonorData>() {
            @Override
            public void onResponse(BrandHonorData response) {
                brandHonorData = response;
                for (BrandHonorSlide brandHonorSlide : brandHonorData.getBrandHonorSlides()) {
                    ImageLoader imageLoader = ImageCacheManager.getInstance().getImageLoader();
                    imageLoader.get(brandHonorSlide.getUrl(), ImageLoader.getImageListener(imageView, R.drawable.no_image, R.drawable.no_image));
                }
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


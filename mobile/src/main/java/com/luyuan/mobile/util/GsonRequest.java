package com.luyuan.mobile.util;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {

    private final Gson mGson = new Gson();
    private final Class<T> mClass;
    private final Listener<T> mListener;
    private Map<String, String> headers;

    public GsonRequest(int method
            , String url
            , Class<T> objectClass
            , Listener<T> listener
            , ErrorListener errorListener) {

        super(method, url, errorListener);
        this.mClass = objectClass;
        this.mListener = listener;

        setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(json, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        if (!MyGlobal.getUser().getSessionId().isEmpty()) {
            headers.put("Cookie", "ASP.NET_SessionId=" + MyGlobal.getUser().getSessionId());
        }
        return headers;
    }

}

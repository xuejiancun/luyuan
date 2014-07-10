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

/**
 * Wrapper for Volley requests to facilitate parsing of json responses.
 *
 * @param <T>
 */
public class GsonRequest<T> extends Request<T> {

    private Map<String, String> headers = new HashMap<String, String>();

    /**
     * Gson parser
     */
    private final Gson mGson;

    /**
     * Class type for the response
     */
    private final Class<T> mClass;


    /**
     * Callback for response delivery
     */
    private final Listener<T> mListener;

    /**
     * Priority for the request
     */
    private Priority priority = Priority.IMMEDIATE;

    /**
     * @param method        Request type.. Method.GET etc
     * @param url           path for the requests
     * @param objectClass   expected class type for the response. Used by gson for serialization.
     * @param listener      handler for the response
     * @param errorListener handler for errors
     */
    public GsonRequest(int method
            , String url
            , Class<T> objectClass
            , Listener<T> listener
            , ErrorListener errorListener) {

        super(method, url, errorListener);
        this.mClass = objectClass;
        this.mListener = listener;
        mGson = new Gson();

        setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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

    public Priority getPriority() {
        return priority;
    }


    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (!MyGlobal.getUser().getSessionId().isEmpty()) {
            headers.put("Cookie", "ASP.NET_SessionId=" + MyGlobal.getUser().getSessionId());
        }
        return headers;
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }

}

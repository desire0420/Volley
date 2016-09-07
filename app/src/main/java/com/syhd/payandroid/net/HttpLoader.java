package com.syhd.payandroid.net;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.thinkive.framework.network.ResponseListener;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.syhd.payandroid.network.GentouHttpService;

import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 封装volley的post&get请求
 */
public class HttpLoader {
    private static HttpLoader httpLoader = null;
    private RequestQueue mRequestQueue;

    public HttpLoader(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public synchronized static HttpLoader getInstance(Context context) {
        if (httpLoader == null) {
            httpLoader = new HttpLoader(context);
        }
        return httpLoader;
    }

    /**
     * 封装volley的post&get请求
     *
     * @param url
     * @param map
     * @param mSyncCallBack
     */
    public void postRequest(String url, final Map<String, String> map, final SyncCallback mSyncCallBack) {
        StringRequest request = new StringRequest(Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mSyncCallBack.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSyncCallBack.onError(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, 1.0F));
        addToRequestQueue(request, getTag(map));
    }

    public String getTag(Map<String, String> parasMap) {
        if (parasMap == null || parasMap.size() == 0) {
            return "";
        }
        JSONObject paramObject = null;
        try {
            paramObject = new JSONObject(parasMap);
        } catch (Exception e) {
            return "";
        }
        if (null != paramObject) return paramObject.toString();
        return "";
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        if (null != mRequestQueue) {
            req.setTag(TextUtils.isEmpty(tag) ? "gentouwang" : tag);
            VolleyLog.d("Add request to queue: %s", req.getUrl());
            mRequestQueue.add(req);
        }
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * 封装volley的post&get请求
     *
     * @param url
     * @param mSyncCallBack
     */
    public void getRequest(String url, final SyncCallback mSyncCallBack) {
        StringRequest stringRequest = new StringRequest(Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mSyncCallBack.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSyncCallBack.onError(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return new HashMap<>();
            }
        };
        mRequestQueue.add(stringRequest);
    }


}

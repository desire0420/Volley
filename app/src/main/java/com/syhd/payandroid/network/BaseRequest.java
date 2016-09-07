package com.syhd.payandroid.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.thinkive.framework.CoreApplication;
import com.android.thinkive.framework.network.RequestBean;
import com.android.thinkive.framework.network.ResponseListener;
import com.android.thinkive.framework.util.AppUtil;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangwei on 2016/9/7.
 */
public abstract class BaseRequest {
    private static String TAG = "BaseRequest-lxp";

    //-------------------mHandler常量定义，开始----------------------
    /**
     * 请求成功并正确返回，error_no == 0时，在transferAction方法中作为第一个入参
     */
    public static final int REQUEST_SUCCESS = 11;
    /**
     * 请求失败，error_no != 0时，在transferAction方法中作为第一个入参
     */
    public static final int REQUEST_FAILED = 22;

    /*
     * 获取缓存成功
     */
    public static final int REQUEST_CACHE_SUCCESS = 33;

    /*
     * 获取缓存失败
     */
    public static final int REQUEST_CACHE_FAILED = 44;

    public static final String ERROR_NO = "error_no";
    public static final String ERROR_INFO = "error_info";

    public static final String ERRORNO = "errorNo";
    public static final String ERRORINFO = "errorInfo";
    //-------------------mHandler常量定义，结束----------------------
   /* *//**
     * 自定义内部Handler，用来回调到主线程
     *//*
    private ActionHandler mActionHandler = new ActionHandler(this);


    *//*
     * 缓存action
     *//*

    private CacheActionHandler mCacheActionHandler = new CacheActionHandler(this);*/

    /**
     * 请求需要的数据对象
     */
    private RequestBean mRequestBean;

    /*
     * 制定Url地址
     */
    private String SpecifyURL;

    /*
     * 功能号为未请求tag；
     */
    private String tag = "";

    /**
     * 请求结果返回监听
     */
    private ResponseListener<JSONObject> mListener = new ResponseListener<JSONObject>() {

        /**
         * 相当于老框架的handler方法，执行在子线程、服务器返回了内容的时后执行
         * @param jsonObject 服务器返回的结果，不一定是正确的结果
         */
        @Override
        public void onResponse(JSONObject jsonObject) {
            Log.i(TAG, "---自动重新登录-->" + jsonObject);

        }

        /**
         * 出错了，服务器没有返回任何结果时执行
         * @param e 在请求服务器过程中，抛出的java异常。
         */
        @Override
        public void onErrorResponse(Exception e) {
            Bundle bundle = new Bundle();
            if (e instanceof ParseError) {
                bundle.putString(ERROR_NO, "-110");
                bundle.putString(ERROR_INFO, "请求参数解析异常!");
            } else if (e instanceof SocketTimeoutException) {
                bundle.putString(ERROR_NO, "-111");
                bundle.putString(ERROR_INFO, "服务器响应超时!");
            } else if (e instanceof TimeoutError) {
                bundle.putString(ERROR_NO, "-112");
                bundle.putString(ERROR_INFO, "服务器请求超时!");
            } else if (e instanceof ServerError) {
                bundle.putString(ERROR_NO, "-114");
                bundle.putString(ERROR_INFO, "服务器出错!");
            } else if (e instanceof VolleyError) {
                bundle.putString(ERROR_NO, "-113");
                bundle.putString(ERROR_INFO, "请求异常!");
            } else {
                bundle.putString(ERROR_NO, "-119");
                bundle.putString(ERROR_INFO, "请检查您的网络!");
            }
            Log.i("MainActivity", "----in request--onErrorResponse->" + bundle.toString());
           // transferErrorAction(REQUEST_FAILED, bundle);

        }
    };

//    /**
//     * @param action
//     */
    public BaseRequest() {
        mRequestBean = new RequestBean();

    }
//
//    /**
//     * @param action
//     */
//    public BaseRequest(IRequestAction action, IRequestCacheAction cacheAction) {
//        mRequestBean = new RequestBean();
//        mActionHandler.setAction(action);
//        mCacheActionHandler.setAction(cacheAction);
//    }

    /**
     * 发起请求 (默认情况下）
     */
    public void request() {
        request(null, mRequestBean.getParam(), "");
    }

    /*
    /*
     * 发起请求
     */
    public void request(String FunNo, HashMap<String, String> params, String jsessionid) {
        HashMap<String, String> map = new HashMap<>();
        String url = "";
        if (null != FunNo) {
            map.put("funcNo", FunNo + "");
        }
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        url = "http://218.17.158.113:13001/servlet/json?";
        tag = getTag(map);


    }
//
//    /**
//     * 请求成功 回调到发起线程
//     *
//     * @param actionType
//     * @param bundle
//     */
//    void transferAction(int actionType, JSONObject bundle) {
//        Message msg = mActionHandler.obtainMessage();
//        msg.obj = bundle;
//        msg.what = actionType;
//        msg.sendToTarget();
//    }
//
//    /**
//     * 请求失败 回调到发起线程
//     *
//     * @param actionType
//     * @param bundle
//     */
//    void transferErrorAction(int actionType, Bundle bundle) {
//        Message msg = mActionHandler.obtainMessage();
//        msg.obj = bundle;
//        msg.what = actionType;
//        msg.sendToTarget();
//    }
//
//    /**
//     * 请求缓存成功 回调到发起线程
//     *
//     * @param actionType
//     * @param bundle
//     */
//    void transferCacheAction(int actionType, JSONObject bundle) {
//        Message msg = mCacheActionHandler.obtainMessage();
//        msg.obj = bundle;
//        msg.what = actionType;
//        msg.sendToTarget();
//    }
//
//    /**
//     * 请求缓存失败 回调到发起线程
//     *
//     * @param actionType
//     * @param bundle
//     */
//    void transferCacheErrorAction(int actionType, Bundle bundle) {
//        Message msg = mCacheActionHandler.obtainMessage();
//        msg.obj = bundle;
//        msg.what = actionType;
//        msg.sendToTarget();
//    }

    /**
     * @param jsonObject
     */
    abstract void getJsonDataWithoutError(JSONObject jsonObject);


    /*
     * 更新请求参数
     */
    public void setParamHashMap(HashMap<String, String> paramHashMap) {
        mRequestBean.setParam(paramHashMap);
    }


/*
    static class ActionHandler extends Handler {

        WeakReference<BaseRequest> mBaseRequestWeakReference;
        private IRequestAction mAction;

        public ActionHandler(BaseRequest baseRequestWeakReference) {
            super(CoreApplication.getInstance().getMainLooper());
            mBaseRequestWeakReference = new WeakReference<BaseRequest>(baseRequestWeakReference);
        }


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_SUCCESS:
                    mAction.onSuccess(CoreApplication.getInstance(), (JSONObject) msg.obj);
                    break;
                case REQUEST_FAILED:
                    mAction.onFailed(CoreApplication.getInstance(), (Bundle) msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }

        public void setAction(IRequestAction action) {
            mAction = action;
        }
    }

    class CacheActionHandler extends Handler {
        WeakReference<BaseRequest> mBaseRequestWeakReference;
        private IRequestCacheAction mAction;

        public CacheActionHandler(BaseRequest baseRequestWeakReference) {
            super(CoreApplication.getInstance().getMainLooper());
            mBaseRequestWeakReference = new WeakReference<BaseRequest>(baseRequestWeakReference);
        }


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_CACHE_SUCCESS:
                    mAction.onCacheSuccess(CoreApplication.getInstance(), (JSONObject) msg.obj);
                    break;
                case REQUEST_CACHE_FAILED:
                    mAction.onCacheFailed(CoreApplication.getInstance(), (Bundle) msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }

        public void setAction(IRequestCacheAction action) {
            mAction = action;
        }

        public IRequestCacheAction getAction() {
            return mAction;
        }
    }*/

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
}

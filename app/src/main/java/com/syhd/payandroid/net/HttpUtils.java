package com.syhd.payandroid.net;

import android.content.Context;

import java.util.Map;


/**
 * 使用volley6.0进行网络访问
 * Created by 韩宝坤 on 2016/1/14.
 */
public class HttpUtils {
    /**
     * post请求
     * @param url             地址
     * @param map             参数
     * @param mSyncCallBack 回调
     */
    public static void httpPost(Context context,String url, Map<String, String> map, SyncCallback mSyncCallBack) {
        HttpLoader.getInstance(context).postRequest(url, map, mSyncCallBack);
    }

    public static void httpGet(Context context,String url, SyncCallback mSyncCallBack){
       HttpLoader.getInstance(context).getRequest(url, mSyncCallBack);
    }

}

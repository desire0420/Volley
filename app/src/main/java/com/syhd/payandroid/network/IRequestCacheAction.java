package com.syhd.payandroid.network;

import android.content.Context;
import android.os.Bundle;

import org.json.JSONObject;

/**
 * 请求子线程程序回调到主线程用的接口。
 *
 * Announcements：
 *
 * @author 王志鸿
 * @company Thinkive
 * @date 2015/7/6
 */
public interface IRequestCacheAction {

    /**
     * 请求成功，且数据正常获取的时候执行
     * @param context
     * @param successData
     */
    void onCacheSuccess(Context context, JSONObject successData);

    /**
     * 请求失败，包括服务器返回正确结果，但前端程序解析数据时出错导致的失败时执行。
     * @param context
     * @param errorData
     */
    void onCacheFailed(Context context, Bundle errorData);
}

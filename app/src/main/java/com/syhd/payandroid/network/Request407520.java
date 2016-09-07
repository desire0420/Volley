package com.syhd.payandroid.network;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by wangwei on 2016/7/25.
 * 发现首页  推荐投顾
 */
public class Request407520 extends BaseRequest {
    //不用缓存
    public Request407520(HashMap<String, String> params, IRequestAction action) {
        super();
        params.put("funcNo", "407222");
        setParamHashMap(params);
    }
    //用缓存
    public Request407520(HashMap<String, String> params, IRequestAction action, IRequestCacheAction cacheAction) {
        super();
        params.put("funcNo", "407222");
        setParamHashMap(params);
    }

    @Override
    void getJsonDataWithoutError(JSONObject successData) {
       // transferAction(REQUEST_SUCCESS, successData);
    }
}


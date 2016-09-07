package com.syhd.payandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.syhd.payandroid.net.HttpUtils;
import com.syhd.payandroid.net.SyncCallback;
import com.syhd.payandroid.network.IRequestAction;
import com.syhd.payandroid.network.IRequestCacheAction;
import com.syhd.payandroid.network.Request407520;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private Button post;
    private Button get;
    private TextView result;
    String url = "http://218.17.158.113:13001/servlet/json?funcNo=407222&match_id=2&user_id=69";
    String urlpost = "http://218.17.158.113:13001/servlet/json?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 支付宝支付
         */
        post = (Button) findViewById(R.id.post);
        result = (TextView) findViewById(R.id.result);
        get = (Button) findViewById(R.id.get);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncGet();

            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  syncPost();

            }
        });
    }



    private void syncGet() {
        HttpUtils.httpGet(MainActivity.this, url, new SyncCallback() {
            @Override
            public void onError(String request) {
                Log.i(TAG, "onError----" + request);
            }

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse----" + response);
                result.setText(response);
            }

        });
    }

    private void syncPost() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("funcNo", "407222");
        map.put("match_id", "2");
        map.put("user_id", "69");
        HttpUtils.httpPost(MainActivity.this, urlpost, map, new SyncCallback() {
                    @Override
                    public void onError(String request) {
                        Log.i(TAG, "onError----" + request);
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse----" + response);
                        result.setText(response);
                    }
                }
        );

    }
}
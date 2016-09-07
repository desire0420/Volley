package com.syhd.payandroid.net;


public abstract class SyncCallback {
	public abstract void onError(String request);

    public abstract void onResponse(String response);
}

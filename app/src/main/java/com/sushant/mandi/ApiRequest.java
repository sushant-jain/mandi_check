package com.sushant.mandi;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;

public class ApiRequest{
    String url;
    RequestQueue queue;
    SetOnResponseListener mListener;
    Context c;
    public interface SetOnResponseListener{
        void onResponse(String response);
    }

    public ApiRequest(String url, SetOnResponseListener mListener,Context c) {
        this.url = url;
        this.mListener = mListener;
        this.c=c;
        queue= Volley.newRequestQueue(c);
    }

    public void requestAPI(){
        queue.add(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                    mListener.onResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }


}

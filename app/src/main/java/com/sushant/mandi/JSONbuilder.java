package com.sushant.mandi;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sushant on 27-08-2016.
 */
public class JSONbuilder {
    private static final String TAG = "JSONbuilder";
    public static ArrayList<Commodity> commodityArrayListBuilder(String resp) throws JSONException {
        JSONObject jObj=new JSONObject(resp);
        JSONArray jsonArray=jObj.getJSONArray("records");
        ArrayList<Commodity> commodityArrayList=new ArrayList<>();
        int size=jsonArray.length();
        for(int i=0;i<size;i++){
           try {
               JSONObject JSONrecord = jsonArray.getJSONObject(i);
               commodityArrayList.add(new Commodity(JSONrecord.getString("id"), Long.parseLong(JSONrecord.getString("timestamp")),
                       JSONrecord.getString("state"), JSONrecord.getString("district"), JSONrecord.getString("market"),
                       JSONrecord.getString("commodity"), JSONrecord.getString("variety"), JSONrecord.getString("arrival_date"), Long.parseLong(JSONrecord.getString("min_price")),
                       Long.parseLong(JSONrecord.getString("max_price")), Long.parseLong(JSONrecord.getString("modal_price"))));
           }catch(Exception e){
               e.printStackTrace();
           }
        }
        return commodityArrayList;

    }

    public static int getTotalRecords(String resp) throws JSONException {
        JSONObject jObj=new JSONObject(resp);
        String total=jObj.getString("total_records");
        Log.d(TAG, "getTotalRecords: "+total);
        Integer t=Integer.valueOf(total);
        return t;
    }
    public static int getCount(String resp) throws JSONException {
        JSONObject jObj=new JSONObject(resp);
        String count=jObj.getString("count");
        Integer t=Integer.getInteger(count);
        return t;
    }
}

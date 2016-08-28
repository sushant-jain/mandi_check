package com.sushant.mandi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sushant on 27-08-2016.
 */
public class JSONbuilder {
    public static ArrayList<Commodity> commodityArrayListBuilder(String resp) throws JSONException {
        JSONObject jObj=new JSONObject(resp);
        JSONArray jsonArray=jObj.getJSONArray("records");
        ArrayList<Commodity> commodityArrayList=new ArrayList<>();
        int size=jsonArray.length();
        for(int i=0;i<size;i++){
            JSONObject JSONrecord=jsonArray.getJSONObject(i);
            commodityArrayList.add(new Commodity(JSONrecord.getString("id"),Long.parseLong(JSONrecord.getString("timestamp")),
                    JSONrecord.getString("state"),JSONrecord.getString("district"),JSONrecord.getString("market"),
                    JSONrecord.getString("commodity"),JSONrecord.getString("variety"),JSONrecord.getString("arrival_date"),Long.parseLong(JSONrecord.getString("min_price")),
                    Long.parseLong(JSONrecord.getString("max_price")),Long.parseLong(JSONrecord.getString("modal_price"))));
        }
        return commodityArrayList;

    }
}

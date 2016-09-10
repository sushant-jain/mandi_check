package com.sushant.mandi;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Sushant on 07-09-2016.
 */
public class DataAccumulator {
    public static final int REQUEST_STATE_LIST =1;
    public static final int REQUEST_MARKET_LIST=2;
    private static final String TAG = "DataAccumulator";


    String url="https://data.gov.in/api/datastore/resource.json?resource_id=9ef84268-d588-465a-a308-a864a43d0070&api-key=b6e645181bc8abd24cf9271e11ff8866";
    String furl=url+"&offset=";
    Context c;
    Integer totalRecords,curr=0,max;
    ArrayList<String> reqArrayList;
    DataAccumulatedListener mDataAccumulatedListener;
    ArrayList<Commodity> commodityArrayList;

    public DataAccumulator(Context c,DataAccumulatedListener mDataAccumulatedListener) {
        this.c = c;
        this.mDataAccumulatedListener=mDataAccumulatedListener;
    }

    public DataAccumulator(Context c, String url, DataAccumulatedListener mDataAccumulatedListener) {
        this.c = c;
        this.url = url;
        this.mDataAccumulatedListener = mDataAccumulatedListener;
    }

    public ArrayList<String> reqArrayBuilder(final int reqCode){

        reqArrayList = new ArrayList<>();
        ApiRequest rqTotal=new ApiRequest(url, new ApiRequest.SetOnResponseListener() {
            @Override
            public void onResponse(String response) {
                try {
                    totalRecords=JSONbuilder.getTotalRecords(response);
                    max=totalRecords/100;
                    for(curr=0;curr<=max&&((commodityArrayList!=null)?commodityArrayList.size()!=0:true);curr++){
                        ApiRequest rqStates=new ApiRequest(furl + String.valueOf(curr), new ApiRequest.SetOnResponseListener() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    commodityArrayList = JSONbuilder.commodityArrayListBuilder(response);
                                    for(Commodity c:commodityArrayList){
                                        if(reqCode==REQUEST_STATE_LIST){
                                        if(!reqArrayList.contains(c.getState())){
                                            reqArrayList.add(c.getState());
                                            Log.d(TAG, "onResponse: adding "+c.getState());
                                        }}
                                        else if(reqCode==REQUEST_MARKET_LIST){
                                            if(!reqArrayList.contains(c.getMarket())){
                                                reqArrayList.add(c.getMarket());
                                                Log.d(TAG, "onResponse: adding "+c.getMarket());
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mDataAccumulatedListener.onDataAccumulated(reqArrayList);
                            }
                        },c);
                        rqStates.requestAPI();
                    }

//                    mDataAccumulatedListener.onDataAccumulated(reqArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },c);
        rqTotal.requestAPI();
        //while(curr!=1);
        return reqArrayList;

    }
}
interface DataAccumulatedListener<ArrayListDataType> {
    void onDataAccumulated(ArrayList<ArrayListDataType> arrayList);
}


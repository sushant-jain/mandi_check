package com.sushant.mandi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button btnDownload;
    TextView tvData;
    ListView lv;
    EditText etSearch;

    RequestQueue queue;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownload= (Button) findViewById(R.id.bt_download);
        tvData= (TextView) findViewById(R.id.tv_data);
        lv= (ListView) findViewById(R.id.lv_comm);
        etSearch= (EditText) findViewById(R.id.et_query);

        queue= Volley.newRequestQueue(this);

        url="https://data.gov.in/api/datastore/resource.json?resource_id=9ef84268-d588-465a-a308-a864a43d0070&api-key=b6e645181bc8abd24cf9271e11ff8866";

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    queue.add(stringRequestBuilder());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        DataAccumulator stateDA=new DataAccumulator(this, new DataAccumulatedListener<String>() {
            @Override
            public void onDataAccumulated(ArrayList<String> stateArrayList) {
                Log.d(TAG, "onCreate: stateArraysize"+stateArrayList.size());

            }
        });
        stateDA.reqArrayBuilder(DataAccumulator.REQUEST_STATE_LIST);


        DataAccumulator marketDA=new DataAccumulator(this, new DataAccumulatedListener<String>() {
            @Override
            public void onDataAccumulated(ArrayList<String> marketArrayList) {
                Log.d(TAG, "onCreate: marketArraysize"+marketArrayList.size());

            }
        });
        marketDA.reqArrayBuilder(DataAccumulator.REQUEST_MARKET_LIST);



    }
    StringRequest stringRequestBuilder() throws UnsupportedEncodingException {
        String stateSearch=etSearch.getText().toString();
        stateSearch=URLEncoder.encode(stateSearch,"utf-8");
        String furl;
        if(stateSearch!=""){
            String query="&filters[state]=\""+stateSearch+"\"";
            //query=URLEncoder.encode(query,"utf-8");
            furl=url+query;
            Log.d(TAG, "stringRequestBuilder: "+furl);
        }else{
            furl=url;
            Log.d(TAG, "stringRequestBuilder: empty");
        }
        //furl= URLEncoder.encode(furl,"utf-8");
        StringRequest stringRequest=new StringRequest(Request.Method.GET, furl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //tvData.setText(response);
                //Log.d(TAG, "onResponse: "+response);
                try {
                    ArrayList<Commodity> commodityArrayList=JSONbuilder.commodityArrayListBuilder(response);
                    ArrayList<String> commodityNameArray=new ArrayList<>();
                    for(Commodity c:commodityArrayList){
                        commodityNameArray.add(c.getState()+" "+c.getCommodity()+" "+c.getModalPrice());
                    }
                    ArrayAdapter<String> commAdapter=new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_list_item_1,commodityNameArray);
                    lv.setAdapter(commAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvData.setText(error.toString());
            }
        }) ;
        return  stringRequest;
    }
}

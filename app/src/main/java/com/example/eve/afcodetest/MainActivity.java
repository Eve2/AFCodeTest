package com.example.eve.afcodetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    StringBuilder stringBuilder;
    RecyclerView mRecyclerView;
    private MerchandiseAdapter merchandiseAdapter;
    public ArrayList<MerchandiseInfo> merchandiseInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stringBuilder = new StringBuilder("https://www.abercrombie.com/anf/nativeapp/qa/codetest/codeTest_exploreData.json");


        mRecyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mRecyclerView.setHasFixedSize(true);

        merchandiseInfos = new ArrayList<>();
        merchandiseAdapter = new MerchandiseAdapter(merchandiseInfos, this);
        mRecyclerView.setAdapter(merchandiseAdapter);

        fetchMerchandise(stringBuilder.toString());
    }

    public void fetchMerchandise(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject josonObject = response.getJSONObject(i);
                        MerchandiseInfo merchandiseInfo = new MerchandiseInfo();
                        merchandiseInfo.setBackgroundImage(josonObject.getString("backgroundImage"));
                        merchandiseInfo.setTitle(josonObject.getString("title"));

                        if (josonObject.has("topDescription")) {
                            merchandiseInfo.setTopDescription(josonObject.getString("topDescription"));
                        } else {
                            merchandiseInfo.setTopDescription("");
                        }

                        if (josonObject.has("promoMessage")) {
                            merchandiseInfo.setPromoMessage(josonObject.getString("promoMessage"));
                        } else {
                            merchandiseInfo.setPromoMessage("");
                        }

                        if (josonObject.has("bottomDescription")) {
                            merchandiseInfo.setBottomDescription(josonObject.getString("bottomDescription"));
                        } else {
                            merchandiseInfo.setBottomDescription("");
                        }

                        if (josonObject.has("content")) {
                            JSONArray jsonContent = josonObject.getJSONArray("content");
                            for (int j = 0; j < jsonContent.length(); j++) {

                                JSONObject contentObject = jsonContent.getJSONObject(j);

                                if (j == 0) {
                                    merchandiseInfo.setTitleOne(contentObject.getString("title"));
                                    merchandiseInfo.setTitleTwo("");
                                    merchandiseInfo.setTargetOne(contentObject.getString("target"));
                                } else {
                                    merchandiseInfo.setTitleTwo(contentObject.getString("title"));
                                    merchandiseInfo.setTargetTwo(contentObject.getString("target"));
                                }

                            }

                        } else {
                            merchandiseInfo.setTitleTwo("");
                            merchandiseInfo.setTitleOne("");
                        }

                        merchandiseInfos.add(merchandiseInfo);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                merchandiseAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;

                //network status check
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }
}

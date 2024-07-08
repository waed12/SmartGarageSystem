package com.example.lastgarageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lastgarageapp.adapter.complainsAdapter;
import com.example.lastgarageapp.adapter.notificationAdapter;
import com.example.lastgarageapp.adapter.showdriverAdapter;
import com.example.lastgarageapp.itemClasses.complainsItem;
import com.example.lastgarageapp.itemClasses.notificationItem;
import com.example.lastgarageapp.itemClasses.showDriversItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class show_drivers extends AppCompatActivity {
    TextView named,source,destination;
    RecyclerView myRecyclerView;

    ArrayList<showDriversItem> shoewDriverarray = new ArrayList<>();
    showdriverAdapter ShowsDriverAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_drivers);




        myRecyclerView = findViewById(R.id.showdriver_recycler);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(show_drivers.this));



        String url = url_serverName.serverName + "showDrivers.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("drivers");
                    showDriversItem myItem;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String textName, String textNews, String textHour
                        String name = reader.getString("name");
                        String source=reader.getString("sour");
                        String destination=reader.getString("dest");
                        String id=reader.getString("id");
                        myItem = new showDriversItem(name,source,destination,id);

                        shoewDriverarray.add(myItem);
                    }
                    ShowsDriverAdapter = new showdriverAdapter(show_drivers.this,shoewDriverarray);
                    myRecyclerView.setAdapter(ShowsDriverAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        my_singleton.getInstance(show_drivers.this).addToRequestQueue(myStringRequest);



    }
}
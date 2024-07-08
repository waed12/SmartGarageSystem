package com.example.lastgarageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lastgarageapp.adapter.complainsAdapter;
import com.example.lastgarageapp.itemClasses.complainsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class read_complaints extends AppCompatActivity {

    RecyclerView myRecyclerView;
    ArrayList<complainsItem> myComplaintsArr = new ArrayList<>();
    complainsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_complaints);
        myRecyclerView = findViewById(R.id.complains_recyview);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(read_complaints.this));

        String url = url_serverName.serverName + "showComplaints.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("complaints");
                    complainsItem myItem;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject reader = jsonArray.getJSONObject(i);
                        String text = reader.getString("text");
                        myItem = new complainsItem(text);

                        myComplaintsArr.add(myItem);
                    }
                    adapter = new complainsAdapter(read_complaints.this, myComplaintsArr);
                    myRecyclerView.setAdapter(adapter);
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
            my_singleton.getInstance(read_complaints.this).addToRequestQueue(myStringRequest);
    }
}
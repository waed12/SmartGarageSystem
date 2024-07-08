package com.example.lastgarageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lastgarageapp.adapter.notificationAdapter;
import com.example.lastgarageapp.itemClasses.notificationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class notifications extends AppCompatActivity {

    ImageView homeIcon,notificationIcon,personalIcon,messagesIcon,menuIcon;
    RecyclerView myRecyclerView;

    ArrayList<notificationItem> myNotification = new ArrayList<>();
    notificationAdapter myNotificationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //views in my actionbarPage
        homeIcon = findViewById(R.id.myActionBar_homeIcon);
        notificationIcon = findViewById(R.id.myActionBar_notificationsIcon);
        personalIcon = findViewById(R.id.myActionBar_personIcon);
        messagesIcon = findViewById(R.id.myActionBar_messagesIcon);
        menuIcon = findViewById(R.id.myActionBar_menuIcon);

        myRecyclerView = findViewById(R.id.notoRecyView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(notifications.this));

        String url = url_serverName.serverName + "showNotifications.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("notification");
                    notificationItem myItem;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String textName, String textNews, String textHour
                        String name = reader.getString("name");
                        String time = reader.getString("time");
                        String newsId = reader.getString("news_id");
                        myItem = new notificationItem(name, time,newsId);

                        myNotification.add(myItem);
                    }
                    myNotificationAdapter = new notificationAdapter(notifications.this, myNotification);
                    myRecyclerView.setAdapter(myNotificationAdapter);
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
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> myMap = new HashMap<>();
                if(login.s_id!=null){
                    myMap.put("s_id", login.s_id);
                }else{
                    myMap.put("s_id", "null");
                }
                return myMap;
            }
        };
        my_singleton.getInstance(notifications.this).addToRequestQueue(myStringRequest);

        notificationIcon.setBackgroundColor(Color.WHITE);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notifications.this, home_page.class);
                startActivity(intent);
            }
        });
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notifications.this, notifications.class);
                startActivity(intent);
            }
        });
        if(login.s_id!=null) {
            personalIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isAdminOrDriver();
                }
            });
            messagesIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(notifications.this, chats.class);
                    startActivity(intent);
                }
            });
        }
        else{
            personalIcon.setVisibility(View.GONE);
            messagesIcon.setVisibility(View.GONE);
        }

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notifications.this, menu.class);
                startActivity(intent);
            }
        });
    }
    //
    private void isAdminOrDriver() {
        String url = url_serverName.serverName + "isAdminOrDriver.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("A_D");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);
                        String check = reader.getString("check");
                        Intent intent;
                        if(check.equals("d")){
                            intent = new Intent(notifications.this, personal_page.class);
                        }else if(check.equals("a")){
                            intent = new Intent(notifications.this, admin_personal_page.class);
                        }else{
                            intent = new Intent(notifications.this, boss_personal_page.class);
                        }
                        startActivity(intent);

                    }
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
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> myMap = new HashMap<>();
                myMap.put("s_id", login.s_id);
                return myMap;
            }
        };
        my_singleton.getInstance(notifications.this).addToRequestQueue(myStringRequest);
    }
}

// عند اضافة خبر يتم عمل اشعار
package com.example.lastgarageapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lastgarageapp.adapter.notificationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_notification extends AppCompatActivity {

    ImageView homeIcon, notificationIcon, personalIcon, messagesIcon, menuIcon,delete_icon;
    TextView name, text, hour, news_id, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notification);

        name =findViewById(R.id.notiItems_newsItem_name);
        delete_icon =findViewById(R.id.notiItems_newsItem_delet);
        text =findViewById(R.id.notiItems_newsItem_text);
        hour=findViewById(R.id.notiItems_newsItem_hour);
        news_id=findViewById(R.id.notificationItem_newsId);
        id =findViewById(R.id.notiItems_newsItem_id);

        showNoti_News(notificationAdapter.myNewsId);

        delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view_notification.this);
                alert.setTitle("تأكيد الحذف");
                alert.setMessage("هل تريد تأكيد الحذف؟");

                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String url = url_serverName.serverName + "deleteIconnews.php";
                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(view_notification.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(view_notification.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("n_id", notificationAdapter.myNewsId);
                                return params;
                            }
                        };
                        my_singleton.getInstance(view_notification.this).addToRequestQueue(stringRequest2);
                    }
                });
                alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create().show();
            }
        });

        //actionbar
        homeIcon = findViewById(R.id.myActionBar_homeIcon);
        notificationIcon = findViewById(R.id.myActionBar_notificationsIcon);
        personalIcon = findViewById(R.id.myActionBar_personIcon);
        messagesIcon = findViewById(R.id.myActionBar_messagesIcon);
        menuIcon = findViewById(R.id.myActionBar_menuIcon);
        notificationIcon.setBackgroundColor(Color.WHITE);

        if(login.s_id==null) {
            personalIcon.setVisibility(View.GONE);
            messagesIcon.setVisibility(View.GONE);
        }
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view_notification.this, home_page.class);
                startActivity(intent);
            }
        });
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view_notification.this, notifications.class);
                startActivity(intent);
            }
        });
        personalIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAdminOrDriver();
            }
        });
        messagesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view_notification.this, chats.class);
                startActivity(intent);
            }
        });

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view_notification.this, menu.class);
                startActivity(intent);
            }
        });
    }
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
                        if(check.equals("d")){
                            Intent intent = new Intent(view_notification.this, personal_page.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(view_notification.this, admin_personal_page.class);
                            startActivity(intent);
                        }

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
        my_singleton.getInstance(view_notification.this).addToRequestQueue(myStringRequest);
    }
    private void showNoti_News(String news_id){
        String url = url_serverName.serverName + "showNewsNotification.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("news");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject reader = jsonArray.getJSONObject(i);

                        id.setText(reader.getString("id"));
                        name.setText(reader.getString("name"));
                        text.setText(reader.getString("text"));
                        hour.setText(reader.getString("time"));

                        if(id.getText().toString().equals(login.myUser_id)){
                            delete_icon.setVisibility(View.VISIBLE);
                        }else{
                            delete_icon.setVisibility(View.GONE);
                        }

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
                myMap.put("news_id", news_id);
                return myMap;
            }
        };
        my_singleton.getInstance(view_notification.this).addToRequestQueue(myStringRequest);
    }
}

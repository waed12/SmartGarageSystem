package com.example.lastgarageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class View_Boss extends AppCompatActivity {
    TextView u_job, u_id,u_name, u_city, u_phone, u_garage;

    ImageView textMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_boss);

        textMessage = findViewById(R.id.ViewBoss_messageIcon);
        u_id = (TextView) findViewById(R.id.ViewBoss_idVal);
        u_name = (TextView) findViewById(R.id.ViewBoss_name);
        u_city = findViewById(R.id.ViewBoss_placeVal);
        u_phone = findViewById(R.id.ViewBoss_phoneNumVal);
        u_job = findViewById(R.id.ViewBoss_namejob);

        u_job.setText("المسؤول");

        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");
        u_id.setText(str);

        if(login.s_id==null){
            textMessage.setVisibility(View.GONE);
        }
        selectBossData();
        textMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View_Boss.this, conversation.class);
                intent.putExtra("chat_id", "");
                intent.putExtra("receiver_id", u_id.getText().toString());
                intent.putExtra("receiver_name", u_name.getText().toString());

                startActivity(intent);
            }
        });
    }

    private void selectBossData() {
        String url = url_serverName.serverName + "view_boss.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("personal_boss");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        String id = reader.getString("id");
                        String name = reader.getString("name");
                        String phone = reader.getString("phone");
                        String city = reader.getString("city");

                        u_id.setText(id);
                        u_name.setText(name);
                        u_phone.setText(phone);
                        u_city.setText(city);
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> myMap = new HashMap<>();
                myMap.put("boss_id", u_id.getText().toString());
                return myMap;
            }
        };
        my_singleton.getInstance(View_Boss.this).addToRequestQueue(myStringRequest);

    }
}

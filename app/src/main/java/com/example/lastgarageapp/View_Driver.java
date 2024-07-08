package com.example.lastgarageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lastgarageapp.adapter.showdriverAdapter;
import com.example.lastgarageapp.itemClasses.notificationItem;
import com.example.lastgarageapp.itemClasses.showDriversItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class View_Driver extends AppCompatActivity {
    TextView u_id, u_name, u_city, u_phone, u_car_id, u_sour, u_dest,u_fun;

    ImageView textMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_driver);


        textMessage = findViewById(R.id.ViewDriverPersonalPage_messageIcon);
        u_id = (TextView) findViewById(R.id.ViewDriverPersonalPage_Id);
        u_name = (TextView) findViewById(R.id.ViewDriverPersonalPage_name);
        u_city = findViewById(R.id.ViewDriverPersonalPage_placeVal);
        u_phone = findViewById(R.id.ViewDriverPersonalPage_phoneNumVal);
        u_car_id = findViewById(R.id.ViewDriverPersonalPage_carNumVal);
        u_sour = findViewById(R.id.ViewDriverPersonalPage_sour);
        u_dest = findViewById(R.id.ViewDriverPersonalPage_dest);
        u_fun=findViewById(R.id.ViewDriverPersonalPage_function);


        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");
        u_id.setText(str);

        if(login.s_id==null){
            textMessage.setVisibility(View.GONE);
        }

        selectdriverdata();

        textMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View_Driver.this, conversation.class);
                intent.putExtra("chat_id", "");
                intent.putExtra("receiver_id", u_id.getText().toString());
                intent.putExtra("receiver_name", u_name.getText().toString());

                startActivity(intent);
            }
        });

    }

    public void selectdriverdata() {
        String url = url_serverName.serverName + "view_driver.php";
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
                        String city=reader.getString("city");
                        String car=reader.getString("car");
                        String phone=reader.getString("phone");
                        String function=reader.getString("name_jop");

                        u_name.setText(name);
                        u_phone.setText(phone);
                        u_city.setText(city);
                        u_sour.setText(source);
                        u_dest.setText(destination);
                        u_car_id.setText(car);
                        u_fun.setText(function);


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
                myMap.put("u_id", u_id.getText().toString());
                return myMap;
            }
        };
        my_singleton.getInstance(View_Driver.this).addToRequestQueue(myStringRequest);

    }

}
package com.example.lastgarageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lastgarageapp.itemClasses.showDriversItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class View_Admin extends AppCompatActivity {
    TextView u_id, u_name, u_city, u_phone, u_fun,u_garage;
    ImageView textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admin);

        textMessage = findViewById(R.id.ViewAdminPersonalPage_messageIcon);
        u_id = (TextView) findViewById(R.id.ViewAdminPersonalPage_adminId);
        u_name = (TextView) findViewById(R.id.ViewAdminPersonalPage_name);
        u_city = findViewById(R.id.ViewAdminPersonalPage_placeVal);
        u_phone = findViewById(R.id.ViewAdminPersonalPage_phoneNumVal);
        u_fun=findViewById(R.id.ViewAdminPersonalPage_function);
        u_garage=findViewById(R.id.ViewAdminPersonalPage_workplaceVal);


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
                Intent intent = new Intent(View_Admin.this, conversation.class);
                intent.putExtra("chat_id", "");
                intent.putExtra("receiver_id", u_id.getText().toString());
                intent.putExtra("receiver_name", u_name.getText().toString());

                startActivity(intent);
            }
        });
    }
    public void selectdriverdata() {
        String url = url_serverName.serverName + "view_admin.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("personal_admin");
                    showDriversItem myItem;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String textName, String textNews, String textHour
                        String name = reader.getString("admin_name");
                        String city=reader.getString("city");
                        String phone=reader.getString("phone");
                        String garage = reader.getString("garage_name");
                        String function=reader.getString("function");

                        u_name.setText(name);
                        u_phone.setText(phone);
                        u_city.setText(city);
                        u_fun.setText(function);
                        u_garage.setText(garage);


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
                myMap.put("a_id", u_id.getText().toString());
                return myMap;
            }
        };
        my_singleton.getInstance(View_Admin.this).addToRequestQueue(myStringRequest);

    }
}
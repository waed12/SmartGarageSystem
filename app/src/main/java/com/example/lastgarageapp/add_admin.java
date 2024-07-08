package com.example.lastgarageapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class add_admin extends AppCompatActivity {
  private TextView name,city,identity_no,phone_no,pass;
  private Button addAdmin_add,addAdmin_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        addAdmin_add=(Button) findViewById(R.id.addAdmin_addButton);
        addAdmin_cancel = (Button) findViewById(R.id.addAdmin_cancelButton);

        name = findViewById(R.id.addAdmin_nameValue);
        pass = findViewById(R.id.addAdmin_passwordValue);
        city = findViewById(R.id.addAdmin__cityValue);
        identity_no= findViewById(R.id.addAdmin_idValue);
        phone_no=findViewById(R.id.addAdmin_phoneValue);

//1
        addAdmin_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(add_admin.this);
                alert.setTitle("إضافة مسؤول جديد");
                alert.setMessage("هل تريد إضافة المسؤول الجديد؟");
                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                if(name.getText().length()==0||identity_no.getText().length()==0||city.getText().length()==0||phone_no.getText().length()==0||pass.getText().length()==0){
                    Toast.makeText(getBaseContext(), "قم بإدخال جميع البيانات", Toast.LENGTH_SHORT).show();
                }else{
                    String url = url_serverName.serverName + "addAdmin.php";
                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", identity_no.getText().toString());
                            params.put("user_name", name.getText().toString());
                            params.put("password", pass.getText().toString());
                            params.put("city", city.getText().toString());
                            params.put("phone_no", phone_no.getText().toString());
                      //      params.put("garage_name",garageName.getSelectedItem().toString());

                            return params;
                        }
                    };
                    my_singleton.getInstance(add_admin.this).addToRequestQueue(stringRequest2);
                   // Toast.makeText(add_admin.this, "تمت الإضافة",Toast.LENGTH_SHORT).show();

                }
            }

        });
                alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        //  Toast.makeText(edit_personal_data.this, "تم التراجع",Toast.LENGTH_SHORT).show();
                    }
                });alert.create().show();

            }
        });
        addAdmin_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    }

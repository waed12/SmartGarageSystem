package com.example.lastgarageapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class reset_password extends AppCompatActivity {
    EditText phoneNum,id;
    Button Send;
///
    //recieve from php
    String pass;

    //send to php
    String Phon_num;

    String SMS="";
    String s="";

    boolean singleClick=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_password);

        id=(EditText) findViewById(R.id.resetPass_user_id);
        phoneNum=(EditText) findViewById(R.id.resetPass_phoneNumber);
        Send=(Button)findViewById(R.id.resetPass_resetPassword);
        //reset(id.getText().toString());




        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(singleClick) {
                    singleClick = false;
////123
                    String idUser = id.getText().toString();
                    String tex_phone = phoneNum.getText().toString();
                    if (TextUtils.isEmpty(idUser) || TextUtils.isEmpty(tex_phone)) {
                        Toast.makeText(reset_password.this, "قم بإدخال جميع البيانات", Toast.LENGTH_SHORT).show();
                    } else {

                        AlertDialog.Builder alert = new AlertDialog.Builder(reset_password.this);
                        String id1 = id.getText().toString();
                        String uRl = url_serverName.serverName +"selectPhoneNum.php";
                        StringRequest myStringRequest = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject object = new JSONObject(response);
                                    JSONArray jsonArray = object.getJSONArray("phone");

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject reader = jsonArray.getJSONObject(i);
                                        s = reader.getString("phone_number");
                                        // Toast.makeText(getBaseContext(), pass, Toast.LENGTH_SHORT).show();


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(reset_password.this, error.toString(), Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("user_id",id1);
                                //   param.put("pass", password);
                                return param;

                            }
                        };
                        my_singleton.getInstance(reset_password.this).addToRequestQueue(myStringRequest);
                        alert.setTitle("تأكيد تسجيل الخروج");
                        alert.setMessage("هل تريد تأكيد تسجيل الخروج؟"+""+ s);
                        alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                                        sendSMS(idUser);
                                    }
                                    else {
                                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                                    }
                                }

                            }


                        });
                        alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alert.create().show();
                    }

                      //  Toast.makeText(getBaseContext(), passe.getText().toString(), Toast.LENGTH_SHORT).show();

                    }
                }

        });

    }


    private void sendSMS(final String id){
        Phon_num=phoneNum.getText().toString();
        String uRl = url_serverName.serverName +"resetPass.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("passwords");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject reader = jsonArray.getJSONObject(i);
                        pass = reader.getString("pass");
                       // Toast.makeText(getBaseContext(), pass, Toast.LENGTH_SHORT).show();
                        SMS="الرقم السري الخاص بك " + "" + pass;
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Phon_num, null, SMS, null, null);
                            Toast.makeText(getBaseContext(), "تم ارسال الرسالة", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getBaseContext(), "فشل الارسال", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(reset_password.this, error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("user_id", id);
                //   param.put("pass", password);
                return param;

            }
        };
        my_singleton.getInstance(reset_password.this).addToRequestQueue(myStringRequest);

      //  String SMS="الرقم السري الخاص بك " + "" + pass;
      //  Toast.makeText(this, SMS, Toast.LENGTH_SHORT).show();


    }

 //   public void reset(final String id) {

        //Toast.makeText(getBaseContext(), id + pass, Toast.LENGTH_SHORT).show();
    // return pass;

   // }


}

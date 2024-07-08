package com.example.lastgarageapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class login extends AppCompatActivity {
    private EditText idNumber, password;
    private Button loginClient;
    private Button loginAdmin;
    private TextView forget;
    public static String myUser_id;
    public static String s_id=null;
    String phon = "";
    String pass="";
    String SMS="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        idNumber = findViewById(R.id.login_idNumber);
        password = findViewById(R.id.login_password);
        loginAdmin = findViewById(R.id.login_loginAdmin);
        loginClient = findViewById(R.id.login_loginClient);
        forget = findViewById(R.id.login_forgetPass);

//        Log.e("",login.myUser_id+"hooooo");
        loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idUser = idNumber.getText().toString();
                String tex_password = password.getText().toString();
                if (idUser.isEmpty() || tex_password.isEmpty()) {
                    Toast.makeText(login.this, "قم بإدخال جميع البيانات", Toast.LENGTH_SHORT).show();
                } else {
                    login(idUser, tex_password);
                }
            }


        });

        loginClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, home_page.class));
                s_id = null;
                finish();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idUser = idNumber.getText().toString();
                if (TextUtils.isEmpty(idUser)) {
                    Toast.makeText(login.this, "قم بإدخال رقم الهوية", Toast.LENGTH_SHORT).show();
                } else {

                    String uRl = url_serverName.serverName + "selectPhoneNum.php";
                    StringRequest myStringRequest = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray jsonArray = object.getJSONArray("phone");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject reader = jsonArray.getJSONObject(i);
                                    phon = reader.getString("phone_number");

                                    // phon="0"+phon;
                                    AlertDialog.Builder alert = new AlertDialog.Builder(login.this);
                                    alert.setTitle("استرجاع كلمة السر ");
                                    alert.setMessage("هل تريد ارسال الرقم السري على رقم الجوال " + "" +
                                            phon.substring(phon.length()-3,phon.length())+"****"+phon.substring(0,3));

                                    alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            //  Toast.makeText(getBaseContext(), passe.getText().toString(), Toast.LENGTH_SHORT).show();
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
                                    alert.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    alert.create().show();


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(login.this, error.toString(), Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> param = new HashMap<>();
                            param.put("user_id", idUser);
                            //   param.put("pass", password);
                            return param;

                        }
                    };
                    my_singleton.getInstance(login.this).addToRequestQueue(myStringRequest);

                }
            }

        });
    }
///////////

    public void login(final String id, final String password) {
        String uRl = url_serverName.serverName + "login1.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("login");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject reader = jsonArray.getJSONObject(i);
                        s_id = reader.getString("s_id");
                        if(s_id.equals("null")){
                            Toast.makeText(getBaseContext(), "خطأ في رقم الهوية أو الرقم السري", Toast.LENGTH_SHORT).show();
                        }else{
                        startActivity(new Intent(login.this, home_page.class));
                        login.myUser_id = idNumber.getText().toString();}

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this, error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("userId", id);
                param.put("pass", password);
                return param;

            }
        };

        my_singleton.getInstance(login.this).addToRequestQueue(myStringRequest);
    }


    private void sendSMS(final String id) {
        String uRl = url_serverName.serverName + "resetPass.php";
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
                        SMS = "الرقم السري الخاص بك " + "" + pass;
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phon, null, SMS, null, null);
                    //       Toast.makeText(getBaseContext(), SMS, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(login.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        my_singleton.getInstance(login.this).addToRequestQueue(myStringRequest);
    }
}








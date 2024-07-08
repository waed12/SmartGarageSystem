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
import com.example.lastgarageapp.itemClasses.notificationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class edit_admin_personal_data extends AppCompatActivity {
    private Button editAdminPersonalData_cancel, editAdminPersonalData_save;
    TextView u_name,u_city,u_phone,garageName;

    ArrayList garage_name_array =new ArrayList();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_personal_data);

        editAdminPersonalData_save = (Button) findViewById(R.id.editAdminPersonalData_savechange);
        u_name=findViewById(R.id.editAdminPersonalData_nameVal);
        u_city=findViewById(R.id.editAdminPersonalData_cityVal);
        u_phone=findViewById(R.id.editAdminPersonalData_phoneNumVal);
        garageName=findViewById(R.id.editAdminPersonalData_garageVal);


        selectadmineditPersonaldata();

        editAdminPersonalData_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(edit_admin_personal_data.this);
                alert.setTitle("حفظ التغيرات");
                alert.setMessage("هل تريد حفظ البيانات الجديدة؟");
                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    String url = url_serverName.serverName+"editadminPersonaldata.php";
                    StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

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
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> myMap = new HashMap<>();

                            myMap.put("name",u_name.getText().toString());
                            myMap.put("city",u_city.getText().toString());
                            myMap.put("phone",u_phone.getText().toString());
                            myMap.put("s_id", login.s_id);
                            return myMap;
                        }
                    };
                    my_singleton.getInstance(edit_admin_personal_data.this).addToRequestQueue(myStringRequest);
           //             Toast.makeText(edit_admin_personal_data.this, "تم الحفظ",Toast.LENGTH_SHORT).show();
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

        editAdminPersonalData_cancel = (Button) findViewById(R.id.editAdminPersonalData_cancel);

        editAdminPersonalData_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }
//1
    public void selectadmineditPersonaldata() {
        String url = url_serverName.serverName + "selectadmineditPersonaldata.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //  Log.d("sss",response);
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("edit_admin_personal");
                    notificationItem myItem;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String textName, String textNews, String textHour
                        String name = reader.getString("admin_name");
                        String phone = reader.getString("phone");
                        String city = reader.getString("city");
                        String garage_name = reader.getString("garage_name");

                        u_name.setText(name);
                        u_phone.setText(phone);
                        u_city.setText(city);
                        garageName.setText(garage_name);

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
                myMap.put("s_id", login.s_id);
                return myMap;
            }
        };
        my_singleton.getInstance(edit_admin_personal_data.this).addToRequestQueue(myStringRequest);

    }

}
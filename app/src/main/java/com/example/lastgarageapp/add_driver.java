package com.example.lastgarageapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class add_driver extends AppCompatActivity {

    private Button addDriver_cancel, addDriver_add;

    private EditText name,password,city,identity_number,PhoneNumb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        addDriver_add=findViewById(R.id.addDriver_add);
        addDriver_cancel = (Button) findViewById(R.id.addDriver_cancel);

        name = findViewById(R.id.addDriver_driverNameVal);
        password = findViewById(R.id.addDriver_passVal);
        city = findViewById(R.id.addDriver_cityVal);
        identity_number = findViewById(R.id.addDriver_identityNumVal);
        PhoneNumb=findViewById(R.id.addDriver_phoneNumVal);

        addDriver_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(add_driver.this);
                alert.setTitle("إضافة سائق جديد");
                alert.setMessage("هل تريد إضافة سائق جديد؟");
                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                if(name.getText().length()==0||identity_number.getText().length()==0||city.getText().length()==0|PhoneNumb.getText().length()==0|password.getText().length()==0) {
                    Toast.makeText(getBaseContext(), "قم بإدخال جميع البيانات", Toast.LENGTH_SHORT).show();
                }else{
                    String url = url_serverName.serverName + "addDriver.php";
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
                            params.put("user_id", identity_number.getText().toString());
                            params.put("user_name", name.getText().toString());
                            params.put("password", password.getText().toString());
                            params.put("city", city.getText().toString());
                            params.put("phone_no", PhoneNumb.getText().toString());

                            return params;
                        }
                    };
                    my_singleton.getInstance(add_driver.this).addToRequestQueue(stringRequest2);
                    //Toast.makeText(add_driver.this, "تمت الإضافة",Toast.LENGTH_SHORT).show();

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
        addDriver_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}

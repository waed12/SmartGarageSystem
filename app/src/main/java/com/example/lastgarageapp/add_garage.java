package com.example.lastgarageapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
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

public class add_garage extends AppCompatActivity {

    private EditText cityName,openHoure,closeHoure,location;
    private Button cancelButt,addButt;
    private Spinner adminSpinner, idSpinner;
    TimePickerDialog timePickerDialog;

    ArrayList adminName_arr =new ArrayList();
    ArrayList adminID_arr =new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garage);

        cityName= findViewById(R.id.addGarage_cityValue);
        openHoure= findViewById(R.id.addGarage_openHoureValue);
        closeHoure= findViewById(R.id.addGarage_closeHoureValue);
        location= findViewById(R.id.addGarage_locationValue);
        adminSpinner =findViewById(R.id.addGarage_adminSpinner);
        cancelButt = findViewById(R.id.addGarage_cancelButt);
        addButt= findViewById(R.id.addGarage_addButt);
        idSpinner= findViewById(R.id.addGarage_IDAdmin);

        adminNameSpinner();
        adminSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (!selectedItem.equals("لم يحدد")) {
                    idSpinner.setSelection(getIndexByString(adminSpinner,adminSpinner.getSelectedItem().toString()));
                }else{

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        addButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(add_garage.this);
                alert.setTitle("إضافة كراج جديد");
                alert.setMessage("هل تريد إضافة كراج جديد؟");
                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                if(cityName.getText().length()==0||openHoure.getText().length()==0||closeHoure.getText().length()==0||location.getText().length()==0){
                    Toast.makeText(getBaseContext(), "قم بإدخال جميع البيانات", Toast.LENGTH_SHORT).show();
                }else{
                    String url = url_serverName.serverName+"addGarage.php";
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

                            myMap.put("garage_name", getData(cityName));
                            myMap.put("admin_id", idSpinner.getSelectedItem().toString());
                            myMap.put("open_hour", removeSpaces(getData(openHoure)));
                            myMap.put("close_hour", removeSpaces(getData(closeHoure)));
                            myMap.put("location", getData(location));
                            return myMap;
                        }
                    };
                    my_singleton.getInstance(add_garage.this).addToRequestQueue(myStringRequest);
                 //   Toast.makeText(add_garage.this, "تمت الإضافة",Toast.LENGTH_SHORT).show();

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

        cancelButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        openHoure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(add_garage.this,R.style.Theme1_LastGarageApp, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        openHoure.setText(String.format("%02d : %02d " , hourOfDay, minute) );
                    }
                },00, 00, true );
                timePickerDialog.show();

            }
        });

        closeHoure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(add_garage.this,R.style.Theme1_LastGarageApp, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        closeHoure.setText(String.format("%02d : %02d " , hourOfDay, minute) );

                    }
                }, 00 ,00,true);
                timePickerDialog.show();
            } });
    }
    public int getIndexByString(Spinner spinner, String string) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(string)) {
                index = i;
                break;
            }
        }
        return index;
    }
    public String getData(EditText t){
        String myString =t.getText().toString();
        return myString;
    }

    static String removeSpaces(String myString) {
        char []str=myString.toCharArray();
        int count = 0;
        for (int i = 0; i<str.length; i++)
            if (str[i] != ' ')
                str[count++] = str[i];
       return (String) String.valueOf(str).subSequence(0, count);
    }
    private void adminNameSpinner() {
        adminName_arr.clear();
        adminName_arr.add(0,"لم يحدد");
        adminID_arr.clear();
        adminID_arr.add(0,"لم يحدد");

        String url = url_serverName.serverName + "adminSpinner.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("admins");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String cityName
                        String admin_name = reader.getString("admin_name");
                        String admin_id = reader.getString("admin_id");
                        adminName_arr.add(admin_name);
                        adminID_arr.add(admin_id);

                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(add_garage.this,android.R.layout.simple_spinner_item, adminName_arr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adminSpinner.setAdapter(adapter);
                    ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(add_garage.this,android.R.layout.simple_spinner_item, adminID_arr);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    idSpinner.setAdapter(adapter2);
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
        });
        my_singleton.getInstance(add_garage.this).addToRequestQueue(myStringRequest);
    }
}



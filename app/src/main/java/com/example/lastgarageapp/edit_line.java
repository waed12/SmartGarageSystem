package com.example.lastgarageapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lastgarageapp.itemClasses.carItem;
//
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class edit_line extends AppCompatActivity {


    private Button cancelButt, saveButt;
    private Spinner sour, dest;
    private EditText fare;
    private TextView noOfCar;
    public String d_or_a="";

    ArrayList source_array =new ArrayList();
    ArrayList destination_array =new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_line);

        cancelButt = findViewById(R.id.editLine_cancelButt);
        saveButt = findViewById(R.id.editLine_saveButt);
        sour = findViewById(R.id.editLine_source);
        dest = findViewById(R.id.editLine_destination);
        fare = findViewById(R.id.editLine_fare);
        noOfCar = findViewById(R.id.editLine_noOfCar);

        //default
        isAdminOrDriver();
        dest.setEnabled(false);

        sour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (!selectedItem.equals("المكان الحالي")) {
                    dest.setEnabled(true);
                    destinationSpinner();
                }else{
                    dest.setEnabled(false);
                    destinationSpinner();
                    dest.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        dest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (!selectedItem.equals("الوجهة")) {
                    selectData(sour.getSelectedItem().toString(),selectedItem);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        saveButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(edit_line.this);
                alert.setTitle("حفظ التغيرات");
                alert.setMessage("هل تريد حفظ البيانات الجديدة؟");
                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                String myFare=getData(fare);
                if(fare.getText().length()==0||sour.getSelectedItem().equals("المكان الحالي")){
                    Toast.makeText(getBaseContext(), "قم بإدخال جميع البيانات", Toast.LENGTH_SHORT).show();
                }else{
                    String url = url_serverName.serverName+"editLine.php";
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

                            myMap.put("garage1_name", sour.getSelectedItem().toString());
                            myMap.put("garage2_name", dest.getSelectedItem().toString());
                            myMap.put("fare", myFare);
                            return myMap;
                        }
                    };
                    my_singleton.getInstance(edit_line.this).addToRequestQueue(myStringRequest);
                   // Toast.makeText(edit_line.this, "تم الحفظ",Toast.LENGTH_SHORT).show();

                }fare.setText("");
                    }
                    });

                 alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
finish();
                          //  Toast.makeText(edit_line.this, "تم التراجع",Toast.LENGTH_SHORT).show();
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
                        setCheck(check);
                        if(d_or_a.equals("a")){
                            sourceSpinner_admin();
                        }else{
                            sourceSpinner();
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
        my_singleton.getInstance(edit_line.this).addToRequestQueue(myStringRequest);
    }
    private void setCheck(String check){
        d_or_a= check;
    }

    public String getData(EditText t){
        String myString =t.getText().toString();
        return myString;
    }
    public void sourceSpinner() {
        source_array.clear();
        source_array.add(0,"المكان الحالي");

        String url = url_serverName.serverName + "sourceSpinner.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("garages");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String cityName
                        String cityName = reader.getString("garage_name");
                        source_array.add(cityName);

                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_line.this,android.R.layout.simple_spinner_item, source_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sour.setAdapter(adapter);
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
        my_singleton.getInstance(edit_line.this).addToRequestQueue(myStringRequest);
    }
    private void sourceSpinner_admin() {
        //if admin
        source_array.clear();
        source_array.add(0,"المكان الحالي");

        String url = url_serverName.serverName + "adminPersonalpage.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("personal_admin");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        String garage_name = reader.getString("garage_name");

                        source_array.add(garage_name);

                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_line.this,android.R.layout.simple_spinner_item, source_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sour.setAdapter(adapter);
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
        my_singleton.getInstance(edit_line.this).addToRequestQueue(myStringRequest);
    }
    public void destinationSpinner() {
        destination_array.clear();
        destination_array.add(0,"الوجهة");

        String url = url_serverName.serverName + "destinationSpinner.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("garages");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String cityName
                        String cityName = reader.getString("garage_name");
                        destination_array.add(cityName);

                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_line.this,android.R.layout.simple_spinner_item, destination_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dest.setAdapter(adapter);
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
                myMap.put("garage_name", sour.getSelectedItem().toString());
                return myMap;
            }
        };
        my_singleton.getInstance(edit_line.this).addToRequestQueue(myStringRequest);
    }
    public void selectData(String garage1, String garage2) {

        String url = url_serverName.serverName + "editLineSelection.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("lines");
                    carItem myItem;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        String f = reader.getString("fare");
                        String no_of_car = reader.getString("no_of_cars");
                        fare.setText(f);
                        noOfCar.setText(no_of_car);
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
                myMap.put("garage1_name", garage1);
                myMap.put("garage2_name", garage2);
                return myMap;
            }
        };
        my_singleton.getInstance(edit_line.this).addToRequestQueue(myStringRequest);
    }}




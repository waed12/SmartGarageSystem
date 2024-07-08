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

public class add_car extends AppCompatActivity {
    private TextView car_no;
    private Spinner driver_name,driver_id,capacity,sour,dest;
    private Button addCar_add,addCar_cancel;

    //Arrays
    ArrayList source_array =new ArrayList();
    ArrayList destination_array =new ArrayList();
    ArrayList name_array =new ArrayList();
    ArrayList driver_array =new ArrayList();
    ArrayList capacity_array =new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        car_no = findViewById(R.id.addCar_carNumVal);
        driver_name=(Spinner) findViewById(R.id.addCar_namedriverval);
        driver_id=(Spinner) findViewById(R.id.addCar_Iddriverval);
        capacity=(Spinner) findViewById(R.id.addCar_sizeVal);
        sour=(Spinner) findViewById(R.id.addCar_source);
        dest=(Spinner) findViewById(R.id.addCar_destination);
        addCar_cancel = (Button) findViewById(R.id.addCar_cancel);
        addCar_add= (Button) findViewById(R.id.addCar_add);
        capacity_array.add(4);
        capacity_array.add(7);


        driverSpinner();
        isAdminOrBoss();
        dest.setEnabled(false);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(add_car.this,android.R.layout.simple_spinner_item, capacity_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capacity.setAdapter(adapter);

        driver_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (!selectedItem.equals("لم يحدد")) {
                    driver_id.setSelection(getIndexByString(driver_name,driver_name.getSelectedItem().toString()));
                }else{

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        driver_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        capacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



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
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        addCar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(add_car.this);
                alert.setTitle("إضافة سيارة جديدة");
                alert.setMessage("هل تريد إضافة سيارة جديدة؟");
                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (car_no.getText().length() == 0||driver_name.getSelectedItem().toString().equals("لم يحدد")||sour.getSelectedItem().toString().equals("المكان الحالي")||dest.getSelectedItem().toString().equals("الوجهة")) {
                            Toast.makeText(getBaseContext(), "قم بإدخال جميع البيانات", Toast.LENGTH_SHORT).show();
                        } else {
                            String url = url_serverName.serverName + "addCar.php";
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

                                    params.put("car_id", car_no.getText().toString());
                                    params.put("driver_name", driver_name.getSelectedItem().toString());
                                    params.put("driver_id", driver_id.getSelectedItem().toString());
                                    params.put("car_size", capacity.getSelectedItem().toString());
                                    params.put("source", sour.getSelectedItem().toString());
                                    params.put("destination", dest.getSelectedItem().toString());

                                    return params;
                                }
                            };
                            my_singleton.getInstance(add_car.this).addToRequestQueue(stringRequest2);
                            //  Toast.makeText(add_car.this, "تمت الإضافة",Toast.LENGTH_SHORT).show();

                        }
                    }

                });
                alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        //  Toast.makeText(edit_personal_data.this, "تم التراجع",Toast.LENGTH_SHORT).show();
                    }
                });
                alert.create().show();

            }
        });



        addCar_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();            }
        });
    }

    private void isAdminOrBoss() {
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
                        if(check.equals("a")){
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
        my_singleton.getInstance(add_car.this).addToRequestQueue(myStringRequest);
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
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(add_car.this,android.R.layout.simple_spinner_item, source_array);
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
        my_singleton.getInstance(add_car.this).addToRequestQueue(myStringRequest);
    }
    public void driverSpinner() {

        name_array.add(0,"لم يحدد");
        driver_array.add(0,"لم يحدد");


        String url = url_serverName.serverName + "nameSpinner.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("names");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        String driverName = reader.getString("driver_name");
                        String driverId = reader.getString("driver_id");

                        name_array.add(driverName);
                        driver_array.add(driverId);



                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(add_car.this,android.R.layout.simple_spinner_item, name_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    driver_name.setAdapter(adapter);
                    ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter(add_car.this,android.R.layout.simple_spinner_item, driver_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    driver_id.setAdapter(adapter1);
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
        my_singleton.getInstance(add_car.this).addToRequestQueue(myStringRequest);
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
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(add_car.this,android.R.layout.simple_spinner_item, source_array);
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
        my_singleton.getInstance(add_car.this).addToRequestQueue(myStringRequest);
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
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(add_car.this,android.R.layout.simple_spinner_item, destination_array);
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
        my_singleton.getInstance(add_car.this).addToRequestQueue(myStringRequest);
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

    }







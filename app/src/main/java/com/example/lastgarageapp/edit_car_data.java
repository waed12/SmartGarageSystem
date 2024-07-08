package com.example.lastgarageapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class edit_car_data extends AppCompatActivity {

    private Button cancel, saveChange;
    private Spinner car_no,driver_name,sour,dest,new_sour,new_dest, driver_id;

    // Arrays
    ArrayList car_array =new ArrayList();
    ArrayList source_array =new ArrayList();
    ArrayList destination_array =new ArrayList();
    ArrayList name_array =new ArrayList();
    ArrayList id_array =new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car_data);
        cancel = (Button) findViewById(R.id.editGarage_cancel);
        saveChange = (Button) findViewById(R.id.editCar_saveChange);
        car_no = (Spinner) findViewById(R.id.editCarData_carNumval);
        driver_name = (Spinner) findViewById(R.id.editCarData_nameDriverVal);
        sour = (Spinner) findViewById(R.id.editCarData_source1);
        dest = (Spinner) findViewById(R.id.editCarData_destination1);
        new_sour = (Spinner) findViewById(R.id.editCarData_source);
        new_dest = (Spinner) findViewById(R.id.editCarData_destination);
        driver_id = (Spinner) findViewById(R.id.editCar_IDDriver);

        dest.setEnabled(false);
        car_no.setEnabled(false);
        new_sour.setEnabled(false);
        new_dest.setEnabled(false);
        driver_name.setEnabled(false);

        isAdminOrDriver();
        driverNameSpinner();

        sour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (!selectedItem.equals("المكان الحالي")) {
                    dest.setEnabled(true);
                    destinationSpinner();
                }else{
                    dest.setEnabled(false);
                    car_no.setEnabled(false);
                    new_sour.setEnabled(false);
                    new_dest.setEnabled(false);
                    driver_name.setEnabled(false);

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
                    car_no.setEnabled(true);
                    carSpinner();
                }else{
                    car_no.setEnabled(false);
                    new_sour.setEnabled(false);
                    new_dest.setEnabled(false);
                    driver_name.setEnabled(false);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        car_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (!selectedItem.equals("لم يحدد")) {
                    new_sour.setEnabled(true);
                    new_dest.setEnabled(true);
                    driver_name.setEnabled(true);
                    selectCarData();
                }else{
                    new_sour.setEnabled(false);
                    new_dest.setEnabled(false);
                    driver_name.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(edit_car_data.this);
                alert.setTitle("حفظ التغيرات");
                alert.setMessage("هل تريد حفظ البيانات الجديدة؟");
                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = url_serverName.serverName+"editCardata.php";
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

                                myMap.put("car_no",car_no.getSelectedItem().toString());
                                myMap.put("driver_id",driver_id.getSelectedItem().toString());
                                myMap.put("source",sour.getSelectedItem().toString());
                                myMap.put("destination",dest.getSelectedItem().toString());
                                return myMap;
                            }
                        };
                        my_singleton.getInstance(edit_car_data.this).addToRequestQueue(myStringRequest);
                      //  Toast.makeText(edit_car_data.this, "تم الحفظ",Toast.LENGTH_SHORT).show();
                    }
                });
                //alert.create().show();

               //     Toast.makeText(getBaseContext(), "قم بإدخال جميع البيانات", Toast.LENGTH_SHORT).show();

                alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            finish();
                        //Toast.makeText(edit_car_data.this, "تم التراجع",Toast.LENGTH_SHORT).show();
                    }
                });alert.create().show();

            }
        });
//        selectCarData();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();            }
        });

        driver_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (!selectedItem.equals("لم يحدد")) {
                    driver_id.setSelection(getIndexByString(driver_name,driver_name.getSelectedItem().toString()));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        new_sour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        new_dest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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
        my_singleton.getInstance(edit_car_data.this).addToRequestQueue(myStringRequest);
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
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_car_data.this,android.R.layout.simple_spinner_item, source_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sour.setAdapter(adapter);
                    new_sour.setAdapter(adapter);

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
        my_singleton.getInstance(edit_car_data.this).addToRequestQueue(myStringRequest);
    }
    public void driverNameSpinner() {

        name_array.add(0,"لم يحدد");
        id_array.add(0,"لم يحدد");

        String url = url_serverName.serverName + "nameSpinner.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("names");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String cityName
                        String driverName = reader.getString("driver_name");
                        String driverID = reader.getString("driver_id");
                        name_array.add(driverName);
                        id_array.add(driverID);

                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_car_data.this,android.R.layout.simple_spinner_item, name_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    driver_name.setAdapter(adapter);
                    ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(edit_car_data.this,android.R.layout.simple_spinner_item, id_array);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    driver_id.setAdapter(adapter2);
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
        my_singleton.getInstance(edit_car_data.this).addToRequestQueue(myStringRequest);
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
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_car_data.this,android.R.layout.simple_spinner_item, source_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sour.setAdapter(adapter);
                    new_sour.setAdapter(adapter);
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
        my_singleton.getInstance(edit_car_data.this).addToRequestQueue(myStringRequest);
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
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_car_data.this,android.R.layout.simple_spinner_item, destination_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dest.setAdapter(adapter);
                    new_dest.setAdapter(adapter);
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
        my_singleton.getInstance(edit_car_data.this).addToRequestQueue(myStringRequest);
    }

    public void carSpinner() {
        car_array.clear();
        car_array.add(0,"لم يحدد");

        String url = url_serverName.serverName + "carSpinner.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("cars");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String cityName
                        String carId = reader.getString("car_id");
                        car_array.add(carId);

                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_car_data.this,android.R.layout.simple_spinner_item, car_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    car_no.setAdapter(adapter);
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
                myMap.put("sour", sour.getSelectedItem().toString());
                myMap.put("dest",dest.getSelectedItem().toString());
                return myMap;
            }
        };
        my_singleton.getInstance(edit_car_data.this).addToRequestQueue(myStringRequest);
    }

    public void selectCarData() {

        String url = url_serverName.serverName + "selecteditCardata.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("drivers");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        String name = reader.getString("name");
                        String source = reader.getString("sour");
                        String destination = reader.getString("dest");

                        if(source.equals(sour.getSelectedItem().toString())){
                            new_sour.setSelection(getIndexByString(new_sour,source));
                            new_dest.setSelection(getIndexByString(new_dest,destination));
                        }else{
                            new_sour.setSelection(getIndexByString(new_sour,destination));
                            new_dest.setSelection(getIndexByString(new_dest,source));
                        }
                        driver_name.setSelection(getIndexByString(driver_name,name));


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
                myMap.put("car_id", car_no.getSelectedItem().toString());
                return myMap;
            }
        };
        my_singleton.getInstance(edit_car_data.this).addToRequestQueue(myStringRequest);
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

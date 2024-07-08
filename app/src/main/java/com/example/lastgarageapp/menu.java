package com.example.lastgarageapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lastgarageapp.itemClasses.lineItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class menu extends AppCompatActivity {
    TextView personalpage,personalInfo,
            addDriver,addManager, addnewcar, addnewline, addnewgarage,
            editlinedata,editgaragedata,editcardata,
            showDrivers, readComplains,writecomplains,
            aboutapp, settings,logout,login1;
    TextView other,add,edit;
    View divider,div4,div10,div19,div3,div6,div18,div5;
    LinearLayout personalLayout, addLayout, editLayout;

    ImageView homeIcon,notificationIcon,personalIcon,messagesIcon,menuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        personalpage=findViewById(R.id.driver_personal_page);
        personalInfo=findViewById(R.id.personal_info);
        div4=findViewById(R.id.divider4);
        div10=findViewById(R.id.divider10);
        div19=findViewById(R.id.divider19);
        div3=findViewById(R.id.divider3);
        div6=findViewById(R.id.divider6);
        div18=findViewById(R.id.divider18);
        div5=findViewById(R.id.divider5);


/////

        add=findViewById(R.id.add);
        addDriver=findViewById(R.id.add_new_driver);
        addManager=findViewById(R.id.add_new_manager);
        addnewcar=findViewById(R.id.add_new_car);
        addnewline=findViewById(R.id.add_new_line);
        addnewgarage=findViewById(R.id.add_new_garage);

        edit=findViewById(R.id.edit);
        editlinedata=findViewById(R.id.edit_data_line);
        editgaragedata=findViewById(R.id.edit_data_garage);
        editcardata=findViewById(R.id.edit_data_car);

        personalLayout=findViewById(R.id.personalLayout);
        addLayout =findViewById(R.id.addLayout);
        editLayout =findViewById(R.id.editLayout);
        other=findViewById(R.id.other);
        showDrivers =findViewById(R.id.show_driver_list);
        readComplains =findViewById(R.id.read_complain);
        writecomplains=findViewById(R.id.add_complain);
        aboutapp=findViewById(R.id.about);
        settings = findViewById(R.id.settings);
        logout = findViewById(R.id.logout);
        divider=findViewById(R.id.divider9);
        login1=findViewById(R.id.login);

        personalpage.setBackgroundColor(Color.WHITE);
        addDriver.setBackgroundColor(Color.WHITE);
        addManager.setBackgroundColor(Color.WHITE);
        addnewcar.setBackgroundColor(Color.WHITE);
        addnewline.setBackgroundColor(Color.WHITE);
        addnewgarage.setBackgroundColor(Color.WHITE);
        editlinedata.setBackgroundColor(Color.WHITE);
        editgaragedata.setBackgroundColor(Color.WHITE);
        editcardata.setBackgroundColor(Color.WHITE);
        showDrivers.setBackgroundColor(Color.WHITE);
        readComplains.setBackgroundColor(Color.WHITE);
        writecomplains.setBackgroundColor(Color.WHITE);
        aboutapp.setBackgroundColor(Color.WHITE);
        settings.setBackgroundColor(Color.WHITE);
        logout.setBackgroundColor(Color.WHITE);
        login1.setBackgroundColor(Color.WHITE);



        isAdminOrDriverMenu();


        personalpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personalpage.setBackgroundColor(0xFFFF6F00);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);
                isAdminOrDriver();

            }
        });

        addDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDriver.setBackgroundColor(0xFFFF6F00);
                personalpage.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);

                Intent intent= new Intent(menu.this ,add_driver.class);
                startActivity(intent);
            }
        });




        addManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addManager.setBackgroundColor(0xFFFF6F00);
                personalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);



                Intent intent= new Intent(menu.this ,add_admin.class);
                startActivity(intent);
            }
        });
        addnewline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewline.setBackgroundColor(0xFFFF6F00);
                personalpage.setBackgroundColor(Color.WHITE);
                //adminPersonalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);


                Intent intent= new Intent(menu.this ,add_line.class);
                startActivity(intent);
            }
        });
        addnewgarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewgarage.setBackgroundColor(0xFFFF6F00);
                personalpage.setBackgroundColor(Color.WHITE);
              //  adminPersonalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);


                Intent intent= new Intent(menu.this ,add_garage.class);
                startActivity(intent);
            }
        });
        addnewcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewcar.setBackgroundColor(0xFFFF6F00);
                personalpage.setBackgroundColor(Color.WHITE);
           //     editpersonaldata.setBackgroundColor(Color.WHITE);
             //   changepass.setBackgroundColor(Color.WHITE);
               /// adminPersonalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);


                Intent intent= new Intent(menu.this ,add_car.class);
                startActivity(intent);

            }
        });
        editgaragedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editgaragedata.setBackgroundColor(0xFFFF6F00);
                personalpage.setBackgroundColor(Color.WHITE);
               // editpersonaldata.setBackgroundColor(Color.WHITE);
                //changepass.setBackgroundColor(Color.WHITE);
               // adminPersonalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);


                Intent intent= new Intent(menu.this ,edit_garage.class);
                startActivity(intent);
            }
        });
        editlinedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editlinedata.setBackgroundColor(0xFFFF6F00);
                //personalpage.setBackgroundColor(Color.WHITE);
                //editpersonaldata.setBackgroundColor(Color.WHITE);
               // changepass.setBackgroundColor(Color.WHITE);
              //  adminPersonalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);


                Intent intent= new Intent(menu.this ,edit_line.class);
                startActivity(intent);

            }
        });
        editcardata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editcardata.setBackgroundColor(0xFFFF6F00);
                personalpage.setBackgroundColor(Color.WHITE);
                //editpersonaldata.setBackgroundColor(Color.WHITE);
                //changepass.setBackgroundColor(Color.WHITE);
                //adminPersonalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);


                Intent intent= new Intent(menu.this ,edit_car_data.class);
                startActivity(intent);
            }
        });
        showDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDrivers.setBackgroundColor(0xFFFF6F00);
                //adminPersonalpage.setBackgroundColor(Color.WHITE);
                personalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);


                Intent intent= new Intent(menu.this ,show_drivers.class);
                startActivity(intent);
            }
        });
        readComplains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readComplains.setBackgroundColor(0xFFFF6F00);
               /// adminPersonalpage.setBackgroundColor(Color.WHITE);
                personalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);


                Intent intent= new Intent(menu.this ,read_complaints.class);
                startActivity(intent);
            }
        });
        writecomplains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writecomplains.setBackgroundColor(0xFFFF6F00);
              ///  adminPersonalpage.setBackgroundColor(Color.WHITE);
                personalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);



                Intent intent= new Intent(menu.this ,write_complaints.class);
                startActivity(intent);
            }
        });
        aboutapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutapp.setBackgroundColor(0xFFFF6F00);
              //  adminPersonalpage.setBackgroundColor(Color.WHITE);
                personalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);


                Intent intent= new Intent(menu.this ,about.class);
                startActivity(intent);


            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings.setBackgroundColor(0xFFFF6F00);
//                adminPersonalpage.setBackgroundColor(Color.WHITE);
                personalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                logout.setBackgroundColor(Color.WHITE);

                Intent intent= new Intent(menu.this ,settings.class);
                startActivity(intent);
            }
        });
        //4

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout.setBackgroundColor(0xFFFF6F00);
                personalpage.setBackgroundColor(Color.WHITE);
               // adminPersonalpage.setBackgroundColor(Color.WHITE);
                addDriver.setBackgroundColor(Color.WHITE);
                addManager.setBackgroundColor(Color.WHITE);
                addnewcar.setBackgroundColor(Color.WHITE);
                addnewline.setBackgroundColor(Color.WHITE);
                addnewgarage.setBackgroundColor(Color.WHITE);
                editlinedata.setBackgroundColor(Color.WHITE);
                editgaragedata.setBackgroundColor(Color.WHITE);
                editcardata.setBackgroundColor(Color.WHITE);
                showDrivers.setBackgroundColor(Color.WHITE);
                readComplains.setBackgroundColor(Color.WHITE);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);
                settings.setBackgroundColor(Color.WHITE);

                AlertDialog.Builder alert = new AlertDialog.Builder(menu.this);
                alert.setTitle("تأكيد تسجيل الخروج");
                alert.setMessage("هل تريد تأكيد تسجيل الخروج؟");

                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent= new Intent(menu.this ,login.class);
                        startActivity(intent);
                        login.s_id=null;
                    }


                });
                alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create().show();
            }



        });


        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login1.setBackgroundColor(0xFFFF6F00);
                writecomplains.setBackgroundColor(Color.WHITE);
                aboutapp.setBackgroundColor(Color.WHITE);

                Intent intent= new Intent(menu.this ,login.class);
                startActivity(intent);

            }
        });



//views in my actionbarPage
        homeIcon=findViewById(R.id.myActionBar_homeIcon);
        notificationIcon=findViewById(R.id.myActionBar_notificationsIcon);
        personalIcon=findViewById(R.id.myActionBar_personIcon);
        messagesIcon=findViewById(R.id.myActionBar_messagesIcon);
        menuIcon=findViewById(R.id.myActionBar_menuIcon);

        menuIcon.setBackgroundColor(Color.WHITE);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(menu.this ,home_page.class);
                startActivity(intent);
            }
        });
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(menu.this ,notifications.class);
                startActivity(intent);
            }
        });
        if(login.s_id!=null) {
            personalIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isAdminOrDriver();
                }
            });
            messagesIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(menu.this, chats.class);
                    startActivity(intent);
                }
            });
        }
        else{
            personalIcon.setVisibility(View.GONE);
            messagesIcon.setVisibility(View.GONE);
        }

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(menu.this ,menu.class);
                startActivity(intent);
            }
        });
    }


    private void isAdminOrDriverMenu() {

        if(login.s_id!=null) {

            String url = url_serverName.serverName + "isAdminOrDriver.php";
            StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray("A_D");
                        lineItem myItem;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject reader = jsonArray.getJSONObject(i);
                            String check = reader.getString("check");
                            if (check.equals("d")) {
                                readComplains.setVisibility(View.GONE);
                                divider.setVisibility(View.GONE);
                                add.setVisibility(View.GONE);
                                edit.setVisibility(View.GONE);
                                addLayout.setVisibility(View.GONE);
                                editLayout.setVisibility(View.GONE);
                                login1.setVisibility(View.GONE);
                                div19.setVisibility(View.GONE);

                            } else if(check.equals("a")) {
                                addManager.setVisibility(View.GONE);
                                addnewgarage.setVisibility(View.GONE);
                                login1.setVisibility(View.GONE);
                                div19.setVisibility(View.GONE);
                                div5.setVisibility(View.GONE);
                                div3.setVisibility(View.GONE);
                            }
                            else{
                                login1.setVisibility(View.GONE);
                                div19.setVisibility(View.GONE);
                                writecomplains.setVisibility(View.GONE);
                                div10.setVisibility(View.GONE);
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
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> myMap = new HashMap<>();
                    myMap.put("s_id", login.s_id);
                    return myMap;
                }
            };
            my_singleton.getInstance(menu.this).addToRequestQueue(myStringRequest);
        }
        else{
            personalInfo.setVisibility(View.GONE);
            personalLayout.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
            addLayout.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            editLayout.setVisibility(View.GONE);
            readComplains.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
            settings.setVisibility(View.GONE);
            div4.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
           /// showDrivers.setVisibility(View.GONE);
          //  div10.setVisibility(View.GONE);
            div19.setVisibility(View.GONE);
            other.setVisibility(View.GONE);



        }
    }
    private void isAdminOrDriver() {
        String url = url_serverName.serverName + "isAdminOrDriver.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("A_D");
                    lineItem myItem;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);
                        String check = reader.getString("check");
                        if(check.equals("d")){
                            Intent intent = new Intent(menu.this, personal_page.class);
                            startActivity(intent);
                        }else if(check.equals("a")){
                            Intent intent = new Intent(menu.this, admin_personal_page.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(menu.this, boss_personal_page.class);
                            startActivity(intent);
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
        my_singleton.getInstance(menu.this).addToRequestQueue(myStringRequest);
    }

}

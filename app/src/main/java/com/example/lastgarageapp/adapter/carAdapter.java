package com.example.lastgarageapp.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lastgarageapp.R;
import com.example.lastgarageapp.View_Driver;
import com.example.lastgarageapp.home_page;
import com.example.lastgarageapp.itemClasses.carItem;
import com.example.lastgarageapp.login;
import com.example.lastgarageapp.my_singleton;
import com.example.lastgarageapp.personal_page;
import com.example.lastgarageapp.show_drivers;
import com.example.lastgarageapp.url_serverName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class carAdapter extends RecyclerView.Adapter<carAdapter.carViewHolder> {
    ArrayList<carItem> myCarItem;
    Context con;
    String iid="";
    public String sour="";
    public String dest="";
    public String nameGarage="";
    String id="";
    private Intent intent;

    public carAdapter(Context context, ArrayList<carItem> myCarItem) {
        this.myCarItem = myCarItem;
        this.con = context;

    }





    @NonNull
    @Override
    public carViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_car_status_list_item, parent, false);
        carViewHolder holder = new carViewHolder(view);
        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull carViewHolder holder, int position) {
        carItem c = myCarItem.get(position);
        holder.carNumber.setText(c.getCarNumber());
        holder.nameDriver.setText(c.getDriverName());
        holder.u_id.setText(c.getuser());

        if(c.getAvailability().equals("0")){
            holder.availability.setText("غير متاحة");
            holder.arrivalLayout.setVisibility(View.VISIBLE);
            if(!c.getArrivalTime().equals("0")){
                holder.arrivalTime.setText(c.getArrivalTime());
            }else{
                holder.arrivalTime.setText("في طريقها للكراج الآخر");
            }
        }else if(c.getAvailability().equals("1")){
            holder.availability.setText("متاحة");
            holder.arrivalLayout.setVisibility(View.GONE);
        }

        holder.noOfPassenger.setText(c.getNoOfPassenger());
        holder.capacity.setText(c.getCapacity());
//        holder.arrivalTime.setText(c.getArrivalTime());




        if(login.s_id!=null){


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
                            if(check.equals("d")){
                                holder.iconDelet.setVisibility(View.GONE);
                           }
                            else if (check.equals("a")){
                             if(home_page.source.equals(home_page.nameGarage)||home_page.destination.equals(home_page.nameGarage)){
                                   holder.iconDelet.setVisibility(View.VISIBLE);
                               }else{
                                    holder.iconDelet.setVisibility(View.GONE);
                               }

                            }else if(check.equals("b")){
                                holder.iconDelet.setVisibility(View.VISIBLE);
                            }

                        }/////////
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(con, error.getMessage() + "", Toast.LENGTH_SHORT).show();
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
            my_singleton.getInstance(con).addToRequestQueue(myStringRequest);
        }
        else{
            holder.iconDelet.setVisibility(View.GONE);
        }

        holder.iconDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(con);
                alert.setTitle("تأكيد الحذف");
                alert.setMessage("هل تريد تأكيد الحذف؟");

                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Toast.makeText(line_status_list_item.this, "تم الحذف",Toast.LENGTH_SHORT).show();


                        String url = url_serverName.serverName + "deleteIconcar.php";
                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(con, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(con, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();

                                params.put("car_id", holder.carNumber.getText().toString());


                                return params;
                            }
                        };
                        my_singleton.getInstance(con).addToRequestQueue(stringRequest2);

                    }


                });
                alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Toast.makeText(line_status_list_item.this, "تم التراجع",Toast.LENGTH_SHORT).show();
                    }
                });
                alert.create().show();
            }
        });


        holder.nameDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iid = holder.u_id.getText().toString();

                if(iid.equals(login.myUser_id)){
                    Intent intent = new Intent(con, personal_page.class);
                    con.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(con, View_Driver.class);
                    intent.putExtra("message_key", iid);
                    con.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return myCarItem.size();
    }

    public class carViewHolder extends RecyclerView.ViewHolder {
        TextView carNumber,u_id,nameDriver, availability, noOfPassenger, capacity, arrivalTime;
        TextView iconDelet;
        LinearLayout arrivalLayout;

        public carViewHolder(@NonNull View itemView) {
            super(itemView);
            carNumber=itemView.findViewById(R.id.carItem_carNumber);
            nameDriver = itemView.findViewById(R.id.carItem_driverName);
            availability = itemView.findViewById(R.id.carItem_availabilty);
            noOfPassenger = itemView.findViewById(R.id.carItem_noOfPassenger);
            capacity = itemView.findViewById(R.id.carItem_capacity);
            arrivalTime = itemView.findViewById(R.id.carItem_arrivalTime);
            iconDelet = itemView.findViewById(R.id.carItem_deleteIcon);
            arrivalLayout = itemView.findViewById(R.id.carItem_arrivalLayout);
            u_id=itemView.findViewById(R.id.carItem_showcaruserid);
            id=carNumber.getText().toString();





        }

    }


}
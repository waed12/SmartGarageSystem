package com.example.lastgarageapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
import com.example.lastgarageapp.conversation;
import com.example.lastgarageapp.home_page;
import com.example.lastgarageapp.itemClasses.showDriversItem;
import com.example.lastgarageapp.login;
import com.example.lastgarageapp.my_singleton;
import com.example.lastgarageapp.personal_page;
import com.example.lastgarageapp.url_serverName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class showdriverAdapter extends RecyclerView.Adapter<showdriverAdapter.showDriversViewHolder>{
    private ArrayList<showDriversItem> myshowDriversItems;

    private Context con;

    public String iid;


    public showdriverAdapter(Context context, ArrayList<showDriversItem> showDriversItems) {
        this.myshowDriversItems= showDriversItems;
        this.con = context;

    }

    @NonNull
    @Override
    public showdriverAdapter.showDriversViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_drivers_list_item, parent, false);
        showdriverAdapter.showDriversViewHolder holder = new showdriverAdapter.showDriversViewHolder(view);
        return holder;
    }//////

    @Override
    public void onBindViewHolder(showdriverAdapter.showDriversViewHolder holder, int position) {
        showDriversItem l = myshowDriversItems.get(position);
        holder.NameText.setText(l.getnameText());
        holder.sour.setText(l.getsourText());
        holder.dest.setText(l.getdestText());
        holder.id.setText(l.getIdText());


        holder.iconMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(con, conversation.class);

                intent.putExtra("chat_id", "");
                intent.putExtra("receiver_name", holder.NameText.getText().toString());
                intent.putExtra("receiver_id", holder.id.getText().toString());

                con.startActivity(intent);

            }
        });


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
                                if(holder.sour.getText().toString().equals(home_page.nameGarage)||holder.dest.getText().toString().equals(home_page.nameGarage)){
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
            holder.iconMessage.setVisibility(View.GONE);
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


                        String url = url_serverName.serverName + "deleteIcondriver.php";
                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(con,response, Toast.LENGTH_SHORT).show();
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

                                params.put("user_id", holder.id.getText().toString());


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

        holder.NameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iid=holder.id.getText().toString();

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
        //how many items in my list
        return myshowDriversItems.size();
    }

    public class showDriversViewHolder extends RecyclerView.ViewHolder {

        TextView NameText, sour, dest, id;
        RelativeLayout show;
        TextView iconMessage,iconDelet;
        public String iid;




        public showDriversViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.showDriversItem_userId);
            NameText = itemView.findViewById(R.id.showDriversItem_driverName);
            sour = itemView.findViewById(R.id.ShowDriversItem_garage1);
            dest = itemView.findViewById(R.id.showDriversItem_garage2);


            show = itemView.findViewById(R.id.showdriver_listitems);

            iconMessage = itemView.findViewById(R.id.showDriversItem_messageIcon);

            iconDelet = itemView.findViewById(R.id.showDriversItem_deleteIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iid=id.getText().toString();

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
            //


        }

    }

}

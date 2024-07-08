package com.example.lastgarageapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.lastgarageapp.View_Admin;
import com.example.lastgarageapp.View_Boss;
import com.example.lastgarageapp.View_Driver;
import com.example.lastgarageapp.admin_personal_page;
import com.example.lastgarageapp.boss_personal_page;
import com.example.lastgarageapp.home_page;
import com.example.lastgarageapp.itemClasses.newsItem;
import com.example.lastgarageapp.login;
import com.example.lastgarageapp.my_singleton;
import com.example.lastgarageapp.personal_page;
import com.example.lastgarageapp.url_serverName;
import com.example.lastgarageapp.view_notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.newViewHolder>{
    private ArrayList<newsItem> myNewsItem;
    private Context con;
    private String iid;
    String d_or_a="";

    public newsAdapter(Context context,ArrayList<newsItem> myNewsItem) {
        this.con=context;
        this.myNewsItem=myNewsItem;

    }


    @NonNull
    @Override
    public newsAdapter.newViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_news_list_item, parent, false);
        newViewHolder holder = new newViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull newsAdapter.newViewHolder holder, int position) {
        newsItem ne= myNewsItem.get(position);
        holder.textName.setText(ne.getTextName());
        holder.textNews.setText(ne.getTextNews());
        holder.textHour.setText(ne.getTextHour());
        holder.newsId.setText(ne.getNewsId());
        holder.personalId.setText(ne.getPersonalId());


        if(login.s_id!=null){
            if(!holder.personalId.getText().toString().equals(login.myUser_id)){
                holder.iconDelete.setVisibility(View.GONE);
            }
        }
        else{
            holder.iconDelete.setVisibility(View.GONE);
        }

        holder.iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(con);
                alert.setTitle("تأكيد الحذف");
                alert.setMessage("هل تريد تأكيد الحذف؟");

                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String url = url_serverName.serverName + "deleteIconnews.php";
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

                                params.put("n_id", holder.newsId.getText().toString());


                                return params;
                            }
                        };
                        my_singleton.getInstance(con).addToRequestQueue(stringRequest2);

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
        holder.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iid=holder.personalId.getText().toString();
                String url = url_serverName.serverName + "isAdminOrDriverToView.php";
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
                                else if(check.equals("a")){
                                    if(iid.equals(login.myUser_id)){
                                        Intent intent = new Intent(con, admin_personal_page.class);
                                        con.startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(con, View_Admin.class);
                                        intent.putExtra("message_key", iid);
                                        con.startActivity(intent);
                                    }

                                }
                                else {
                                    if(iid.equals(login.myUser_id)){
                                        Intent intent = new Intent(con, boss_personal_page.class);
                                        con.startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(con, View_Boss.class);
                                        intent.putExtra("message_key", iid);
                                        con.startActivity(intent);
                                    }
                                }
                            }
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
                        myMap.put("u_id",iid );
                        return myMap;
                    }
                };
                my_singleton.getInstance(con).addToRequestQueue(myStringRequest);
           /*

               */

            }
        });


    }



    @Override
    public int getItemCount() {
        return myNewsItem.size();
    }

    public class newViewHolder extends RecyclerView.ViewHolder {
        TextView textName,textNews,textHour,newsId, personalId;
        ImageView  iconDelete;

        public newViewHolder(@NonNull View itemView) {
            super(itemView);
            textName=itemView.findViewById(R.id.newsItem_name);
            textHour=itemView.findViewById(R.id.newsItem_clock);
            textNews=itemView.findViewById(R.id.newsItem_description);
            newsId=itemView.findViewById(R.id.newsItem_newsId);
            personalId=itemView.findViewById(R.id.newsItem_personalId);
            iconDelete=itemView.findViewById(R.id.newsItem_delet);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(con, view_notification.class);
                    notificationAdapter.myNewsId=newsId.getText().toString();
                    con.startActivity(intent);
                }
            });

        }
    }
}


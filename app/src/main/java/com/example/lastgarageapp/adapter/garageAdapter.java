package com.example.lastgarageapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import com.example.lastgarageapp.View_Driver;
import com.example.lastgarageapp.admin_personal_page;
import com.example.lastgarageapp.conversation;
import com.example.lastgarageapp.edit_garage;
import com.example.lastgarageapp.home_page;
import com.example.lastgarageapp.itemClasses.garageItem;
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

public class garageAdapter extends RecyclerView.Adapter<garageAdapter.garageViewHolder> {

    private ArrayList<garageItem> myGarageItem;
    private LayoutInflater mInflater;
    Context con;
    String iid="";

    public garageAdapter(Context context, ArrayList<garageItem> myGarageItem) {
        this.myGarageItem = myGarageItem;
        this.con=context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public garageAdapter.garageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.activity_garage_status_list_item,parent,false);
        garageAdapter.garageViewHolder holder =new garageAdapter.garageViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(garageAdapter.garageViewHolder holder, int position) {
        garageItem g = myGarageItem.get(position);
        holder.cityName.setText(g.getCityName());
        holder.adminName.setText(g.getAdminName());
        holder.location.setText(g.getLocation());
        holder.fromHoure.setText(g.getFromHoure());
        holder.toHoure.setText(g.getToHoure());
        holder.id.setText(g.getadminid());

        holder.adminName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iid=holder.id.getText().toString();

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
        });


    }

    @Override
    public int getItemCount() {
        //how many items in my list
        return myGarageItem.size();
    }

    public class garageViewHolder extends RecyclerView.ViewHolder{
        TextView cityName, adminName, location, toHoure, fromHoure,id;
        Spinner sour;


        public garageViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName=itemView.findViewById(R.id.garageItem_cityName);
            adminName=itemView.findViewById(R.id.garageItem_adminName);
            location=itemView.findViewById(R.id.garageItem_locationValue);
            toHoure=itemView.findViewById(R.id.garageItem_toHoure);
            fromHoure=itemView.findViewById(R.id.garageItem_FromHoure);
            sour = itemView.findViewById(R.id.homePage_source);
            id=itemView.findViewById(R.id.garageItem_adminid);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    home_page.getInstance().MoveFromGarageToLine(cityName.getText().toString());

                }
            });



        }
    }
}

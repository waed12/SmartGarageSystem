package com.example.lastgarageapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.lastgarageapp.add_driver;
import com.example.lastgarageapp.edit_line;
import com.example.lastgarageapp.home_page;
import com.example.lastgarageapp.itemClasses.lineItem;
import com.example.lastgarageapp.line_status_list_item;
import com.example.lastgarageapp.login;
import com.example.lastgarageapp.my_singleton;
import com.example.lastgarageapp.url_serverName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class lineAdapter extends RecyclerView.Adapter<lineAdapter.lineViewHolder> {
    private ArrayList<lineItem> myLineItem;
    Context con;



    public lineAdapter(Context context, ArrayList<lineItem> myLineItem) {
        this.myLineItem = myLineItem;
        this.con = context;

    }

    @NonNull
    @Override
    public lineAdapter.lineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_line_status_list_item, parent, false);
        lineAdapter.lineViewHolder holder = new lineAdapter.lineViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(lineAdapter.lineViewHolder holder, int position) {
        lineItem l = myLineItem.get(position);
        holder.lineFare.setText(l.getLineFare());
        holder.noOfCars.setText(l.getNoOfCar());
        holder.garage1.setText(l.getGarage1());
        holder.garage2.setText(l.getGarage2());

    }

    @Override
    public int getItemCount() {
     return myLineItem.size();
    }


    public class lineViewHolder extends RecyclerView.ViewHolder {
        TextView lineFare, noOfCars, garage1,garage2;


        public lineViewHolder(@NonNull View itemView) {
            super(itemView);
            lineFare = itemView.findViewById(R.id.lineItem_lineFare);
            noOfCars = itemView.findViewById(R.id.lineItem_noOfCar);
            garage1= itemView.findViewById(R.id.lineItem_garage1);
            garage2= itemView.findViewById(R.id.lineItem_garage2);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 //   Toast.makeText(con, garageAdapter.sourcenotequalgarage1 + "", Toast.LENGTH_SHORT).show();

                //    if(garage1.getText().toString().compareTo(garageAdapter.sourcenotequalgarage1)==0) {

                        home_page.getInstance().MoveFromLineToCar(garage1.getText().toString(), garage2.getText().toString());
                 //   }

                  // else{
                    //  home_page.getInstance().MoveFromLineToCar(garage2.getText().toString(), garage1.getText().toString());
                 //  }

                }
            });



        }

    }




}

package com.example.lastgarageapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.lastgarageapp.conversation;
import com.example.lastgarageapp.itemClasses.chatItem;
import com.example.lastgarageapp.my_singleton;
import com.example.lastgarageapp.url_serverName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.messengerViewHolder> {
    ArrayList<chatItem> myMessengerItem;
    Context con;

    public chatAdapter(Context context, ArrayList<chatItem> myMessengerItem) {
        this.con=context;
        this.myMessengerItem=myMessengerItem;

    }

    @NonNull
    @Override
    public messengerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chats_list_item, parent, false);
        messengerViewHolder holder = new  messengerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull messengerViewHolder holder, int position) {
        chatItem m= myMessengerItem.get(position);
        holder.chat_id.setText(m.getChatId());
        holder.name.setText(m.getTextName());
        holder.textMessage.setText(m.getTextMessage());
        holder.hour.setText(m.getTextHour());

        holder.iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(con);
                alert.setTitle("تأكيد الحذف");
                alert.setMessage("هل تريد تأكيد الحذف؟");

                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String url = url_serverName.serverName + "deleteChat.php";
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

                                params.put("chat_id", holder.chat_id.getText().toString());
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
    }

    @Override
    public int getItemCount() {
        return myMessengerItem.size();
    }


    public class messengerViewHolder extends RecyclerView.ViewHolder {
        TextView name,textMessage, hour,chat_id;
        ImageView iconDelete;

        public messengerViewHolder(@NonNull View itemView) {
            super(itemView);
            chat_id =itemView.findViewById(R.id.messageItem_chatId);
            name =itemView.findViewById(R.id.messageItem_name);
            hour =itemView.findViewById(R.id.messageItem_clock);
            textMessage=itemView.findViewById(R.id.messageItem_message);
            iconDelete=itemView.findViewById(R.id.messageItem_deletIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    conversation.chat_id= chat_id.getText().toString();
//                    conversation.static_receiver_name= name.getText().toString();
                    Intent intent = new Intent(con, conversation.class);

                    intent.putExtra("chat_id", chat_id.getText().toString());
                    intent.putExtra("receiver_name", name.getText().toString());
                    intent.putExtra("receiver_id", "");

                    con.startActivity(intent);
                }
            });

        }

    }

    }


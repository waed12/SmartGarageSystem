package com.example.lastgarageapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lastgarageapp.R;
import com.example.lastgarageapp.itemClasses.messageItem;
import com.example.lastgarageapp.login;

import java.util.ArrayList;


public class messageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<messageItem> myChatItem ;
    private LayoutInflater mInflater;
    private static int TYPE_SENDER=1;
    private static int TYPE_RECEIVER=2;
    private int flag=0;
    Context con;

    public messageAdapter(Context context, ArrayList<messageItem> myChatItem) {
        this.myChatItem = myChatItem;
        this.mInflater = LayoutInflater.from(context);
        this.con=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;

        if(viewType==TYPE_SENDER){
            view= LayoutInflater.from(con).inflate(R.layout.send_massage_item,parent,false);
            holder =new myViewHolder(view);
        }else{
            view= mInflater.inflate(R.layout.received_message_item,parent,false);
            holder =new receiverViewHolder(view);
        }
        return holder;    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        messageItem c = myChatItem.get(position);
        if(getItemViewType(position)==TYPE_SENDER){
            ((myViewHolder)holder).sendMessageText.setText(c.getMessageText());
            ((myViewHolder)holder).sendHourText.setText(c.getHour());
        } else{
            ((receiverViewHolder)holder).receivedMessageText.setText(c.getMessageText());
            ((receiverViewHolder)holder).receivedHourText.setText(c.getHour());
        }
    }

    @Override
    public int getItemCount() {
        return myChatItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(myChatItem.get(position).getSender_id().equals(login.myUser_id)){
            return TYPE_SENDER;
        } else{
            return TYPE_RECEIVER;
        }
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView sendMessageText, sendHourText;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            sendMessageText=itemView.findViewById(R.id.sendMessageItem_messageText);
            sendHourText =itemView.findViewById(R.id.sendMessageItem_houre);
        }

    }
    public class receiverViewHolder extends RecyclerView.ViewHolder{

        TextView receivedMessageText, receivedHourText;
        public receiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receivedMessageText=itemView.findViewById(R.id.receivedMessageItem_messageText);
            receivedHourText =itemView.findViewById(R.id.receivedMessageItem_houre);
        }

    }
}
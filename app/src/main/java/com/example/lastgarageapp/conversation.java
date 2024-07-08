package com.example.lastgarageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lastgarageapp.adapter.messageAdapter;
import com.example.lastgarageapp.itemClasses.messageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class conversation extends AppCompatActivity {

    ArrayList<messageItem> myMessages = new ArrayList<>();
    messageAdapter myMessageAdapter;
    RecyclerView myRecyclerView;
    ImageView send_icon, backIcon;
    TextView addMessage, receiverName_textView;
    ScrollView myScroll;

    public String chat_id="";
    public String receiver_id="";
    public String receiver_name ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        receiverName_textView =findViewById(R.id.conversation_receiverName);
        addMessage =findViewById(R.id.conversation_addText);
        send_icon = findViewById(R.id.conversation_send_icon);
        backIcon =findViewById(R.id.conversation_backIcon);
        myRecyclerView = findViewById(R.id.converRecyView);
        myScroll = findViewById(R.id.conversation_myScroll);

        Intent intent = getIntent();
        chat_id = intent.getStringExtra("chat_id");
        receiver_name = intent.getStringExtra("receiver_name");
        receiver_id = intent.getStringExtra("receiver_id");

        myRecyclerView.setLayoutManager(new LinearLayoutManager(conversation.this));
        receiverName_textView.setText(receiver_name);
        selectMessages();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        send_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_message();
            }
        });
    }

    private void selectMessages(){
        myMessages.clear();
        String url = url_serverName.serverName + "selectMessages.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("messages");
                    messageItem myItem;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        String messageText = reader.getString("message_text");
                        String hour = reader.getString("hour");
                        String sender_id = reader.getString("sender_id");

                        myItem = new messageItem(messageText, hour, sender_id);
                        myMessages.add(myItem);
                    }
                    myMessageAdapter = new messageAdapter(conversation.this, myMessages);
                    myRecyclerView.setAdapter(myMessageAdapter);
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
                myMap.put("chat_id", chat_id);
                myMap.put("receiver_id", receiver_id);
                return myMap;
            }
        };
        my_singleton.getInstance(conversation.this).addToRequestQueue(myStringRequest);
    }
    private void add_message(){
        String message_text=addMessage.getText().toString();
        if (addMessage.getText().length() == 0) {
            Toast.makeText(getBaseContext(), "لا يمكنك إرسال رسالة فارغة", Toast.LENGTH_SHORT).show();
        } else {
            String url = url_serverName.serverName + "addMessage.php";
            StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    selectMessages();
//                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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
                    myMap.put("receiver_id", receiver_id);
                    myMap.put("chat_id", chat_id);
                    myMap.put("message_text", message_text);
                    myMap.put("s_id", login.s_id);
                    return myMap;
                }
            };
            my_singleton.getInstance(conversation.this).addToRequestQueue(myStringRequest);
//            myMessages.clear();
//            selectMessages();
//            myMessageAdapter.notifyDataSetChanged();
        }
        addMessage.setText("");
    }
}
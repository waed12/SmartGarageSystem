package com.example.lastgarageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class write_complaints extends AppCompatActivity {

    Button sendButt;
    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_complaints);

        sendButt=findViewById(R.id.writeComplaints_sendButt);
        text=findViewById(R.id.writeComplaints_text);

        sendButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myText=text.getText().toString();
                if(text.getText().length()==0){
                    Toast.makeText(getBaseContext(), "قم بإدخال اقتراح أو شكوى في مربع النص الذي فيا أعلى", Toast.LENGTH_SHORT).show();
                    text.setFocusable(true);
                }else{
                    String url = url_serverName.serverName+"addComplaint.php";
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

                            myMap.put("text", myText);

                            return myMap;
                        }
                    };
                    my_singleton.getInstance(write_complaints.this).addToRequestQueue(myStringRequest);
                }
                text.setText("");
            }
        });
    }

}
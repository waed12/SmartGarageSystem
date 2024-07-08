package com.example.lastgarageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class settings extends AppCompatActivity {
    private Button settings_cancel;
    private TextView editPersonalSettings,editadminPersonalSettings;
    private TextView changePassSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings_cancel = (Button) findViewById(R.id.settings_cancel);
        editPersonalSettings = (TextView) findViewById(R.id.editPersonalSettings);
        changePassSettings = (TextView) findViewById(R.id.changePassSettings);

        settings_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editPersonalSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditPersonal_page();

            }
        });

        changePassSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChangePass_page();
            }
        });

    }
  //  public void openAdminEditPersonal_page(){
     //   Intent intent = new Intent(this,edit_admin_personal_data.class);
       // startActivity(intent);

  //  }
    //5

    public void openEditPersonal_page(){
      //  Intent intent = new Intent(this,edit_personal_data.class);
      //  startActivity(intent);
        isAdminOrDriver();

    }
    public void openChangePass_page(){
        Intent intent = new Intent(this,change_password.class);
        startActivity(intent);


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
                            Intent intent = new Intent(settings.this, edit_personal_data.class);
                            startActivity(intent);
                        }else if(check.equals("a")){
                            Intent intent = new Intent(settings.this, edit_admin_personal_data.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(settings.this, edit_boss_personal_data.class);
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
        my_singleton.getInstance(settings.this).addToRequestQueue(myStringRequest);
    }
}



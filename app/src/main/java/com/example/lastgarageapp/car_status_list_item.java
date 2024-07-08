package com.example.lastgarageapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class car_status_list_item extends AppCompatActivity {

    private TextView carItem_editIcon;
    private TextView carItem_deleteIcon;

    private TextView car_no;
    public static String carNomber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_status_list_item);
//        carItem_editIcon = (TextView) findViewById(R.id.carItem_editIcon);
        car_no=(TextView) findViewById(R.id.carItem_carNumber);
        carNomber=car_no.getText().toString();
        carItem_editIcon = (TextView) findViewById(R.id.carItem_deleteIcon);

        carItem_editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditCarData_page();
            }
        });

        carItem_deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(car_status_list_item.this);
                    alert.setTitle("تأكيد الحذف");
                    alert.setMessage("هل تريد تأكيد الحذف؟");
                    alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(car_status_list_item.this, "تم الحذف",Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.setPositiveButton("لا", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(car_status_list_item.this, "تم التراجع",Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.create().show();

            }
        });

    }
    public void openEditCarData_page(){
        Intent intent = new Intent(this,edit_car_data.class);
        startActivity(intent);


    }

}

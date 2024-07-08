package com.example.lastgarageapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class first_page_loading {
   private Activity activity;
  // private AlertDialog dialoge;

    first_page_loading(Activity myActivity){
        activity= myActivity;


    }
  void startLoading(){
       // AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    LayoutInflater inflater = activity.getLayoutInflater();
  //  builder.setView(inflater.inflate(R.layout.activity_first_page, null));

    //dialoge= builder.create();
    //dialoge.show();



}
void dismissDialoge(){
      //  dialoge.dismiss();

}
}

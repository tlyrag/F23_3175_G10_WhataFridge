package com.douglas.whatafridge.Views;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.douglas.whatafridge.Controller.MailController;
import com.douglas.whatafridge.R;



public class ProfileActivity extends WFTemplate {


    Button btnSave;

    EditText editTextProfileUserId;
    EditText editTextName;
    EditText editTextAge;
    EditText editTextHeight;
    EditText editTextWeight;
    Switch switchGlutenFree;
    Spinner spinnerTypeVegetarian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editTextProfileUserId = findViewById(R.id.editTextProfileUserId);
        editTextProfileUserId.setEnabled(false);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        switchGlutenFree = findViewById(R.id.switchGlutenFree);
        spinnerTypeVegetarian = findViewById(R.id.spinnerTypeVegetarian);

        btnSave = findViewById(R.id.btnSave);

        SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
        String userid = preferences.getString(getString(R.string.txtUserid),"");

        //get information from database


        //profile image control


        //save
        btnSave.setOnClickListener((View v) -> {



            //save database
        });

    }
}
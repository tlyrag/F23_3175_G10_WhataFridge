package com.douglas.whatafridge.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import com.douglas.whatafridge.Controller.Database.MyDBHelper;
import com.douglas.whatafridge.Controller.Database.UserDBController;
import com.douglas.whatafridge.Controller.MailController;
import com.douglas.whatafridge.R;
import com.douglas.whatafridge.databinding.ActivityProfileBinding;
import com.douglas.whatafridge.databinding.ActivityVerificationBinding;

import java.util.Timer;
import java.util.TimerTask;

public class VerificationActivity extends WFTemplate {

    ActivityVerificationBinding binding;

    MyDBHelper myDBHelper;
    UserDBController db;

    TimerTask timerTask;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtViewVerifyTime.setText("");

        myDBHelper = new MyDBHelper(VerificationActivity.this);
        db = new UserDBController(myDBHelper);

        binding.btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.editTextEmail.getText().toString().isEmpty()){
                    Toast.makeText(VerificationActivity.this, "Enter your email.", Toast.LENGTH_SHORT).show();
                    binding.editTextEmail.hasFocus();
                }else{

                    try {
                        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                                .permitDiskReads()
                                .permitDiskWrites()
                                .permitNetwork().build());

                        MailController mailController = new MailController();

                        String code = mailController.createEmailCode();
                        String body = "Verification Code : " + code;

                        mailController.sendMail("[WhatAFridge] Verification Code",body,binding.editTextEmail.getText().toString());
                        binding.txtViewVerifyTime.setText(R.string.txtVerifyTime);

                        //save Database
                        db.insertVerifyCode(binding.editTextEmail.getText().toString(), code);


                        startTimerTask();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });


        binding.btnVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.editTextEmail.getText().toString().isEmpty() || binding.editTextCode.getText().toString().isEmpty()){
                    Toast.makeText(VerificationActivity.this, "Check again!", Toast.LENGTH_SHORT).show();
                }else{

                    String email = binding.editTextEmail.getText().toString();
                    String oriCode = db.selectVerifyCode(email);
                    if(oriCode.equals(binding.editTextCode.getText().toString())){
                        Intent intent = new Intent(VerificationActivity.this,SignUpActivity.class);
                        intent.putExtra("email", binding.editTextEmail.getText().toString());
                        startActivity(intent);
                    }
                }
            }
        });



    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        myDBHelper.close();
        super.onDestroy();
    }

    private void startTimerTask(){
        if(timerTask != null){
            binding.txtViewVerifyTime.setText("");
            binding.txtTimer.setText("");
            timerTask.cancel();
            timerTask = null;
        }

        timerTask = new TimerTask() {
            int count = 10;

            @Override
            public void run() {



                if(count == 0){

                    db.deleteVerifyCode(binding.editTextEmail.getText().toString());
                    timerTask.cancel();
                    runOnUiThread(new TimerTask() {
                        @Override
                        public void run() {
                            binding.txtViewVerifyTime.setText("");
                            binding.txtTimer.setText("");
                            Toast.makeText(VerificationActivity.this, "The time is over. Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    count--;

                    binding.txtTimer.post(() -> {
                        int min = count / 60;
                        int sec = count % 60;
                        binding.txtTimer.setText(min + ":" + sec);
                    });
                }
            }
        };
        timer.schedule(timerTask,0,1000);

    }
}
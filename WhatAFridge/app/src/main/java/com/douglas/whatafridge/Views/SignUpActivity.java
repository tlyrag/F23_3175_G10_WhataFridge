package com.douglas.whatafridge.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.douglas.whatafridge.Controller.Database.UserDBController;
import com.douglas.whatafridge.Model.ObjectModels.User;
import com.douglas.whatafridge.R;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends WFTemplate {


    EditText editTextSignUpEmail;
    EditText editTextSignUpPassword;
    EditText editTextConfirmPassword;
    Button btnSignUp;
    UserDBController db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = new UserDBController(SignUpActivity.this);

        editTextSignUpEmail = findViewById(R.id.editTextSignUpEmail);
        editTextSignUpPassword = findViewById(R.id.editTextSignUpPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener((View v) -> {
            if(editTextSignUpEmail.getText().toString().isEmpty()){
                Toast.makeText(this, R.string.txtLoingIdHint, Toast.LENGTH_SHORT).show();
                return;
            }

            if(editTextSignUpPassword.getText().toString().isEmpty()){
                Toast.makeText(this, R.string.txtPasswordHint, Toast.LENGTH_SHORT).show();
                return;
            }

            if(editTextConfirmPassword.getText().toString().isEmpty()){
                Toast.makeText(this, R.string.txtHintConfirmPassword, Toast.LENGTH_SHORT).show();
                return;
            }

            if(!editTextSignUpEmail.getText().toString().contains("@")){
                Toast.makeText(this, R.string.txtCheckEmail, Toast.LENGTH_SHORT).show();
                return;
            }

            if(!editTextSignUpPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())){
                Toast.makeText(this, R.string.txtCheckPassword, Toast.LENGTH_SHORT).show();
                return;
            }


            String userid = editTextSignUpEmail.getText().toString();
            String password = editTextSignUpPassword.getText().toString();
            String shaPassword = encryptSHA256(password);
            User user = db.selectUserInfo(userid);

            if(user == null){
                //save database
                db.insertUserPass(userid, shaPassword);

                //save SharedPreferences
                SharedPreferences preferences = getSharedPreferences("USER", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.txtUserid),userid);
                editor.putString(getString(R.string.txtPassword),shaPassword);

                editor.apply();

                //move to home page
                startActivity(new Intent(SignUpActivity.this,HomePageActivity.class));

            }else{
                Toast.makeText(this, "Already have an account.", Toast.LENGTH_SHORT).show();
                return;
            }

        });




    }

    public String encryptSHA256(String oriString){
        String shaString = "";
        byte[] message = oriString.getBytes(StandardCharsets.UTF_8);

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(oriString.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            shaString = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return shaString;
    }
}
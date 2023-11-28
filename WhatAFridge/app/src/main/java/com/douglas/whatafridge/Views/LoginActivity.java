package com.douglas.whatafridge.Views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.douglas.whatafridge.Controller.Database.MyDBHelper;
import com.douglas.whatafridge.Controller.Database.UserDBController;
import com.douglas.whatafridge.Controller.MailController;
import com.douglas.whatafridge.Model.ObjectModels.User;
import com.douglas.whatafridge.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import javax.mail.MessagingException;

public class LoginActivity extends WFTemplate {

    SignInClient oneTapClient;
    BeginSignInRequest signUpRequest;

    private static final int REQ_ONE_TAP = 2;
    private boolean showOneTapUI = true;

    Button btnLoginWithGoogle;
    Button btnLoginWithoutGoogle;
    EditText editTextUserId;
    EditText editTextPassword;

    TextView txtViewSignUp;

    String Userid;
    String Password;
    MyDBHelper myDBHelper;
    UserDBController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //after login successfully, it is stored in the device and use again.
        SharedPreferences preferences = getSharedPreferences("USER", MODE_PRIVATE);
        Userid = preferences.getString(getString(R.string.txtUserid),"");
        Password = preferences.getString(getString(R.string.txtPassword),"");

        myDBHelper = new MyDBHelper(LoginActivity.this);

        db = new UserDBController(myDBHelper);

        if(!Userid.equals("")){
            //check the information with Database
            User user = db.selectUserInfo(Userid);

            //move to mainActivity
            if(Password.equals(user.getPassword())){
                startActivity(new Intent(LoginActivity.this,MainPageActivity.class));
            }
        }

        //hide actionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        txtViewSignUp = findViewById(R.id.txtViewSignUp);
        txtViewSignUp.setOnClickListener((View v) -> {
            //startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            startActivity(new Intent(LoginActivity.this,VerificationActivity.class));

        });

        btnLoginWithGoogle = findViewById(R.id.btnLoginWithGoogle);
        btnLoginWithoutGoogle = findViewById(R.id.btnLoginWithoutGoogle);

        //google one tap login (It works in a real device)
        oneTapClient = Identity.getSignInClient(this);

        //send Client Id to OAuth 2.0 server
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.web_client_id))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        //to get data from Activity
        ActivityResultLauncher<IntentSenderRequest> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {

                    //onActivityResult: main activity-> sub activity -> main activity
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            try {
                                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                                String idToken = credential.getGoogleIdToken();
                                if (idToken !=  null) {
                                    // Got an ID token from Google. Use it to authenticate
                                    // with your backend.
                                    String email = credential.getId(); //email
                                    String password = credential.getPassword(); //password;
                                    //simple SHA
                                    String shaPassword = encryptSHA256(password);

                                    //compare to database
                                    User user = db.selectUserInfo(email);

                                    if(user == null){
                                        db.insertUserPass(email,shaPassword);
                                    }else{
                                        db.updateUserInfo(user);
                                    }

                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(getString(R.string.txtUserid),email);
                                    editor.putString(getString(R.string.txtPassword),shaPassword);

                                    editor.apply();

                                    startActivity(new Intent(LoginActivity.this,MainPageActivity.class));

                                }
                            } catch (ApiException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

        btnLoginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTapClient.beginSignIn(signUpRequest)
                        .addOnSuccessListener(LoginActivity.this, new OnSuccessListener<BeginSignInResult>() {
                            @Override
                            public void onSuccess(BeginSignInResult result) {

//                                    startIntentSenderForResult(
//                                            result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
//                                            null, 0, 0, 0);
                                IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
                                activityResultLauncher.launch(intentSenderRequest);

                            }
                        })
                        .addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // No Google Accounts found. Just continue presenting the signed-out UI.
                                Log.d(TAG,"OnTapClient Failure: "+  e.getLocalizedMessage());
                            }
                        });
            }
        });


        btnLoginWithoutGoogle.setOnClickListener(view -> {

            editTextUserId = findViewById(R.id.editTextUserId);
            editTextPassword = findViewById(R.id.editTextPassword);

            //CSIS3175
            //c635d9b9556127408e3bb3a8e3a074506248c96a9c82b368da4a66d108b5c676

            if(editTextUserId.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter User Email.", Toast.LENGTH_SHORT).show();
            }else if(editTextPassword.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter the password.", Toast.LENGTH_SHORT).show();
            }else{

                String oriPassword = editTextPassword.getText().toString();
                String shaPassword = encryptSHA256(oriPassword);

                //compare to database
                User user = db.selectUserInfo(editTextUserId.getText().toString());

                if(user == null){
                    Toast.makeText(this, "Not existed user information. Please Sign up.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(shaPassword.equals(user.getPassword())){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(getString(R.string.txtUserid),editTextUserId.getText().toString());
                    editor.putString(getString(R.string.txtPassword),shaPassword);

                    editor.apply();

                    startActivity(new Intent(LoginActivity.this,MainPageActivity.class));
                }

            }

            /*

            //sending email
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                                .permitDiskReads()
                                .permitDiskWrites()
                                .permitNetwork().build());

            MailController mailController = new MailController();
            String subject = "test";
            String body = "test";
            String recipients = "kimn25@student.douglascollege.ca";
            try {
                mailController.sendMail(subject,body,recipients);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

             */
            //startActivity(new Intent(LoginActivity.this,HomePageActivity.class));
            startActivity(new Intent(LoginActivity.this,MainPageActivity.class));

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

    @Override
    protected void onDestroy() {
        myDBHelper.close();
        super.onDestroy();
    }
}
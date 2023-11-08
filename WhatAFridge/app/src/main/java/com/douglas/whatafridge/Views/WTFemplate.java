package com.douglas.whatafridge.Views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.douglas.whatafridge.R;

public class WTFemplate extends AppCompatActivity {
    public final String TAG = "WTF APP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wfridge_template);
        setUpActionBar();

    }

    private void setUpActionBar() {
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setLogo(R.mipmap.ic_launcher);
        actionbar.setTitle(R.string.txtAppTitle);
    }
}
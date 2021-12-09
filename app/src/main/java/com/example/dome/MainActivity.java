package com.example.dome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lk.simple.DatePickerFragmentDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatePickerFragmentDialog dialog=new DatePickerFragmentDialog();
        dialog.show(getSupportFragmentManager(),"ss");
    }
}
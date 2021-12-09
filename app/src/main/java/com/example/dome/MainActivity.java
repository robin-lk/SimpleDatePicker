package com.example.dome;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lk.simple.DatePickerFragmentDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DatePickerFragmentDialog1 dialog=new DatePickerFragmentDialog1();
        DatePickerFragmentDialog dialog=new DatePickerFragmentDialog();

        new Thread(){

            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.show(getSupportFragmentManager(),"ss");

                    }
                });
            }
        }.start();
    }
}
package com.example.dome;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.lk.simple.DatePickerFragmentDialog;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DatePickerFragmentDialog1 dialog=new DatePickerFragmentDialog1();
        DatePickerFragmentDialog dialog = new DatePickerFragmentDialog();
        Calendar calendar = Calendar.getInstance();
        dialog.initDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

        new Thread() {

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
                        dialog.show(getSupportFragmentManager(), "ss");

                    }
                });
            }
        }.start();
        getDbFile();
    }


    private void getDbFile() {

        File databases = getDir("databases", MODE_APPEND);
        File database = getDir("database", MODE_APPEND);
        File obbDir = getObbDir();
        if (obbDir != null) {
            Log.e("obbDir", obbDir.toString());
        }
        File oo = getDatabasePath("oo");
        File filesDir = getFilesDir();
        File dd = getDatabasePath("dd");
        if (dd != null) {
            Log.e("dd", dd.toString());
        }
        if (databases.exists() && databases.isDirectory()) {
            Log.e("file", databases.toString());
            Log.e("filesDir", filesDir.toString());
            Log.e("database", database.toString());
            File[] files = databases.listFiles();
            if (files != null)
                for (File file : files) {
                    Log.e("file", file.toString());
                }
        }

    }
}
package com.baitu.jnitest;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),
                getApplicationContext().getFilesDir().getPath() + "," + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
        long a = System.currentTimeMillis();
        String str = UnInstallListen.register(getApplicationContext().getFilesDir().getPath(), "http://www.baidu.com",
                android.os.Build.VERSION.SDK_INT);
        long b = System.currentTimeMillis();
        Toast.makeText(getApplicationContext(), str+","+(b-a), Toast.LENGTH_LONG).show();
    }
}



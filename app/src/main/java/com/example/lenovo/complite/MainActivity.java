package com.example.lenovo.complite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick1(View v){
        Intent intent=new Intent("com.example.lenovo.complite.ACTION_START");
        startActivity(intent);
    }
    public void onClick2(View v){
        Intent intent2=new Intent("com.example.lenovo.complite.SHADOWS");
        intent2.addCategory("android.intent.category.MY_CATEGORY");
        startActivity(intent2);
    }
}

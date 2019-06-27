package com.bailun.bl_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bailun.bl_demo.listdataview.demo.view.ListDataViewActivity;
import com.bailun.bl_demo.permission.AopPermissionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_go_to_bailun_btn_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BailunBtnActivity.class));
            }
        });
        findViewById(R.id.btn_go_to_bailun_listdataview_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListDataViewActivity.class));
            }
        });
        findViewById(R.id.btn_go_to_bailun_permission_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AopPermissionActivity.class));
            }
        });
    }
}

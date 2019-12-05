package com.bailun.bl_demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.bailun.bl_uilib.BailunButton;

/**
 * BailunButton演示Demo
 *
 * @author yh
 */
public class BailunBtnActivity extends AppCompatActivity {

    private BailunButton btnHuichachaLoading;
    private BailunButton btnHuichachaSelect;
    private BailunButton btnHuihuLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bailun_btn);
        btnHuichachaLoading = findViewById(R.id.btn_huichacha_loading);
        btnHuichachaLoading.startLoading();
        btnHuichachaSelect = findViewById(R.id.btn_huichacha_select);
        btnHuichachaSelect.setSelected(true);
        btnHuihuLoading = findViewById(R.id.btn_hui_hu_loading);
        btnHuihuLoading.startLoading();
    }
}

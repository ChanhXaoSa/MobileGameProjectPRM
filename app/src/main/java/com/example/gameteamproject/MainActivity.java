package com.example.gameteamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnBatDauChoi,btnDangNhap,btnDangKi;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        biding();
        hideLoginRegis();
        clickBtnBatDauChoi();
        clickBtnDangNhap();
        clickBtnDangKi();
    }
    private void hideLoginRegis() {
        btnDangKi.setVisibility(View.GONE);
        btnDangNhap.setVisibility(View.GONE);
    }
    private void biding() {
        btnBatDauChoi = findViewById(R.id.btnBatDauChoi);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKi = findViewById(R.id.btnDangKi);
    }
    private void clickBtnBatDauChoi() {
        btnBatDauChoi.setOnClickListener(view -> {
            btnBatDauChoi.setVisibility(View.GONE);
            btnDangKi.setVisibility(View.VISIBLE);
            btnDangNhap.setVisibility(View.VISIBLE);
        });
    }
    private void clickBtnDangNhap() {
        btnDangNhap.setOnClickListener(view -> {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
    private void clickBtnDangKi() {
        btnDangKi.setOnClickListener(view -> {
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
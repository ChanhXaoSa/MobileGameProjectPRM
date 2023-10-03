package com.example.gameteamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    // khai báo biến
    private EditText etUsername, etPassword, etConfirmPassword;
    private Button btnSignUp;
    private final String REQUIRE ="Require";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding();
        checkInput();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void binding(){
        etUsername = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etPasswordConfirm);
        btnSignUp = (Button) findViewById(R.id.btnSignIn);
    }

    private boolean checkInput(){
        // user, password
        if(TextUtils.isEmpty(etUsername.getText().toString())
                || TextUtils.isEmpty(etPassword.getText().toString())
                || TextUtils.isEmpty(etConfirmPassword.getText().toString())){
            etUsername.setError(REQUIRE);
            return false;
        }
        if(!TextUtils.equals(etPassword.getText().toString(), etConfirmPassword.getText().toString())){
            Toast.makeText(this, "Password are not match", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    // Đăng kí
    private void signUp(){
        if(!checkInput()){
            return;
        }
        // Start Main Activity
        Intent intent = new Intent(this, InGameActivity.class);
        startActivity(intent);
        finish();   // đóng màn hình đang chạy
    }
}
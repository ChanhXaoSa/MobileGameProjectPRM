package com.example.gameteamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    // khai báo biến
    private EditText etUsername, etPassword;
    private Button btnSignIn;
    //noti
    private final String REQUIRE ="Require";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding();

        // Register event
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    public void binding(){
        etUsername = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
    }

    private boolean checkInput(){
        // user, password
        if(TextUtils.isEmpty(etUsername.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())){
            etUsername.setError(REQUIRE);
            return false;
        }
        return true;
    }

    // Đăng nhập
    private void signIn(){
        if(!checkInput()){
            return;
        }
        if(!etUsername.getText().toString().equals("admin")){
            Toast.makeText(this, "Username does not exits", Toast.LENGTH_LONG).show();
            return;
        }

        if(!etPassword.getText().toString().equals("12345")){
            Toast.makeText(this, "Password wrong", Toast.LENGTH_LONG).show();
            return;
        }
        // Start Main Activity
        Intent intent = new Intent(this, InGameActivity.class);
        startActivity(intent);
        finish();   // đóng màn hình đang chạy
    }
}
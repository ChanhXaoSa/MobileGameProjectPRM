package com.example.gameteamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

        // Get the saved user data
        String username = getUsername();
        String password = getPassword();

        if(!etUsername.getText().toString().equals("admin") && !etUsername.getText().toString().equals(username)){
            Toast.makeText(this, "Username does not exits", Toast.LENGTH_LONG).show();
            return;
        }

        if(!etPassword.getText().toString().equals("12345") && !etPassword.getText().toString().equals(password)){
            Toast.makeText(this, "Password wrong", Toast.LENGTH_LONG).show();
            return;
        }
        // Start Main Activity
        Intent intent = new Intent(this, InGameActivity.class);
        startActivity(intent);
        finish();   // đóng màn hình đang chạy
    }

    private String getUsername() {
        // Get the SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

        // Get the username from the SharedPreferences
        String username = sharedPreferences.getString("username", "");

        return username;
    }

    private String getPassword() {
        // Get the SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

        // Get the password from the SharedPreferences
        String password = sharedPreferences.getString("password", "");

        return password;
    }
}
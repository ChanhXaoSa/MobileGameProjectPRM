package com.example.gameteamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
        // Save the user data
        saveUserData(etUsername.getText().toString(), etPassword.getText().toString());
        return true;
    }

    // Đăng kí
    private void signUp(){
        if(!checkInput()){
            return;
        }
        // Start Main Activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();   // đóng màn hình đang chạy
    }

    // Lưu dữ liệu tài khoản
    private void saveUserData(String username, String password) {
        // Get the SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

        // Create an editor for the SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Put the user data in the SharedPreferences
        editor.putString("username", username);
        editor.putString("password", password);

        // Apply the changes
        editor.apply();
    }
}
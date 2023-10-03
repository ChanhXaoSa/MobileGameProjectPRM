package com.example.gameteamproject;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class InGameActivity extends AppCompatActivity {
    private Account account = new Account("a","a",100000);
    private TextView tvBalance;
    private Button btnBet;
    private EditText etnCar1, etnCar2, etnCar3;
    private SeekBar sbCar1,sbCar2,sbCar3;
    private Button btnStart;
    private final int TARGET_PROGRESS = 100;
    private final Handler handler = new Handler();
    private final Random random = new Random();
    private boolean isRunning = false;
    private boolean isFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        anhXa();
        betAction();
        startAction();
        showBalance();
    }
    private void anhXa() {
        tvBalance = findViewById(R.id.tvBalance);
        etnCar1 = findViewById(R.id.etnCar1);
        etnCar2 = findViewById(R.id.etnCar2);
        etnCar3 = findViewById(R.id.etnCar3);
        btnBet = findViewById(R.id.btnBet);
        sbCar1 = findViewById(R.id.sbCar1);
        sbCar2 = findViewById(R.id.sbCar2);
        sbCar3 = findViewById(R.id.sbCar3);
        btnStart = findViewById(R.id.btnStart);
    }
    private void showBalance() {
        tvBalance.setText("Balance:" + account.getMoney());
    }
    private void startAction() {
        btnStart.setOnClickListener(view -> {
            if (!isRunning) {
                startProcessBar();
                isRunning = true;
                isFinished = false;
                btnStart.setEnabled(false);
            } else {
                stopProgressBar();
                isRunning = false;
            }
        });
    }
    private void betAction() {
        btnBet.setOnClickListener(view -> {
            if (!etnCar1.getText().toString().trim().isEmpty()) {
                account.setMoney(account.getMoney() - Integer.parseInt(etnCar1.getText().toString()));
            }
            if (!etnCar2.getText().toString().trim().isEmpty()) {
                account.setMoney(account.getMoney() - Integer.parseInt(etnCar2.getText().toString()));
            }
            if (!etnCar3.getText().toString().trim().isEmpty()) {
                account.setMoney(account.getMoney() - Integer.parseInt(etnCar3.getText().toString()));
            }
            showBalance();
        });
    }
    private void clearBet() {
        etnCar1.setText("");
        etnCar2.setText("");
        etnCar3.setText("");
    }
    private void startProcessBar() {
        sbCar1.setProgress(0);
        sbCar2.setProgress(0);
        sbCar3.setProgress(0);

        Runnable runnable = new Runnable() {
            int progress1 = 0;
            int progress2 = 0;
            int progress3 = 0;

            @Override
            public void run() {
                if (!isFinished) {
                    if (progress1 < TARGET_PROGRESS) {
                        progress1 += random.nextInt(10) + 1;
                        sbCar1.setProgress(progress1);
                    }

                    if (progress2 < TARGET_PROGRESS) {
                        progress2 += random.nextInt(10) + 1;
                        sbCar2.setProgress(progress2);
                    }

                    if (progress3 < TARGET_PROGRESS) {
                        progress3 += random.nextInt(10) + 1;
                        sbCar3.setProgress(progress3);
                    }

                    if (progress1 >= TARGET_PROGRESS || progress2 >= TARGET_PROGRESS || progress3 >= TARGET_PROGRESS) {
                        isFinished = true;
                    }

                    if (!isFinished) {
                        handler.postDelayed(this, 50);
                    } else {
                        if (progress1 >= TARGET_PROGRESS) {
                            if (!etnCar1.getText().toString().trim().isEmpty()) {
                                account.setMoney(account.getMoney() + Integer.parseInt(etnCar1.getText().toString()) * 2);
                            }
                        } else if (progress2 >= TARGET_PROGRESS) {
                            if (!etnCar2.getText().toString().trim().isEmpty()) {
                                account.setMoney(account.getMoney() + Integer.parseInt(etnCar2.getText().toString()) * 2);
                            }
                        } else {
                            if (!etnCar3.getText().toString().trim().isEmpty()) {
                                account.setMoney(account.getMoney() + Integer.parseInt(etnCar3.getText().toString()) * 2);
                            }
                        }
                        showBalance();
                        clearBet();
                        stopProgressBar();
                        isRunning = false;
                        btnStart.setEnabled(true);
                    }
                }
            }
        };
        handler.post(runnable);
    }
    private void stopProgressBar() {
        handler.removeCallbacksAndMessages(null);
    }
}
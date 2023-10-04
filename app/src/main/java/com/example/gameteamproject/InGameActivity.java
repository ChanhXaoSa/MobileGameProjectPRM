package com.example.gameteamproject;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class InGameActivity extends AppCompatActivity {
    private final Account account = new Account("a","a",100000);
    private TextView tvBalance, tvWinnerCarName, tvWinnerMoneyGet;
    private Button btnBet;
    private EditText etnCar1, etnCar2, etnCar3;
    private int betCar1, betCar2, betCar3;
    private int totalMoneyGet = 0;
    private SeekBar sbCar1,sbCar2,sbCar3;
    private Button btnStart;
    private final int TARGET_PROGRESS = 1000;
    private final Handler handler = new Handler();
    private final Random random = new Random();
    private boolean isRunning = false;
    private boolean isFinished = false;

    private MediaPlayer mediaPlayer;
    private MediaPlayer clickSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        clickSound = MediaPlayer.create(this, R.raw.click);
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
        String string = "Balance " + account.getMoney() + "$";
        tvBalance.setText(string);
    }
    private void startAction() {
        btnStart.setOnClickListener(view -> {
            if (!isRunning) {
                startProcessBar();
                mediaPlayer = MediaPlayer.create(this, R.raw.running);
                mediaPlayer.start();
                clickSound.start();
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
        if (betCar1 < 0 || betCar2 < 0 || betCar3 < 0) {
            Toast.makeText(this, "Bet amount cannot be less than 0!", Toast.LENGTH_SHORT).show();
            return;
        }
        btnBet.setOnClickListener(view -> {
            if (!etnCar1.getText().toString().trim().isEmpty()) {
                betCar1 = Integer.parseInt(etnCar1.getText().toString());
            }
            if (!etnCar2.getText().toString().trim().isEmpty()) {
                betCar2 = Integer.parseInt(etnCar2.getText().toString());
            }
            if (!etnCar3.getText().toString().trim().isEmpty()) {
                betCar3 = Integer.parseInt(etnCar3.getText().toString());
            }
            if (account.getMoney() < (betCar1 + betCar2 + betCar3)) {
                Toast.makeText(this, "You do not have enought money!", Toast.LENGTH_SHORT).show();
            } else {
                account.setMoney(account.getMoney() - (betCar1 + betCar2 + betCar3));
            }
            showBalance();
            clickSound.start();
        });
    }
    private void clearBet() {
        etnCar1.setText("");
        etnCar2.setText("");
        etnCar3.setText("");
        betCar1 = 0;
        betCar2 = 0;
        betCar3 = 0;
        totalMoneyGet = 0;
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
                        progress1 += random.nextInt(20) + 1;
                        sbCar1.setProgress(progress1);
                    }

                    if (progress2 < TARGET_PROGRESS) {
                        progress2 += random.nextInt(20) + 1;
                        sbCar2.setProgress(progress2);
                    }

                    if (progress3 < TARGET_PROGRESS) {
                        progress3 += random.nextInt(20) + 1;
                        sbCar3.setProgress(progress3);
                    }

                    if (progress1 >= TARGET_PROGRESS || progress2 >= TARGET_PROGRESS || progress3 >= TARGET_PROGRESS) {
                        isFinished = true;
                    }

                    if (!isFinished) {
                        handler.postDelayed(this, 50);
                    } else {
                        winnerResultOpen();
                        if (progress1 >= TARGET_PROGRESS) {
                            tvWinnerCarName.setText("Car 1 win the race!!!");
                            if (betCar1 != 0) {
                                account.setMoney(account.getMoney() + betCar1 * 2);
                            }
                            totalMoneyGet = totalMoneyGet + betCar1*2 - betCar2 - betCar3;
                        } else if (progress2 >= TARGET_PROGRESS) {
                            tvWinnerCarName.setText("Car 2 win the race!!!");
                            if (betCar2 != 0) {
                                account.setMoney(account.getMoney() + betCar2 * 2);
                            }
                            totalMoneyGet = totalMoneyGet + betCar2*2 - betCar3 - betCar1;
                        } else {
                            tvWinnerCarName.setText("Car 3 win the race!!!");
                            if (betCar3 != 0) {
                                account.setMoney(account.getMoney() + betCar3 * 2);
                            }
                            totalMoneyGet = totalMoneyGet + betCar3*2 - betCar1 - betCar2;
                        }
                        if (betCar1 == 0 && betCar2 == 0 && betCar3 == 0) {
                            tvWinnerMoneyGet.setText("You are not participating in betting on this race !");
                        } else if (totalMoneyGet < 0) {
                            tvWinnerMoneyGet.setText("You lost " + totalMoneyGet*-1 +"$");
                        } else {
                            tvWinnerMoneyGet.setText("You earn " + totalMoneyGet + "$");
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
    private void winnerResultOpen() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_winner_popup, null);
        tvWinnerMoneyGet = popupView.findViewById(R.id.tvWinnerMoneyGet);
        tvWinnerCarName = popupView.findViewById(R.id.tvWinnerCarName);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(this, R.raw.tada);
        mediaPlayer.start();
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupView.setOnTouchListener((view, motionEvent) -> {
            popupWindow.dismiss();
            return true;
        });
    }

    private void stopProgressBar() {
        handler.removeCallbacksAndMessages(null);
    }
}
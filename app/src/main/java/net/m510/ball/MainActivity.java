package net.m510.ball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.health.TimerStat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView ball;

    private float ballX;
    private float ballY;
    private float incY;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    private boolean action_flg = false;
    private boolean is_start = false;

    private int frameHeight;
    private int ballSize;

    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        ball = findViewById(R.id.orange);

        ballX = 500.0f;
        ballY = 500.0f;

        ball.setX(ballX);
        ball.setY(ballY);

        scoreLabel.setText("Score : 0");

    }

    public void changePos() {
        if ( score < 200 ) incY = 20;
        if ( score >= 200 && score < 400 ) incY = 40;
        if ( score >= 400 && score < 600 ) incY = 60;
        if ( score >= 600 ) incY = 80;
        if ( action_flg ) {
            ballY -= incY;
        } else {
            ballY += incY;
        }
//        if ( ballY < 0) ballY = 0;
//        if ( ballY >  frameHeight - ballSize ) ballY = frameHeight - ballSize;
        if ( ballY < 0 || ballY > frameHeight - ballSize ) {
            // Game Over
            if ( timer != null ) {
                timer.cancel();
                timer = null;
            }
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
            return;
        }

        ball.setY(ballY);
        score += 1;
        scoreLabel.setText("Score : " + score);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if ( is_start == false ) {
            is_start = true;

            FrameLayout frame = findViewById(R.id.frame);
            frameHeight = frame.getHeight();

//            ballY = frameHeight / 2.0f;
            ballY = ball.getY();
            ballSize = ball.getHeight();
            startLabel.setVisibility(View.GONE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);
        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }
        return true;
    }

}
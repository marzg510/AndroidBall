package net.m510.ball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");

        SharedPreferences pref = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        int highScore = pref.getInt("HIGH_SCORE", 0);
        if ( score > highScore ) {
            highScoreLabel.setText("High Score : " + score);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.apply();
        } else {
            highScoreLabel.setText("High Score : " + highScore);
        }
    }

    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}

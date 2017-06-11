package com.ahn.spottheodd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {
    private View.OnClickListener listener;
    private Button pButton;
    private TextView score, bestScore, screenDisplay;
    private String intentCheck;
    private int cScore, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT>20){
            setTheme(R.style.AppTheme);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF0B9EBD"));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game);

        Intent i = getIntent();
        score = (TextView) findViewById(R.id.current_score);
        bestScore = (TextView) findViewById(R.id.best_score);
        screenDisplay=(TextView) findViewById(R.id.score_screen);
        intentCheck=i.getStringExtra("intentCheck");
        switch (intentCheck){
            case Selection.CLASSIC:
                data=i.getIntExtra("data", 0);
                screenDisplay.setText(R.string.classic_mode);
                break;
            case Selection.ARCADE:
                data=i.getIntExtra("data", 0);
                screenDisplay.setText(R.string.arcade_mode);
                break;
            case Selection.HARD:
                data=i.getIntExtra("data", 0);
                screenDisplay.setText(R.string.hard_mode);
                break;
        }
        cScore = i.getIntExtra("score",0);
        pButton=(Button) findViewById(R.id.play_game);
        score.setText(cScore+"");
        bestScore.setText(data+"");

        listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    Intent g = new Intent(getApplicationContext(), Selection.class);
                    startActivity(g);
                    finish();
            }
        };
        pButton.setOnClickListener(listener);
    }

    @Override
    public void onBackPressed(){
        Intent g = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(g);
        finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        finish();
    }
}

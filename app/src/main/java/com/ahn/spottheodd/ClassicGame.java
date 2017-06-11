package com.ahn.spottheodd;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class ClassicGame extends AppCompatActivity {
    private int score=0, size, uniquePosition, data, color, distinctChange;
    float factor;
    private PauseDialog dialog = new PauseDialog();
    boolean resume;
    GridView gridView;
    private TextView scoreView, time;
    CountDownTimer timer;
    ImageAdapter imageAdapter;
    Drawable uniform, distinct;
    GameMethods methods;
    long timeLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT>20){
            setTheme(R.style.AppTheme);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF0B9EBD"));
        }
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.classic_game);
        gridView=(GridView)findViewById(R.id.gridview);
        timeLeft = intent.getLongExtra("gameTime",46000);
        score = intent.getIntExtra("score",0);
        color=intent.getIntExtra("color",0);
        distinctChange = intent.getIntExtra("distinct", 0);
        uniquePosition = intent.getIntExtra("pos", 0);
        resume = intent.getBooleanExtra("resume", false);
        factor = intent.getFloatExtra("factor", 0.7f);
        // Create the Custom Adapter Object
        imageAdapter = new ImageAdapter(this);
        size = imageAdapter.getCount();
        changeGrid(uniquePosition,color,distinctChange,resume);
        scoreView = (TextView) findViewById(R.id.score);
        time = (TextView) findViewById(R.id.time);
        scoreView.setText(""+score);
    }

    public void startCountDownTimer(){
        timer = new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Handling touch/click Event on GridView Item
                timeLeft = millisUntilFinished;
                long sec = (millisUntilFinished % 60000) / 1000;
                time.setText(""+sec);
                gridView.setOnItemClickListener(new GridView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                        if(position==uniquePosition){
                            score++;
                            scoreView.setText(""+score);
                            factor-=0.015;
                            if(factor<0.16f)
                                factor =0.16f;
                            changeGrid(0,0,0,false);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            @Override
            public void onFinish() {
                Intent endGame = new Intent(getApplicationContext(), EndGame.class);
                endGame.putExtra("intentCheck", Selection.CLASSIC);
                db.addScoreClassic(score);
                data=db.getAllScoresClassic();
                endGame.putExtra("data",data);
                endGame.putExtra("score", score);
                startActivity(endGame);
                finish();
            }
        }.start();
    }

    public void changeGrid(int pos, int col, int distinctCol, boolean resume){
        if(Build.VERSION.SDK_INT>21){
            uniform = getDrawable(R.drawable.uniform_square);
            distinct = getDrawable(R.drawable.distinct_square);
        }
        else{
            uniform = getResources().getDrawable(R.drawable.uniform_square);
            distinct = getResources().getDrawable(R.drawable.distinct_square);
        }
        methods = new GameMethods();
        String[] values = methods.changeColor(factor);
        String colorHex = "#"+values[0];
        String distinctChangeHex = "#"+values[1];
        if(!resume){
            uniquePosition = (int) (Math.random()*size);
            color = Color.parseColor(colorHex);
            distinctChange = Color.parseColor(distinctChangeHex);
        }
        if(resume){
            uniquePosition=pos;
            color = col;
            distinctChange = distinctCol;
        }
        imageAdapter.setUniquePos(uniquePosition);
        uniform.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        distinct.setColorFilter(distinctChange, PorterDuff.Mode.SRC_ATOP);
        gridView.setAdapter(imageAdapter);
    }

    DatabaseHandler db = new DatabaseHandler(this);
    public void pause(View v){
        dialog.classicValues(timeLeft,score, Selection.CLASSIC, color, distinctChange, true, uniquePosition, factor);
        onPause();
        dialog.show(getFragmentManager(), "my_dialog");
        dialog.setCancelable(false);
        }

    @Override
    public void finish(){
        super.finish();
        distinct.clearColorFilter();
        uniform.clearColorFilter();
    }

    @Override
    public void onPause(){
        super.onPause();
        timer.cancel();
    }

    @Override
    public void onResume(){
        super.onResume();
        startCountDownTimer();
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
        timer.cancel();
        finish();
    }

}
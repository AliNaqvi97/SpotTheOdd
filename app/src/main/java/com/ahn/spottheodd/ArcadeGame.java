package com.ahn.spottheodd;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class ArcadeGame extends AppCompatActivity {
    private int score, size, uniquePosition, lives = 10, data;
    float factor=0.7f;
    private PauseDialog dialog;
    GridView gridView;
    private TextView scoreView, livesView;
    ImageAdapter imageAdapter;
    Drawable uniform, distinct;
    GameMethods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT>20){
            setTheme(R.style.AppTheme);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF0B9EBD"));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arcade_game);
        gridView=(GridView)findViewById(R.id.gridview);
        // Create the Custom Adapter Object
        imageAdapter = new ImageAdapter(this);
        size = imageAdapter.getCount();
        changeGrid();
        scoreView = (TextView) findViewById(R.id.score);
        livesView = (TextView) findViewById(R.id.lives);
        scoreView.setText(""+score);
        livesView.setText("= "+lives);

        final DatabaseHandler db = new DatabaseHandler(this);

        // Handling touch/click Event on GridView Item
        gridView.setOnItemClickListener(new GridView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                if(position==uniquePosition){
                    score++;
                    lives++;
                    scoreView.setText(""+score);
                    livesView.setText("= "+lives);
                    factor-=0.015;
                    if(factor<0.16f)
                        factor =0.16f;
                    changeGrid();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                    lives--;
                    livesView.setText("= "+lives);
                }
                if(lives<=0){
                    Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_SHORT).show();
                    Intent endGame = new Intent(getApplicationContext(), EndGame.class);
                    endGame.putExtra("intentCheck", Selection.ARCADE);
                    db.addScoreArcade(score);
                    data=db.getAllScoresArcade();
                    endGame.putExtra("data",data);
                    endGame.putExtra("score", score);
                    startActivity(endGame);
                    finish();
                }
            }
        });
    }

    public void changeGrid(){
        uniquePosition = (int) (Math.random()*size);
        imageAdapter.setUniquePos(uniquePosition);
        methods = new GameMethods();

        if(Build.VERSION.SDK_INT>21){
            uniform = getDrawable(R.drawable.uniform_square);
            distinct = getDrawable(R.drawable.distinct_square);
        }
        else{
            uniform = getResources().getDrawable(R.drawable.uniform_square);
            distinct = getResources().getDrawable(R.drawable.distinct_square);
        }

        String[] values = methods.changeColor(factor);
        String colorHex = "#"+values[0];
        String distinctChangeHex = "#"+values[1];
        int color = Color.parseColor(colorHex);
        int distinctChange = Color.parseColor(distinctChangeHex);
        uniform.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        distinct.setColorFilter(distinctChange, PorterDuff.Mode.SRC_ATOP);
        gridView.setAdapter(imageAdapter);
    }

    public void pause(View v){
        dialog = new PauseDialog();
        dialog.getIntent(Selection.ARCADE);
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
    public void onBackPressed(){
        Intent g = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(g);
        finish();
    }
}

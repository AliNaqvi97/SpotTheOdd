package com.ahn.spottheodd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Selection extends AppCompatActivity {

    public InstructionsDialog dialog;
    public static final String PREFS_NAME = "MyPrefsFile1";
    public static final String CLASSIC = "from_classic";
    public static final String ARCADE = "from_arcade";
    public static final String HARD = "from_hard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT>20){
            setTheme(R.style.AppTheme);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF0B9EBD"));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);
        getIntent();
    }


    public void PlayClassic(View view){
        dialog = new InstructionsDialog();
        dialog.getIntent(CLASSIC);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("skipMessageClassic", "NOT checked");
        if (!skipMessage.equals("checked"))
            dialog.show(getFragmentManager(), "my_dialog");
        else if(skipMessage.equals("checked")) {
            Intent game = new Intent(getApplicationContext(), ClassicGame.class);
            startActivity(game);
            finish();
        }
    }
    public void PlayArcade(View view){
        dialog = new InstructionsDialog();
        dialog.getIntent(ARCADE);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("skipMessageArcade", "NOT checked");
        if (!skipMessage.equals("checked"))
            dialog.show(getFragmentManager(), "my_dialog");
        else if(skipMessage.equals("checked")) {
            Intent game = new Intent(getApplicationContext(), ArcadeGame.class);
            startActivity(game);
            finish();
        }
    }
    public void PlayHard(View view){
        dialog = new InstructionsDialog();
        dialog.getIntent(HARD);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("skipMessageHard", "NOT checked");
        if (!skipMessage.equals("checked"))
            dialog.show(getFragmentManager(), "my_dialog");
        else if(skipMessage.equals("checked")) {
            Intent game = new Intent(getApplicationContext(), HardGame.class);
            startActivity(game);
            finish();
        }
    }
    @Override
    public void onBackPressed(){
        Intent g = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(g);
        finish();
    }
}

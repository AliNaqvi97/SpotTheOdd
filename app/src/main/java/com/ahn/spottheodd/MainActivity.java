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

public class MainActivity extends AppCompatActivity {

    private Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT>20){
            setTheme(R.style.AppTheme);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#FF0B9EBD"));
    }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (Button) findViewById(R.id.play);
    }

    public void PlayGame(View view){
        Intent i = new Intent(getApplicationContext(), Selection.class);
        startActivity(i);
        finish();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        finish();
    }
}

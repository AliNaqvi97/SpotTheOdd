package com.ahn.spottheodd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ali on 2/26/2016.
 */
public class PauseDialog extends DialogFragment {

    LayoutInflater inflater;
    View v;
    private Button resume, quit, restart;
    private long timeLeft;
    private String intentCheck;
    int classicScore, gridColor,distinctChange, position;
    float factor;

    public void classicValues(long gameTimer, int score, String activity, int color,
                              int distinceColor, boolean resume, int pos, float lightFactor){
        intentCheck = activity;
        timeLeft=gameTimer;
        classicScore = score;
        gridColor = color;
        distinctChange = distinceColor;
        position=pos;
        factor = lightFactor;
    }

    public void getIntent(String activity){
        intentCheck = activity;
    }


    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.pause_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        resume = (Button) v.findViewById(R.id.resume);
        restart = (Button) v.findViewById(R.id.restart);
        quit = (Button) v.findViewById(R.id.quit);


       resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(intentCheck) {
                    case "from_classic":
                        Intent game = new Intent(getActivity(), ClassicGame.class);
                        game.putExtra("score", classicScore);
                        game.putExtra("gameTime", timeLeft);
                        game.putExtra("resume", true);
                        game.putExtra("pos",position);
                        game.putExtra("color", gridColor);
                        game.putExtra("distinct",distinctChange);
                        game.putExtra("factor", factor);
                        startActivity(game);
                        getActivity().finish();
                        getDialog().dismiss();
                        break;
                    case "from_arcade":
                        getDialog().dismiss();
                        break;
                    case "from_hard":
                        getDialog().dismiss();
                        break;
                }

            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(intentCheck) {
                    case Selection.CLASSIC:
                        Intent classicGame = new Intent(getActivity(), ClassicGame.class);
                        startActivity(classicGame);
                        getActivity().finish();
                        getDialog().dismiss();
                        break;
                    case Selection.ARCADE:
                        Intent arcadeGame = new Intent(getActivity(), ArcadeGame.class);
                        startActivity(arcadeGame);
                        getActivity().finish();
                        break;
                    case Selection.HARD:
                        Intent hardGame = new Intent(getActivity(), HardGame.class);
                        startActivity(hardGame);
                        getActivity().finish();
                        break;
                }
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quit = new Intent(getActivity(), MainActivity.class);
                startActivity(quit);
                getActivity().finish();
            }
        });
        return builder.create();
    }
}

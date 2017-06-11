package com.ahn.spottheodd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Ali on 2/26/2016.
 */
public class InstructionsDialog extends DialogFragment{

    LayoutInflater inflater;
    View v;
    private TextView instructions, doNotShowText;
    private String intentCheck;
    private Button okay, cancel;
    private CheckBox doNotShowAgain;
    public static final String PREFS_NAME = "MyPrefsFile1";

    public void getIntent(String activity){
        intentCheck = activity;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.instructions_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        okay = (Button) v.findViewById(R.id.okay);
        cancel = (Button) v.findViewById(R.id.cancel);
        instructions = (TextView) v.findViewById(R.id.instructions_text);
        doNotShowAgain = (CheckBox) v.findViewById(R.id.checkbox);
        doNotShowText = (TextView) v.findViewById(R.id.do_not_show);

        switch(intentCheck){
            case Selection.CLASSIC:
              instructions.setText(R.string.classic_instructions);
                break;
            case Selection.ARCADE:
                instructions.setText(R.string.arcade_instructions);
                break;
            case Selection.HARD:
                instructions.setText(R.string.hard_instructions);
                break;
        }

        doNotShowText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(doNotShowAgain.isChecked()){
                    doNotShowAgain.setChecked(false);}
                else{
                    doNotShowAgain.setChecked(true);
                }
            }
        });

        okay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent classicGame = new Intent(getActivity(), ClassicGame.class);
                Intent arcadeGame = new Intent(getActivity(), ArcadeGame.class);
                Intent hardGame = new Intent(getActivity(), HardGame.class);
                String checkBoxResult = "NOT checked";
                if (doNotShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();

                switch (intentCheck) {
                    case Selection.CLASSIC:
                        editor.putString("skipMessageClassic", checkBoxResult);
                        editor.commit();
                        startActivity(classicGame);
                        getActivity().finish();
                        break;
                    case Selection.ARCADE:
                        editor.putString("skipMessageArcade", checkBoxResult);
                        editor.commit();
                        startActivity(arcadeGame);
                        getActivity().finish();
                        break;
                    case Selection.HARD:
                        editor.putString("skipMessageHard", checkBoxResult);
                        editor.commit();
                        startActivity(hardGame);
                        getActivity().finish();
                        break;
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                getDialog().dismiss();
            }
        });
        return builder.create();
    }
}


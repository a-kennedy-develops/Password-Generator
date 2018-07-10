package com.example.alexk.passwordgenerator;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements DoAsyncTask.IResult {

    ArrayList<String> passwordList = new ArrayList<>();
//    TextView passwordResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declaring necessary fields for UI components
        final TextView passwordCount = findViewById(R.id.textView_pwCountResult);
        final TextView passwordLength = findViewById(R.id.textView_pwLengthsResult);

        SeekBar seekBarCount = findViewById(R.id.seekBar_count);
        SeekBar seekBarLength = findViewById(R.id.seekBar_length);
        Button buttonGenerate = findViewById(R.id.button_generate);
        ProgressBar progressBar = findViewById(R.id.progressBar_horizontal);

        passwordCount.setText(String.valueOf(seekBarCount.getProgress() + 1)); // Setting initial text here.
                                                                               // The addition creates a minimum password count selectable.
        passwordLength.setText(String.valueOf(seekBarLength.getProgress() + 5)); // ^^^

        seekBarCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passwordCount.setText(String.valueOf(progress + 1));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passwordLength.setText(String.valueOf(progress + 5));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoAsyncTask doAsyncTask = new DoAsyncTask(MainActivity.this); // Creating AsyncTask instance
                doAsyncTask.execute(Integer.parseInt(passwordCount.getText().toString()), // Parsing string values from TextViews as Integer
                        Integer.parseInt(passwordLength.getText().toString())); // Executing Async Task
            }
        });
    }

    @Override
    public void handleData(final ArrayList<String> newPasswordList) {
        final String [] passwordResultStrings = new String[newPasswordList.size()];
        for (int i = 0; i < passwordResultStrings.length; i++) {
            passwordResultStrings[i] = newPasswordList.get(i);
        }
        final TextView passwordResult = findViewById(R.id.textView_result);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Passwords: Select one")
                .setItems(passwordResultStrings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                passwordResult.setText(passwordResultStrings[which]);
            }
        }).setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

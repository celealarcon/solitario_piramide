package com.example.cele.piramide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void listenerExit(View view){
        finishAffinity();
    }

    public void listenerInstructions(View view){
        Intent intent = new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    }

    public void listenerPlay(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

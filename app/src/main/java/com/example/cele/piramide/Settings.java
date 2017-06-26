package com.example.cele.piramide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void listenerDorsoAzul(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("color", 1);
        startActivity(intent);
    }

    public void listenerDorsoRojo(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("color", 0);
        startActivity(intent);
    }
}

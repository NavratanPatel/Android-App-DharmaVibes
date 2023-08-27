package com.project.navratan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //starus bar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#EF6306"));
        // Set BackgroundDrawable
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);
        //Top
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.purple_200));
    }
    public void Login(View v)
    {
//        public Intent i1= new Intent(.MainActivity.Class);
        button1= findViewById(R.id.button1);
        Toast.makeText(this, "Successfully Login...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Activity2.class);
        startActivity(intent);

    }
}
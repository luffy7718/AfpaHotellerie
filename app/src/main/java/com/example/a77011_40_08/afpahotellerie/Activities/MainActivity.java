package com.example.a77011_40_08.afpahotellerie.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a77011_40_08.afpahotellerie.R;

public class MainActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        Intent intent = null;
            intent = new Intent(getApplicationContext(), HomeActivity.class);

            startActivity(intent);
            finish();

    }
}

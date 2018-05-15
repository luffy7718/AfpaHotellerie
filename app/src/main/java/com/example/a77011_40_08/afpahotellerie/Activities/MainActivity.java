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
       goToHome();
    }
    private void goToHome() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= 23) {
            intent = new Intent(getApplicationContext(), PermissionActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        }


        if (intent != null) {
            startActivity(intent);
            finish();
        }
    }
}

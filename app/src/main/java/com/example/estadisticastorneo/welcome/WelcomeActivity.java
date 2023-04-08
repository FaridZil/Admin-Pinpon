package com.example.estadisticastorneo.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.estadisticastorneo.logger.HomeActivity;
import com.example.estadisticastorneo.view.MainActivity;
import com.example.estadisticastorneo.R;
import com.example.estadisticastorneo.logger.DataCompleteActivity;
import com.example.estadisticastorneo.logger.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferences mPref,mPref2,mPreModeNight;
    private boolean nightMode;
    private FirebaseAuth _firebaseauth;
    private DatabaseReference _databasereference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mPref = getApplicationContext().getSharedPreferences("carousel",MODE_PRIVATE);
        mPref2 = getApplicationContext().getSharedPreferences("locality",MODE_PRIVATE);
        mPreModeNight = getApplicationContext().getSharedPreferences("MODE",MODE_PRIVATE);
        nightMode = mPreModeNight.getBoolean("nightMode",false);
        if(nightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        _firebaseauth = FirebaseAuth.getInstance();
        _databasereference = FirebaseDatabase.getInstance().getReference();
        boolean view = mPref.getBoolean("carouselView",false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (true) {
                    FirebaseUser user = _firebaseauth.getCurrentUser();
                    if (user!= null){
                        consultaDatosUser(user);
                    }else {
                        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Intent intent = new Intent(WelcomeActivity.this, CarouselActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }
    public void updateUI(FirebaseUser user){
        if(user!= null){
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
    public void consultaDatosUser(FirebaseUser user){
        _databasereference.child("Users").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().child("nombre").getValue()!= null){
                    updateUI(user);
                }else{
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
//                    Intent intent = new Intent(WelcomeActivity.this, DataCompleteActivity.class);
//                    startActivity(intent);
//                    finish();
                }

            }
        });
    }
}

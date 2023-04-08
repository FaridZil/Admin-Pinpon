package com.example.estadisticastorneo.logger;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.estadisticastorneo.R;

public class HomeActivity extends AppCompatActivity {
    private TextView homeTitle;
    private Button signUp;
    private Button signIn;
    private ImageView signGoogle;
    private ImageView imgGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_home);
        homeTitle = findViewById(R.id.textTitleWelcomeHome);
        signUp = findViewById(R.id.btn_sign_up_welcome_home);
        signIn = findViewById(R.id.btn_sign_in_welcome_home);
        signGoogle = findViewById(R.id.btn_sign_google_welcome_home);
        imgGoogle = findViewById(R.id.imageView_btn_sign_google_welcome_home);
        Drawable drawable = getResources().getDrawable(R.drawable.button_colors_green);
        signUp.setBackgroundDrawable(drawable);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}

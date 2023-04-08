package com.example.estadisticastorneo.logger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estadisticastorneo.R;

public class PasswordRestarActivity extends AppCompatActivity {
    Bundle bundle;
    private TextView _mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_restar);
        _mail = findViewById(R.id.text345);
        bundle = this.getIntent().getExtras();
        _mail.setText(""+bundle.getString("email"));
    }
    public void toLogin(View view){
        Intent intent = new Intent(PasswordRestarActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

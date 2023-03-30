package com.example.estadisticastorneo.logger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estadisticastorneo.R;
import com.google.firebase.auth.FirebaseAuth;

public class EmailVerficationActivity extends AppCompatActivity {
    private TextView _mail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verfication);
        mAuth = FirebaseAuth.getInstance();
        _mail = findViewById(R.id.text34);
        _mail.setText(""+mAuth.getCurrentUser().getEmail());
        mAuth.signOut();
    }
    public void toLogin(View view){
        Intent intent = new Intent(EmailVerficationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

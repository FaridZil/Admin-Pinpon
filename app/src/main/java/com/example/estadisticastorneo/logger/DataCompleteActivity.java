package com.example.estadisticastorneo.logger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.estadisticastorneo.R;
import com.example.estadisticastorneo.bean.User;
import com.example.estadisticastorneo.view.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class DataCompleteActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference _databasereference;
    FirebaseUser userA;
    EditText _nombre;
    View btn_registrarte;
    User user;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_complete);
        mAuth = FirebaseAuth.getInstance();
        _databasereference = FirebaseDatabase.getInstance().getReference();
        _nombre = findViewById(R.id.ediTxtNombreData);
        btn_registrarte = findViewById(R.id.btn_registrarse);
        btn_registrarte.setBackgroundResource(R.drawable.btndesha);
    }


    public void agregaUsuario(View view){
        String nombre=_nombre.getText().toString();
        if(!nombre.isEmpty()) {
            user = new User();
            user.setNombre(mAuth.getCurrentUser().getDisplayName());
            user.setEmail(mAuth.getCurrentUser().getEmail());
            user.setPassword("password");
            user.setActive(false);
            addToDataBase(mAuth.getCurrentUser().getUid());
        }else{
            Toast.makeText(DataCompleteActivity.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }



    private void addToDataBase(String userID){
        if(user!= null){
            _databasereference.child("Users").child(userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Log.e("Usuario","Se agrego correctamente");
                        Intent intent = new Intent(DataCompleteActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(DataCompleteActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

package com.example.estadisticastorneo;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.estadisticastorneo.providers.GeofireProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class My_profile extends Fragment {
    private FirebaseAuth _firebaseAuth;
    private DatabaseReference _databasereference;
    private GeofireProvider _geofireProvider;
    private SharedPreferences _preferences;
    private SharedPreferences _preferences2;
    private SharedPreferences.Editor _editor;
    private boolean nightMode;

    private SwitchCompat switchMode;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inicializando las variables para obtener la referencia a la base de datos
        _firebaseAuth = FirebaseAuth.getInstance();
        _databasereference = FirebaseDatabase.getInstance().getReference();

        // Inicializando la variable para almacenar y consultar datos en tiempo real
        _geofireProvider = new GeofireProvider();

        // Inicializando la variable para mantener la percistencia de la sesion
        _preferences = getContext().getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);

        //modo view
        switchMode = v.findViewById(R.id.switchMode);
        _preferences2 = getContext().getSharedPreferences("MODE",MODE_PRIVATE);
        nightMode = _preferences2.getBoolean("nightMode",false);
        if(nightMode){
            switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        switchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();
                if(nightMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    _editor = _preferences2.edit();
                    _editor.putBoolean("nightMode",false);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    _editor = _preferences2.edit();
                    _editor.putBoolean("nightMode",true);
                }
                _editor.apply();
            }
        });
        return v;
    }
}

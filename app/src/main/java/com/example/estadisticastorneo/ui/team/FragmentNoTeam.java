package com.example.estadisticastorneo.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.estadisticastorneo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_no_team extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference _databasereference;
    private ListView listTeam;

    public Fragment_no_team(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        _databasereference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_no_team,container,false);
        listTeam = v.findViewById(R.id.list_item_profile_no_team);
        return v;
    }
}

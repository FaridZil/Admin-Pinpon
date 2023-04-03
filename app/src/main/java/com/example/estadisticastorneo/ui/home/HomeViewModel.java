package com.example.estadisticastorneo.ui.home;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.estadisticastorneo.bean.Confrontation;
import com.example.estadisticastorneo.props.GestionarAdapterDinamicViewConfrontation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Handler;
import android.os.Looper;


public class HomeViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private DatabaseReference _databasereference;
    private FirebaseUser user;
    private final MutableLiveData<Confrontation> confrontationMutableLiveData;

    public HomeViewModel() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        _databasereference = FirebaseDatabase.getInstance().getReference();
        confrontationMutableLiveData = new MutableLiveData<>();
        loadPingPongData();
    }

    public void loadPingPongData() {
        Confrontation mConfrontation = new Confrontation();
        _databasereference.child("Confrontations").child("PingPong").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String idKey = snapshot.getKey();
                String imgViewTeam1 = snapshot.child("imgViewTeam1").getValue(String.class);
                String imgViewTeam2 = snapshot.child("imgViewTeam2").getValue(String.class);
                String textNameTeam1 = snapshot.child("textNameTeam1").getValue(String.class);
                String textNameTeam2 = snapshot.child("textNameTeam2").getValue(String.class);
                String textViewPosition = snapshot.child("textViewPosition").getValue(String.class);
                int textViewScoreTm1 = snapshot.child("textViewScoreTm1").getValue(Integer.class);
                int textViewScoreTm2 = snapshot.child("textViewScoreTm2").getValue(Integer.class);
                String textViewTime = snapshot.child("textViewTime").getValue(String.class);
                String txtTeamName1 = snapshot.child("txtTeamName1").getValue(String.class);
                String txtTeamName2 = snapshot.child("txtTeamName2").getValue(String.class);

                mConfrontation.addItem(idKey,imgViewTeam1,imgViewTeam2,textNameTeam1,textNameTeam2,txtTeamName1,txtTeamName2,textViewTime,textViewPosition,String.valueOf(textViewScoreTm1),String.valueOf(textViewScoreTm2));
                confrontationMutableLiveData.setValue(mConfrontation);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String idKey = snapshot.getKey();
                String imgViewTeam1 = snapshot.child("imgViewTeam1").getValue(String.class);
                String imgViewTeam2 = snapshot.child("imgViewTeam2").getValue(String.class);
                String textNameTeam1 = snapshot.child("textNameTeam1").getValue(String.class);
                String textNameTeam2 = snapshot.child("textNameTeam2").getValue(String.class);
                String textViewPosition = snapshot.child("textViewPosition").getValue(String.class);
                int textViewScoreTm1 = snapshot.child("textViewScoreTm1").getValue(Integer.class);
                int textViewScoreTm2 = snapshot.child("textViewScoreTm2").getValue(Integer.class);
                String textViewTime = snapshot.child("textViewTime").getValue(String.class);
                String txtTeamName1 = snapshot.child("txtTeamName1").getValue(String.class);
                String txtTeamName2 = snapshot.child("txtTeamName2").getValue(String.class);
                mConfrontation.changedItem(idKey,imgViewTeam1,imgViewTeam2,textNameTeam1,textNameTeam2,txtTeamName1,txtTeamName2,textViewTime,textViewPosition,String.valueOf(textViewScoreTm1),String.valueOf(textViewScoreTm2));
                confrontationMutableLiveData.setValue(mConfrontation);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public LiveData<Confrontation> getListConfrontation() { return confrontationMutableLiveData;}

}
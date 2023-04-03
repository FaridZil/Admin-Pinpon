package com.example.estadisticastorneo.providers;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.LocationCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeofireProvider {
    DatabaseReference _databasereference;
    GeoFire _geoFire;
    FirebaseAuth mAuth;
    GeoLocation _location;
    private String idDriver;
    private boolean activeGeoProvider;

    public GeofireProvider() {
        _databasereference = FirebaseDatabase.getInstance().getReference().child("Users");
        _geoFire = new GeoFire(_databasereference);
        mAuth =  FirebaseAuth.getInstance();
        this.activeGeoProvider = true;
        _location= new GeoLocation(0.0000,0.0000);
    }

    public void inactive(){this.activeGeoProvider=false;}
    public boolean getActive(){return activeGeoProvider;}

    public String getID(){
        return mAuth.getCurrentUser().getUid();
    }

    public boolean session(){
        boolean active= false;
        if(mAuth.getCurrentUser()!=null){
            active=true;
        }else{
            active = false;
        }
        return  active;
    }

}

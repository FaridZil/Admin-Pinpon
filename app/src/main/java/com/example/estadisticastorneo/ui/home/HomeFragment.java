package com.example.estadisticastorneo.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.estadisticastorneo.R;
import com.example.estadisticastorneo.bean.Confrontation;
import com.example.estadisticastorneo.databinding.FragmentHomeBinding;
import com.example.estadisticastorneo.databinding.FragmentSlideshowBinding;
import com.example.estadisticastorneo.props.GestionarAdapterDinamicViewConfrontation;
import com.example.estadisticastorneo.ui.slideshow.SlideshowViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private FirebaseAuth mAuth;
    private GestionarAdapterDinamicViewConfrontation listDinamic;
    private DatabaseReference _databasereference;
    private FirebaseUser user;
    private ListView lista;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        _databasereference = FirebaseDatabase.getInstance().getReference();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lista = binding.listItemTeam;
        createListAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void createListAdapter(){
        lista.clearChoices();
        homeViewModel.getListConfrontation().observe(getViewLifecycleOwner(), new Observer<Confrontation>() {
            @Override
            public void onChanged(Confrontation confrontation) {
                if(getActivity() != null){
                    listDinamic = new GestionarAdapterDinamicViewConfrontation(
                            confrontation.getImgViewTeam1(),
                            confrontation.getImgViewTeam2(),
                            confrontation.getTextNameTeam1(),
                            confrontation.getTextNameTeam2(),
                            confrontation.getTxtTeamName1(),
                            confrontation.getTxtTeamName2(),
                            confrontation.getTextViewTime(),
                            confrontation.getTextViewPosition(),
                            confrontation.getTextViewScoreTm1(),
                            confrontation.getTextViewScoreTm2(),getContext()
                    );
                    lista.setAdapter(listDinamic);
                }
            }
        });
    }
}
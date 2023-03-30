package com.example.estadisticastorneo.props;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.estadisticastorneo.R;

import java.util.List;

public class MiAdaptador extends ArrayAdapter<String> {
    public MiAdaptador(Context context, List<String> datos) {
        super(context, android.R.layout.simple_spinner_item, datos);
        setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = super.getView(position, convertView, parent);
        // Aquí puedes personalizar el aspecto visual del elemento seleccionado del Spinner
        TextView textView = vista.findViewById(android.R.id.text1);
        textView.setTextSize(18);
        return vista;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View vista = super.getDropDownView(position, convertView, parent);
        // Aquí puedes personalizar el aspecto visual de los elementos de la lista desplegable
        return vista;
    }
}

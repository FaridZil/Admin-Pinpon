package com.example.estadisticastorneo.props;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.estadisticastorneo.R;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class GestionarAdapterDinamicViewConfrontation extends BaseAdapter {
    private int[] teamIcons = {R.drawable.ic_futbol, R.drawable.ic_ping_pong};
    private List<String> imgViewTeam1;
    private List<String> imgViewTeam2;
    private List<String> textNameTeam1;
    private List<String> textNameTeam2;
    private List<String> txtTeamName1;
    private List<String> txtTeamName2;
    private List<String> textViewTime;
    private List<String> textViewPosition;
    private List<String> textViewScoreTm1;
    private List<String> textViewScoreTm2;
    private static LayoutInflater inflater= null;
    private Context contexto;

    public GestionarAdapterDinamicViewConfrontation(List<String> imgViewTeam1, List<String> imgViewTeam2, List<String> textNameTeam1, List<String> textNameTeam2, List<String> txtTeamName1, List<String> txtTeamName2, List<String> textViewTime, List<String> textViewPosition, List<String> textViewScoreTm1, List<String> textViewScoreTm2, Context contexto) {
        this.imgViewTeam1 = imgViewTeam1;
        this.imgViewTeam2 = imgViewTeam2;
        this.textNameTeam1 = textNameTeam1;
        this.textNameTeam2 = textNameTeam2;
        this.txtTeamName1 = txtTeamName1;
        this.txtTeamName2 = txtTeamName2;
        this.textViewTime = textViewTime;
        this.textViewPosition = textViewPosition;
        this.textViewScoreTm1 = textViewScoreTm1;
        this.textViewScoreTm2 = textViewScoreTm2;
        this.contexto = contexto;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return txtTeamName1.size();
    }
    @Override
    public  Object getItem(int posicion) {
        return posicion;
    }
    @Override
    public  long  getItemId(int posicion) {
        return posicion;
    }

    public class Holder
    {
        ImageView imageTeam1;
        ImageView imageTeam2;
        TextView textNameTeam1;
        TextView textNameTeam2;
        TextView txtTeamName1;
        TextView txtTeamName2;
        TextView textViewTime;
        TextView textViewPosition;
        TextView textViewScoreTm1;
        TextView textViewScoreTm2;
    }

    @Override
    public View getView(final int posicion, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View fila;
        fila = inflater.inflate(R.layout.item_list_team, null);

        holder.imageTeam1 = fila.findViewById(R.id.imageTeam1);
        holder.imageTeam2 = fila.findViewById(R.id.imageTeam2);
        holder.textNameTeam1 = fila.findViewById(R.id.textNameTeam1);
        holder.textNameTeam2 = fila.findViewById(R.id.textNameTeam2);
        holder.txtTeamName1 = fila.findViewById(R.id.txtTeamName1);
        holder.txtTeamName2 = fila.findViewById(R.id.txtTeamName2);
        holder.textViewTime = fila.findViewById(R.id.textViewTime);
        holder.textViewPosition = fila.findViewById(R.id.textViewPosition);
        holder.textViewScoreTm1 = fila.findViewById(R.id.textViewScoreTm1);
        holder.textViewScoreTm2 = fila.findViewById(R.id.textViewScoreTm2);
        if(Integer.parseInt(textViewScoreTm1.get(posicion))>Integer.parseInt(textViewScoreTm2.get(posicion))){
            holder.textViewScoreTm1.setTextColor(Color.parseColor("#0EF312"));
            holder.textViewScoreTm2.setTextColor(Color.parseColor("#FFFFFF"));
        }else if(Integer.parseInt(textViewScoreTm1.get(posicion))==Integer.parseInt(textViewScoreTm2.get(posicion))){
            holder.textViewScoreTm1.setTextColor(Color.parseColor("#FFC107"));
            holder.textViewScoreTm2.setTextColor(Color.parseColor("#FFC107"));
        }else{
            holder.textViewScoreTm1.setTextColor(Color.parseColor("#FFFFFF"));
            holder.textViewScoreTm2.setTextColor(Color.parseColor("#0EF312"));
        }
        holder.imageTeam1.setImageResource(teamIcons[0]);
        holder.imageTeam2.setImageResource(teamIcons[1]);
        holder.textNameTeam1.setText(textNameTeam1.get(posicion));
        holder.textNameTeam2.setText(textNameTeam2.get(posicion));
        holder.txtTeamName1.setText(txtTeamName1.get(posicion));
        holder.txtTeamName2.setText(txtTeamName2.get(posicion));
        holder.textViewTime.setText(textViewTime.get(posicion));

        // Obtener la hora almacenada en el holder y convertirla a LocalTime
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalTime storedTime = LocalTime.parse(holder.textViewTime.getText().toString(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // Obtener la hora actual
                    LocalTime currentTime = LocalTime.now();
                    // Calcular la duración entre la hora almacenada y la hora actual
                    Duration duration = Duration.between(storedTime, currentTime);
                    // Obtener los valores de horas, minutos y segundos de la duración
                    long hours = duration.toHours();
                    long minutes = duration.toMinutes() % 60;
                    long seconds = duration.getSeconds() % 60;
                    holder.textViewTime.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                    handler.postDelayed(this,1000);
                }
            });
            // Actualizar el texto del TextView con el tiempo transcurrido

        } else {
            holder.textViewTime.setVisibility(View.INVISIBLE);
        }

        holder.textViewPosition.setText(textViewPosition.get(posicion));
        holder.textViewScoreTm1.setText(textViewScoreTm1.get(posicion));
        holder.textViewScoreTm2.setText(textViewScoreTm2.get(posicion));


        return fila;
    }

}

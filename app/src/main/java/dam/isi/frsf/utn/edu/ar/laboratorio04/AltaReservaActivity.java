package dam.isi.frsf.utn.edu.ar.laboratorio04;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.TestReceiver;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.Utils;

public class AltaReservaActivity extends AppCompatActivity {

    private ListView listaReserva;
    private ReservaAdapter adaptadorReserva;
    private Button botonReservar;
    private Boolean SeReserva;
    public static Reserva Seleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_reserva);
        listaReserva = (ListView) findViewById(R.id.ListView_ListaReserva);
        botonReservar = (Button) findViewById(R.id.Button_Reservar);
        Seleccionado= new Reserva();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Boolean esReserva = intent.getExtras().getBoolean("esReserva");
        botonReservar.setVisibility(View.INVISIBLE);
        if(esReserva) {
            ArrayList<Reserva> dptoElegido = (ArrayList<Reserva>) intent.getSerializableExtra("listaReservas");
            adaptadorReserva = new ReservaAdapter(AltaReservaActivity.this, dptoElegido);
            listaReserva.setAdapter(adaptadorReserva);
            listaReserva.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    view.setSelected(true);
                    for (int i = 0; i < listaReserva.getChildCount(); i++)
                        listaReserva.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    listaReserva.getChildAt(position).setBackgroundColor(Color.CYAN);
                    Seleccionado = (Reserva) listaReserva.getItemAtPosition(position);
                }
            });
            botonReservar.setVisibility(View.VISIBLE);
            botonReservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     dialogConfirmarReserva();

                }
            });
        }
        else{
            ArrayList<Reserva> dptoElegido = (ArrayList<Reserva>) intent.getSerializableExtra("listaReservas");
            adaptadorReserva = new ReservaAdapter(AltaReservaActivity.this, dptoElegido);
            listaReserva.setAdapter(adaptadorReserva);
        }

    }

    private void dialogConfirmarReserva(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Seguro que deseas reservar?:\nid: "+Seleccionado.getId()+"\n" +
                "precio: "+Seleccionado.getAlojamiento().getPrecio()+"\nDescripción: "+Seleccionado.getAlojamiento().getDescripcion()+"\nCiudad: "+Seleccionado.getAlojamiento().getCiudad()+"\nFecha Inicio: "+Seleccionado.getFechaInicio()+". Fecha Fin: "+Seleccionado.getFechaFin())
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Seleccionado.setConfirmada(true);
                        Departamento.buscarYConfirmarReserva(Seleccionado);
                        MainActivity.usuario.getReservas().add(Seleccionado);
                        Toast reserva = Toast.makeText(AltaReservaActivity.this,"La reserva está en pendiente: "+Seleccionado.getId()+"\n"+Seleccionado.getConfirmada()+"\n"+Seleccionado.getFechaInicio(),Toast.LENGTH_SHORT);
                        reserva.show();

                        AlarmManager manager = (AlarmManager) AltaReservaActivity.this.getSystemService(Context.ALARM_SERVICE);
                        Calendar cal = Utils.getTimeAfterInSecs(1);
                        Intent        intent  = new Intent(AltaReservaActivity.this, TestReceiver.class);
                        intent.putExtra("message","Single Shot Alarm");
                        PendingIntent pIntent = PendingIntent.getBroadcast(AltaReservaActivity.this, 2, intent,  PendingIntent.FLAG_CANCEL_CURRENT);

                        manager.setRepeating(AlarmManager.RTC,cal.getTimeInMillis(),(3*1000),pIntent);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }




}
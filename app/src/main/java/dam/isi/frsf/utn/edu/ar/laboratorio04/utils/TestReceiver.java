package dam.isi.frsf.utn.edu.ar.laboratorio04.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;

import dam.isi.frsf.utn.edu.ar.laboratorio04.AltaReservaActivity;
import dam.isi.frsf.utn.edu.ar.laboratorio04.MainActivity;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;



public class TestReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {


        if(/*System.currentTimeMillis()%3==0*/true){
            //Intent returnIntent = new Intent();
            //returnIntent.putExtra("result",true);

            //Toast.makeText(context,"Reserva confirmada",Toast.LENGTH_SHORT).show();
            cancelRepeatingAlarm(context);
            AltaReservaActivity.Seleccionado.setConfirmada(true);
            Departamento.buscarYConfirmarReserva(AltaReservaActivity.Seleccionado);
            MainActivity.usuario.getReservas().add(AltaReservaActivity.Seleccionado);
            sendNotificacion(context,"Reserva Confirmada");
        }
    }

    public void sendRepeatingAlarm(Context context)
    {
        Calendar cal = Utils.getTimeAfterInSecs(1);
        Intent intent = new Intent(context,TestReceiver.class);

        PendingIntent pi = getDistinctPendingIntent(context,intent,2);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        am.setRepeating(AlarmManager.RTC,cal.getTimeInMillis(),3*10,pi);

    }

    public void cancelRepeatingAlarm(Context context)
    {
        Intent intent = new Intent(context, TestReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    private void sendNotificacion(Context context,String message){

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nm = (NotificationManager) context.getSystemService(ns);

        Intent intent = new Intent(context,AltaReservaActivity.class);
        intent.putExtra("listaReservas",(ArrayList<Reserva>) MainActivity.usuario.getReservas());
        intent.putExtra("esReserva",false);


        PendingIntent pi = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context.getApplicationContext())
                .setContentIntent(pi)
                .setContentTitle("Notificación de Reservalo.com")
                .setSmallIcon(android.R.drawable.ic_menu_send)
                .setContentText(message)
                .setSound(MainActivity.usuario.getRingstone());
        nm.notify(1,mBuilder.build());

    }

    protected PendingIntent getDistinctPendingIntent(Context context,Intent intent, int requestId){
        return PendingIntent.getBroadcast(context,requestId,intent,0);
    }
}

package dam.isi.frsf.utn.edu.ar.laboratorio04.utils;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import android.os.Build;
import android.util.Log;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.Utils;


/**
 * Created by Usuario on 8/11/2016.
 */
public class TestReceiver extends BroadcastReceiver {
    private static final String tag = "TestReceiver";
    @Override
    public void onReceive(Context context, Intent intent){
        Log.d(tag, "intent=" + intent);
        String message= intent.getStringExtra("message");
        this.sendRepeatingAlarm(context);
//        Log.d(tag, message);
    }

    public void sendRepeatingAlarm(Context context)
    {
        Calendar cal = Utils.getTimeAfterInSecs(1);
        Intent intent = new Intent(context,TestReceiver.class);

        PendingIntent pi = getDistinctPendingIntent(context,intent,2);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        am.setRepeating(AlarmManager.RTC,cal.getTimeInMillis(),3*1000,pi);

    }
    protected PendingIntent getDistinctPendingIntent(Context context,Intent intent, int requestId){
        return PendingIntent.getBroadcast(context,requestId,intent,0);
    }

}

package dam.isi.frsf.utn.edu.ar.laboratorio04.utils;

import android.annotation.TargetApi;

import android.os.Build;

import java.util.Calendar;

/**
 * Created by Usuario on 8/11/2016.
 */
public class Utils {

        public static Calendar getTimeAfterInSecs(int secs) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND,secs);
            return cal;
        }
}

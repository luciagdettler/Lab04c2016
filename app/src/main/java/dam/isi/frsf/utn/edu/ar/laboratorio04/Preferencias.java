package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.os.Bundle;
import android.preference.PreferenceActivity;



import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;

/**
 * Created by Usuario on 9/11/2016.
 */
public class Preferencias extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       addPreferencesFromResource(R.xml.preferencias);
    }


}

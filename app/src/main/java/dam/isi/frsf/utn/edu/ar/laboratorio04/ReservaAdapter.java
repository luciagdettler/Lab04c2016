package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio04.R;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;



public class ReservaAdapter extends ArrayAdapter<Reserva> {
    private LayoutInflater inflater;
    private Context contexto;


    private Integer seleccionado;

    public ReservaAdapter(Context contexto, List<Reserva> items) {
        super(contexto, R.layout.fila_reserva, items);
        seleccionado=-1;
        inflater = LayoutInflater.from(contexto);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("#.##");
        View row = convertView;
        if (row == null) row = inflater.inflate(R.layout.fila_reserva, parent, false);
        TextView txtCiudad = (TextView) row.findViewById(R.id.Descripcion_Dpto);
        txtCiudad.setText("Ciudad: "+ this.getItem(position).getAlojamiento().getCiudad()+". Alojamiento: "+this.getItem(position).getAlojamiento().getDescripcion());
        TextView txtPrecio = (TextView) row.findViewById(R.id.r_precio);
        txtPrecio.setText("$" + (df.format(this.getItem(position).getAlojamiento().getPrecio())));
        TextView fechaInicio = (TextView) row.findViewById(R.id.r_fechaInicio);
        fechaInicio.setText(this.getItem(position).getFechaInicio()+".");
        TextView fechaFin = (TextView) row.findViewById(R.id.r_fechaFin);
        fechaFin.setText(this.getItem(position).getFechaFin()+".");
        return (row);

    }

    public void setSeleccionado(Integer seleccionado) {
        this.seleccionado = seleccionado;
    }
}

package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.BuscarDepartamentosTask;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.BusquedaFinalizadaListener;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.FormBusqueda;

public class ListaDepartamentosActivity extends AppCompatActivity implements BusquedaFinalizadaListener<Departamento> {

    private TextView tvEstadoBusqueda;
    private ListView listaAlojamientos;
    private DepartamentoAdapter departamentosAdapter;
    private List<Departamento> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alojamientos);
        lista= new ArrayList<>();
        listaAlojamientos= (ListView) findViewById(R.id.listaAlojamientos);
        tvEstadoBusqueda = (TextView) findViewById(R.id.estadoBusqueda);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Boolean esBusqueda = intent.getExtras().getBoolean("esBusqueda");
        if(esBusqueda){
            FormBusqueda fb = (FormBusqueda ) intent.getSerializableExtra("frmBusqueda");
            new BuscarDepartamentosTask(ListaDepartamentosActivity.this).execute(fb);
            tvEstadoBusqueda.setText("Buscando....");
            tvEstadoBusqueda.setVisibility(View.VISIBLE);
        }else{
            tvEstadoBusqueda.setVisibility(View.GONE);
            lista=Departamento.getAlojamientosDisponibles();
        }
        departamentosAdapter = new DepartamentoAdapter(ListaDepartamentosActivity.this,lista);
        listaAlojamientos.setAdapter(departamentosAdapter);
    }

    @Override
    public void busquedaFinalizada(List<Departamento> listaDepartamento) {
        //TODO implementar
       /* departamentosAdapter = new DepartamentoAdapter(ListaDepartamentosActivity.this,listaDepartamento);
        listaAlojamientos.setAdapter(departamentosAdapter);
        tvEstadoBusqueda.setVisibility(View.GONE);
*/
        tvEstadoBusqueda.setText("Búsqueda finalizada, departamentos encontrados: "+listaDepartamento.size());
        departamentosAdapter = new DepartamentoAdapter(ListaDepartamentosActivity.this,listaDepartamento);
        listaAlojamientos.setAdapter(departamentosAdapter);
        listaAlojamientos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Departamento item = (Departamento) parent.getItemAtPosition(position);
                Toast Seleccionado = Toast.makeText(ListaDepartamentosActivity.this,"El departamento seleccionado es: "+item.getId(),Toast.LENGTH_SHORT);
                Seleccionado.show();
                Log.i("Click", "click en el elemento " + position + " de mi ListView");
                dialogElegirDpto(item);

            }
        });

    }

    @Override
    public void busquedaActualizada(String msg) {

        tvEstadoBusqueda.setText(" Buscando..."+msg);
    }

    private void dialogElegirDpto(final Departamento item){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas reservar este departamento?:\n"+item.getId()+"\n"+item.getDescripcion()+"\n"+item.getCiudad())
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(ListaDepartamentosActivity.this,AltaReservaActivity.class);
                        i.putExtra("listaReservas",(ArrayList<Reserva>)item.getReservas());
                        i.putExtra("esReserva",true);

                        startActivity(i);
                        finish();


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

package com.uac.tp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ControladorDB controlador;

    ListView lista;

    ArrayList<Empleado> listaEmpleados;


    ListaEmpleadosAdapter adapter;
    SearchView buscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //listaEmpleados = new ArrayList<>();

        controlador = new ControladorDB(getApplicationContext());
        lista = findViewById(R.id.idListview);
        listaEmpleados = controlador.obtenerRegistros();

        adapter = new ListaEmpleadosAdapter(getApplicationContext(), R.layout.item_lista, listaEmpleados);
        lista.setAdapter(adapter);

        registerForContextMenu(lista);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Empleado> listaEmpleados = controlador.obtenerRegistros();
                ListaEmpleadosAdapter adaptador = new ListaEmpleadosAdapter(getApplicationContext(), R.layout.item_lista, listaEmpleados);
                lista.setAdapter(adaptador);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Actualización cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_lista, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.IdModificar:
                modificarRegistro(menuInfo.position);
                return true;
            case R.id.IdEliminar:
                borrarRegistro(menuInfo.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void modificarRegistro(int posicion) {
        Intent intent = new Intent(this, ActualizarActivity.class);
        intent.putExtra("indice", posicion);
        startActivityForResult(intent, 2);
    }

    private void borrarRegistro(int posicion) {
        int retorno = controlador.borrarRegistro(listaEmpleados.get(posicion));
        if (retorno == 1) {
            Toast.makeText(getApplicationContext(), "registro eliminado", Toast.LENGTH_SHORT).show();
            listaEmpleados = controlador.obtenerRegistros();
            adapter = new ListaEmpleadosAdapter(getApplicationContext(), R.layout.item_lista, listaEmpleados);
            lista.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "error al borrar", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_busqueda, menu);
        MenuItem item= menu.findItem(R.id.idbuscador);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter.setFilter(listaEmpleados);
                return true;
            }
        });
        return true;
    }


    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    public boolean onQueryTextChange(String newText) {
        try {

            ArrayList<Empleado>listafiltrada=filter(listaEmpleados,newText);
            adapter.setFilter(listafiltrada);

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private ArrayList<Empleado> filter(ArrayList<Empleado> empleados,String texto){
        ArrayList<Empleado>listaFiltrada=new ArrayList<>();
        try{
            texto=texto.toLowerCase().trim();

            for(Empleado emple : empleados){
                String cod=emple.getCedula().toString();
                if (cod.contains(texto)){
                    listaFiltrada.add(emple);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaFiltrada;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}

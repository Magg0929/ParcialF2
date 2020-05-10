package com.uac.tp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActualizarActivity extends AppCompatActivity {

    ControladorDB controlador;
    EditText nombre,salario,cedula;

    Spinner spn_estratos, spn_niveles;
    int estrato,indice;
    String nivel;
    Button actualizar,cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        cedula=findViewById(R.id.idCedula);
        nombre=findViewById(R.id.idNombre);
        salario=findViewById(R.id.idSalario);
        spn_estratos =findViewById(R.id.SPN1);
        spn_niveles =findViewById(R.id.SPN2);
        actualizar=findViewById(R.id.idActualizar);
        cancelar=findViewById(R.id.idCancelar);

        controlador= new ControladorDB(getApplicationContext());


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Estratos,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_estratos.setAdapter(adapter);



        spn_estratos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estrato= Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        ArrayAdapter<CharSequence> adaptador=ArrayAdapter.createFromResource(this,R.array.Niveles,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_niveles.setAdapter(adaptador);



        spn_niveles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivel= parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        Intent i = getIntent();
        indice = i.getIntExtra("indice", 0);
        ArrayList<Empleado> lista = controlador.obtenerRegistros();

        Empleado empleado = lista.get(indice);

        cedula.setText(empleado.getCedula());

        nombre.setText(empleado.getNombre());
        salario.setText(String.valueOf(empleado.getSalario()));


        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empleado empl = new Empleado(cedula.getText().toString().trim(), nombre.getText().toString(),estrato, Float.parseFloat(salario.getText().toString().trim()),nivel);
                int retorno = controlador.actualizarRegistro(empl);
                if (retorno == 1) {
                    Toast.makeText(getApplicationContext(), "Los datos han sido actualizados", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "fallo al actualizar", Toast.LENGTH_SHORT).show();
                }
                limpiar();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

    }


    private void limpiar() {
        nombre.setText("");
        salario.setText("");
    }

}

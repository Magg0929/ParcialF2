package com.uac.tp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nombre,cedula,salario;
    Spinner spnestratos,spnniveles;
    int estrato;
    String nivel;
    Button registrar,lista;
    ControladorDB controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cedula=findViewById(R.id.edtCedula);
        nombre=findViewById(R.id.edtNombre);
        salario=findViewById(R.id.edtSalario);
        spnestratos=findViewById(R.id.spnEstratos);
        spnniveles=findViewById(R.id.spnNiveles);
        registrar=findViewById(R.id.btnGuardar);
        lista=findViewById(R.id.btnListamiento);

        controlador= new ControladorDB(getApplicationContext());


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Estratos,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnestratos.setAdapter(adapter);


        spnestratos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estrato= Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        ArrayAdapter<CharSequence> adaptador=ArrayAdapter.createFromResource(this,R.array.Niveles,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnniveles.setAdapter(adaptador);


        spnniveles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivel= parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cedula.getText().toString().trim().length()==0){
                    Toast.makeText(getApplicationContext(), "Error en el ingreso de datos, verifique la seleccionde datos", Toast.LENGTH_SHORT).show();
                } else{
                    Empleado empleado = new Empleado(cedula.getText().toString().trim(),nombre.getText().toString(),estrato,Float.parseFloat(salario.getText().toString().trim()),nivel);
                    if (!controlador.buscarEstudiante(cedula.getText().toString().trim())){
                        Log.d("Buscar", "No encontrado");
                        long retorno = controlador.agregarRegistro(empleado);
                        if (retorno != -1) {
                            Toast.makeText(v.getContext(), "Empleado guardado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "registro fallido", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Log.d("Buscar", "encontrado");
                        Toast.makeText(getApplicationContext(),"El empleado ya se encuentra registrado",Toast.LENGTH_SHORT).show();
                    }
                    limpiar();
                }
            }


        });
        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaActivity.class);
                startActivity(i);
            }
        });

    }

    private void limpiar() {
        cedula.setText("");
        nombre.setText("");
        salario.setText("");
    }

}

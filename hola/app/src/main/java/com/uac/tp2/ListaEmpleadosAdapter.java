package com.uac.tp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaEmpleadosAdapter extends BaseAdapter {

    private ArrayList<Empleado> lista;
    private Context contexto;
    private int layoutRecurso;

    public ListaEmpleadosAdapter(Context contexto, int layoutRecurso, ArrayList<Empleado> lista) {
        this.lista = lista;
        this.contexto = contexto;
        this.layoutRecurso = layoutRecurso;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(contexto).inflate(layoutRecurso, null);
        }

        Empleado empleado=lista.get(position);


        TextView cedulalis=view.findViewById(R.id.textCedula);
        TextView nombrelis=view.findViewById(R.id.txtNombre);
        TextView estratolis=view.findViewById(R.id.textEstrato);
        TextView salariolis=view.findViewById(R.id.textSalario);
        TextView nivellis=view.findViewById(R.id.textNivel);



        cedulalis.setText(empleado.getCedula());
        nombrelis.setText(empleado.getNombre());
        estratolis.setText(String.valueOf(empleado.getEstrato()));
        salariolis.setText(String.valueOf(empleado.getSalario()));
        nivellis.setText(empleado.getNivel_edu());

        return view;
    }

    public void setFilter(ArrayList<Empleado> listaempleados){
        this.lista=new ArrayList<>();
        this.lista.addAll(listaempleados);
        notifyDataSetChanged();
    }

}

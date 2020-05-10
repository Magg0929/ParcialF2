package com.uac.tp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ControladorDB {
    private SQLiteBD bd;
    public ControladorDB(Context context) {
        this.bd = new SQLiteBD(context, ModeloDB.TABLA_EMPLEADO, null, 1);
    }
    public long agregarRegistro(Empleado empleado) {
        try {
            SQLiteDatabase database = bd.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ModeloDB.COL_CEDULA, empleado.getCedula());
            values.put(ModeloDB.COL_NOMBRE, empleado.getNombre());
            values.put(ModeloDB.COL_ESTRATO, empleado.getEstrato());
            values.put(ModeloDB.COL_SALARIO, empleado.getSalario());
            values.put(ModeloDB.COL_NIVEL_EDU, empleado.getNivel_edu());
            return database.insert(ModeloDB.TABLA_EMPLEADO, null, values);
        } catch (Exception e) {
            return -1L;
        }
    }

    public int actualizarRegistro(Empleado empleado) {
        try {
            SQLiteDatabase database = bd.getWritableDatabase();
            ContentValues valoresActualizados = new ContentValues();
            valoresActualizados.put(ModeloDB.COL_NOMBRE, empleado.getNombre());
            valoresActualizados.put(ModeloDB.COL_CEDULA, empleado.getCedula());
            valoresActualizados.put(ModeloDB.COL_ESTRATO, empleado.getEstrato());
            valoresActualizados.put(ModeloDB.COL_SALARIO, empleado.getSalario());
            valoresActualizados.put(ModeloDB.COL_NIVEL_EDU, empleado.getNivel_edu());

            String campoParaActualizar = ModeloDB.COL_CEDULA + " = ?";
            String[] argumentosParaActualizar = {String.valueOf(empleado.getCedula())};

            return database.update(ModeloDB.TABLA_EMPLEADO, valoresActualizados, campoParaActualizar, argumentosParaActualizar);
        } catch (Exception e) {
            return 0;
        }
    }

    public int borrarRegistro(Empleado persona) {
        try {
            SQLiteDatabase database = bd.getWritableDatabase();
            String[] argumentos = {String.valueOf(persona.getCedula())};
            return database.delete(ModeloDB.TABLA_EMPLEADO, ModeloDB.COL_CEDULA + " = ?", argumentos);
        } catch (Exception e) {
            return 0;
        }
    }

    public ArrayList<Empleado> obtenerRegistros() {
        ArrayList<Empleado> empleados = new ArrayList<>();

        SQLiteDatabase database = bd.getReadableDatabase();

        String[] columnasConsultar = {ModeloDB.COL_CEDULA, ModeloDB.COL_NOMBRE, ModeloDB.COL_ESTRATO
                , ModeloDB.COL_SALARIO, ModeloDB.COL_NIVEL_EDU};

        Cursor cursor = database.query(ModeloDB.TABLA_EMPLEADO, columnasConsultar
                , null, null, null, null, null);

        if (cursor == null) {
            return empleados;
        }

        if (!cursor.moveToFirst()) {
            return empleados;
        }

        do {

            Empleado empleado = new Empleado(cursor.getString(0), cursor.getString(1)
                    , cursor.getInt(2), cursor.getFloat(3), cursor.getString(4));
            empleados.add(empleado);
        } while (cursor.moveToNext());

        cursor.close();
        return empleados;
    }
    public boolean buscarEstudiante(String ced){
        String[] args = new String[] {ced};
        SQLiteDatabase database = bd.getReadableDatabase();
        Cursor c = database.query(ModeloDB.TABLA_EMPLEADO, null, "cedula=?", args, null, null, null);
        if (c.getCount()>0) {
            database.close();
            return true;
        }
        else{
            database.close();
            return false;}

    }


}

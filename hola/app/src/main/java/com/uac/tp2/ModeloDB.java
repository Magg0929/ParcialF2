package com.uac.tp2;

public class ModeloDB {
    public static final String NOMBREDB = "baseD";
    public static final String TABLA_EMPLEADO = "empleado";
    public static final String COL_CEDULA = "cedula";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_ESTRATO = "estrato";
    public static final String COL_SALARIO = "salario";
    public static final String COL_NIVEL_EDU = "nivel_educativo";

    public static final String CREATE_TABLA_EMPLEADO="CREATE TABLE empleado(" +
            "cedula TEXT primary key," +
            "nombre TEXT not null,"+
            "estrato INTEGER not null," +
            "salario REAL not null," +
            "nivel_educativo TEXT not null" +
            ");";
}

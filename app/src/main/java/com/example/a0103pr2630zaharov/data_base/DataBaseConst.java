package com.example.a0103pr2630zaharov.data_base;

public class DataBaseConst {
    public static final String DATA_BASE_NAME = "calc.db";
    public static final int DATA_BASE_VERSION = 1;
    public static final String TABLE_NAME_RESULTS = "Results";
    public static final String RESULTS_ID = "id";
    public static final String RESULTS_EXPRESSIONS = "expression";
    public static final String RESULTS_RES = "result";
    public static final String CREATE_TABLE_RESULTS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_RESULTS +
            "" + " ( " + RESULTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RESULTS_EXPRESSIONS + " TEXT, " +
            "" + RESULTS_RES + " TEXT);";
    public static final String DELETE_TABLE_RESULTS = "DROP TABLE IF EXISTS " + TABLE_NAME_RESULTS;
}

package com.example.basededatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

 class AplicacionSQLiteOpenHelper(context:Context): SQLiteOpenHelper(context, DATABASE_NAME,null,
     DATABASE_VERSION) {
     companion object {
         private const val DATABASE_NAME = "Tienda"
         private const val DATABASE_VERSION = 1

         // Crear la tabla1
         private const val CREATE_CATEGORIA:String = "CREATE TABLE CATEGORIA" +
                 " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                 "nombre TEXT," +
                 "activo INTEGER DEFAULT 1 NOT NULL," +
                 " descripcion text);"
         private const val DROP_CATEGORIA="DROP TABLE IF EXISTS CATEGORIA"
         // Crear la tabla2
         private const val CREATE_PRODUCTO = "CREATE TABLE PRODUCTO (" +
                 "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 "categoriaid INT NOT NULL,"+
                 "nombre TEXT, " +
                 "descripcion TEXT, " +
                 "activo INTEGER DEFAULT 1 NOT NULL,"+
                 "precio REAL,"+
                 "FOREIGN KEY (categoriaid) REFERENCES CATEGORIA(id));"
         private const val DROP_PRODUCTO="DROP TABLE IF EXISTS PRODUCTO"
     }
     override fun onCreate(db: SQLiteDatabase?) {
         if (db != null) {
             db.execSQL(CREATE_CATEGORIA)
             db.execSQL(CREATE_PRODUCTO)
         }

     }

     override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newversion: Int) {
        if(db!=null){
            db.execSQL(DROP_PRODUCTO)
            db.execSQL(DROP_CATEGORIA)
        }
     }
 }
package com.example.basededatos.entities

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

data class Producto(var id:Int,var nombre:String, var descripcion:String,
                    var activo:Boolean, var precio:Float, var categoria:Categoria?=null) :
    AEntity<Producto>() {
    companion object{
        private var TABLENAME:String="Producto"
        public fun loadAll(db: SQLiteDatabase):ArrayList<Producto>? {
            val projection = arrayOf("id", "nombre", "activo", "descripcion","precio","categoria")
            var elements = ArrayList<Producto>();
            val cursor = db.query(
                Producto.TABLENAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
            )
            while(cursor.moveToNext()){
                elements.add(Producto().transform(cursor))

            }

            cursor.close()
            return elements
        }
        public fun loadByCategoria(c:Categoria,db: SQLiteDatabase):ArrayList<Producto>? {
            val projection = arrayOf("id", "nombre", "activo", "descripcion","precio","categoriaid")
            var elements = ArrayList<Producto>();
            var selection="categoriaid =?"
            var selectionargs= arrayOf(c.id.toString())
            val cursor = db.query(
                Producto.TABLENAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionargs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
            )
            while(cursor.moveToNext()){
                elements.add(Producto().transform(cursor))

            }

            cursor.close()
            return elements
        }


    }
    constructor():this(-1,"","",true,0f,null)

    override fun transform(c: Cursor): Producto {

        this.id=c.getInt(c.getColumnIndexOrThrow("id"));
        this.activo=c.getInt(c.getColumnIndexOrThrow("activo"))>0;
        this.precio=c.getFloat(c.getColumnIndexOrThrow("precio"));
        this.nombre=c.getString(c.getColumnIndexOrThrow("nombre"));
        this.descripcion=c.getString(c.getColumnIndexOrThrow("descripcion"));

        return this;
    }

    override fun insert(db: SQLiteDatabase): Long {
        val values = ContentValues().apply {
            put("nombre",nombre)
            put("activo",activo)
            put("descripcion",descripcion)
            put("precio",precio)
            put("categoriaid", categoria?.id ?:-1 )
        }
        var id=db.insert(Producto.TABLENAME,null,values);
        this.id=id.toInt();
        return id;

    }

    override fun update(db: SQLiteDatabase): Int {
        val values = ContentValues().apply {
            put("nombre",nombre)
            put("activo",activo)
            put("descripcion",descripcion)
            put("precio",precio)
            put("categoria", categoria?.id ?:-1 )
        }


        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.update(
            Producto.TABLENAME,
            values,
            selection,
            selectionArgs)

    }

    override fun delete(db: SQLiteDatabase): Int {
        val selection = "id = ?"
        val selectionArgs = arrayOf(this.id.toString())
        return db.delete(Producto.TABLENAME, selection, selectionArgs);

    }

}
package com.example.basededatos.entities

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

data class Categoria(var id:Int,var nombre:String,var activo:Boolean,
                     var descripcion:String,var productos:ArrayList<Producto>?=null) :
    AEntity<Categoria>() {
   companion object{
       private var TABLENAME:String="Categoria"
       public fun loadAll(db: SQLiteDatabase):ArrayList<Categoria>? {
           val projection = arrayOf("id", "nombre", "activo", "descripcion")
           var elements = ArrayList<Categoria>();
           val cursor = db.query(
               Categoria.TABLENAME,   // The table to query
               projection,             // The array of columns to return (pass null to get all)
               null,              // The columns for the WHERE clause
               null,          // The values for the WHERE clause
               null,                   // don't group the rows
               null,                   // don't filter by row groups
               null               // The sort order
           )
            while(cursor.moveToNext()){
                elements.add(Categoria().transform(cursor))

           }

           cursor.close()
           return elements
       }

       //}
   }
    constructor():this(-1,"",true,"")

    override fun transform(c: Cursor): Categoria {

        this.id=c.getInt(c.getColumnIndexOrThrow("id"));
        this.activo=c.getInt(c.getColumnIndexOrThrow("activo"))>0;
        this.nombre=c.getString(c.getColumnIndexOrThrow("nombre"));
        this.descripcion=c.getString(c.getColumnIndexOrThrow("descripcion"));

        return this;
    }

    override fun insert(db: SQLiteDatabase): Long {
        val values = ContentValues().apply {
            put("nombre",nombre)
            put("activo",activo)
            put("descripcion",descripcion)
        }
        var id=db.insert(Categoria.TABLENAME,null,values);
        this.id=id.toInt();
        return id;

    }

    override fun update(db: SQLiteDatabase): Int {
        val values = ContentValues().apply {
            put("nombre",nombre)
            put("activo",activo)
            put("descripcion",descripcion)
        }


        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.update(
            Categoria.TABLENAME,
            values,
            selection,
            selectionArgs)

    }

    override fun delete(db: SQLiteDatabase): Int {
        val selection = "id = ?"
        val selectionArgs = arrayOf(this.id.toString())
        return db.delete(Categoria.TABLENAME, selection, selectionArgs);

    }
    override fun toString(): String {
        return this.id.toString()+":"+this.nombre
    }

}
package com.example.basededatos.entities

import android.database.sqlite.SQLiteDatabase

interface IEntity <T>{

    fun insert( db: SQLiteDatabase):Long;
    fun update( db: SQLiteDatabase):Int
    fun delete(db:SQLiteDatabase):Int
}
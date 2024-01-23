package com.example.basededatos.entities

import android.database.Cursor

abstract class AEntity<T>: IEntity<T> {

    protected abstract fun transform(c:Cursor):T
}
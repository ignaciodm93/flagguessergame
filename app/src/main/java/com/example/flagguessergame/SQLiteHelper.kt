package com.example.flagguessergame

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    //Creo qla base de datos
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table if not exists Users ( _id integer primary key autoincrement, name text, password text, score int)")
    }

    //Método usado ocasionalmente para corregir datos de la base de datos o actualizar columnas
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            val eraseForUpdate = "DROP TABLE IF EXISTS Users"
            db!!.execSQL(eraseForUpdate)
            onCreate(db)
    }
}
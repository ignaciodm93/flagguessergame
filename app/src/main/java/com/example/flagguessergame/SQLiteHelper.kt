package com.example.flagguessergame

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        //db.execSQL("create table Users(id int primary key, name text, password text, score int)")
    //chequear el autoincrement
        db.execSQL("create table if not exists Users (id int primary key autoincrement, name text, password text, score int)")
        db.execSQL("insert into Users (name, password, score) VALUES (admin, pass123, 0)");
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}
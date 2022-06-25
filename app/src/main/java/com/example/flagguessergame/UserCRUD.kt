package com.example.flagguessergame

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import java.io.IOException

class UserCRUD() {

    //Chequeo si el usuario existe, consultando por el nombre de usuario ingresado
    fun exists(context: Context, nameToCheck: String): Boolean {
        val admin = SQLiteHelper(context, "GameDB", null, 1)
        val bd = admin.writableDatabase
        var fila = bd.rawQuery("select * from Users where name = ? ", arrayOf(nameToCheck))
        var amount = fila.count
        bd.close()
        return amount > 0
    }

    //Evaluo si la contraseña coincide para el usuario ingresado
    fun userMatchesPass(context: Context, nameToCheck: String, passToCheck: String): Boolean {
        val admin = SQLiteHelper(context, "GameDB", null, 1)
        val db = admin.writableDatabase
        val query = "select * from Users where name = ? and password = ?"
        val cursor = db.rawQuery(query, arrayOf(nameToCheck, passToCheck))
        val result = cursor.count > 0
        cursor.close()
        db.close()
        return result
    }

    //Registro un nuevo usuario en la bd
    fun registerNewUser(context: Context, nameToSave: String, passToSave: String) {
        try {
            val admin = SQLiteHelper(context,"GameDB", null, 1)
            val bd = admin.writableDatabase
            val newUser = ContentValues()
            newUser.put("name", nameToSave)
            newUser.put("password", passToSave)
            newUser.put("score", 0)
            bd.insert("Users", null, newUser)
            bd.close()
        } catch (ex: IOException) {
            Toast.makeText(context, "Ocurrió un error al intentar guardar el nuevo usuario", Toast.LENGTH_SHORT)
        }
    }

    //Obtengo los 10 puntajes mas altos para llenar la activity de Ranking
    @SuppressLint("Range")
    fun getTopTenCursor(context: Context, db: SQLiteDatabase): Cursor {
        val query = "SELECT * FROM Users ORDER BY score DESC LIMIT 10"
        val cursor = db.rawQuery(query, null)
        return cursor
    }

    //Metodo para borrar por nombre (chequea que la contraseña sea la adecuada)
    fun eraseByName(context: Context, nameToErase: String, pass: String){
        val admin = SQLiteHelper(context,"GameDB", null, 1)
        val db = admin.writableDatabase
        val findQuery = "select * from Users where name = ?"
        if(this.userMatchesPass(context, nameToErase, pass)) {
            val query = "delete from Users where name = ?"
            val cursor = db.rawQuery(query, arrayOf(nameToErase))
        } else {
            Toast.makeText(context, "La contraseña no es la adecuada o el usuario no existe", Toast.LENGTH_LONG).show()
        }
        db.close()
    }

    //Metodo para borrar todos
    fun eraseAll(context: Context){
        val admin = SQLiteHelper(context,"GameDB", null, 1)
        val db = admin.writableDatabase
        val query = "delete from Users"
        val cursor = db.rawQuery(query, null)
        db.close()
    }

    //Actualizo o guardo el puntaje de un jugador, tomando el puntaje previo si ya habia jugado anteriormente
    fun updateScore(context: Context, name: String, newScore: Int) {
        try{
            val admin = SQLiteHelper(context,"GameDB", null, 1)
            val db = admin.writableDatabase
            val oldScore = getCurrentPlayersScore(context, name)
            val updatedScore = newScore + oldScore

            val updateQuery = "update Users set score = $updatedScore where name = '$name' "
            db.execSQL(updateQuery)
            db.close()
            Toast.makeText(context, "Puntaje guardado exitosamente!", Toast.LENGTH_LONG).show()
        } catch(ex: IOException) {
            Toast.makeText(context, "Ocurrió un error al guardar el puntaje", Toast.LENGTH_SHORT).show()
        }
    }

    //Consulto y obtengo el puntaje de un jugador especifico
    @SuppressLint("Range")
    private fun getCurrentPlayersScore(context: Context, name: String): Int {
        val admin = SQLiteHelper(context, "GameDB", null, 1)
        val db = admin.writableDatabase
        val query = "select score from Users where name = ?"
        val cursor = db.rawQuery(query, arrayOf(name))
        var score = ""

            try {
                if(cursor != null && cursor.moveToFirst()) {
                    score = cursor.getString(cursor.getColumnIndex("score"))
                }
            } catch(ex: IOException){
                Toast.makeText(context, "Ocurrió un error al intentar obtener el puntaje", Toast.LENGTH_SHORT).show()
            } finally {
                cursor?.close()
            }

        cursor.close()
        db.close()
        return score.toInt()
    }
}
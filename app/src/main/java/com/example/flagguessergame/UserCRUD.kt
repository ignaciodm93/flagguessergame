package com.example.flagguessergame

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.widget.Toast
import java.io.IOException

class UserCRUD() {

    class User(name: String, score: Int)

    fun exists(context: Context, nameToCheck: String): Boolean {
        val admin = SQLiteHelper(context, "GameDB", null, 1)
        val bd = admin.writableDatabase
        var fila = bd.rawQuery("select * from Users where name = ? ", arrayOf(nameToCheck))
        var amount = fila.count
        bd.close()
        return amount > 0
    }

    //Chequea si un usuario con los datos ingresados ya está registrado.
    fun exists1(context: Context, nameToCheck: String): Boolean {
        val admin = SQLiteHelper(context, "users", null, 1)
        val db = admin.writableDatabase
        //val query = "select * from Users where name = ?"
        val query = "SELECT * FROM Users WHERE name = ?"
        val cursor:Cursor = db.rawQuery(query, arrayOf(nameToCheck))

        val result = cursor.count > 0
        if( cursor.count > 0) {
            print("hay reg")
        }
        //db.close()
        //return result

        /*val admin = SQLiteHelper(context, "users", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select name from users where name=${nameToCheck}", null)
            bd.close()

            if(fila.moveToFirst()) {
                print("si tiene")
            } else {
                print("no tiene")
            }

            return fila.moveToFirst()*/
        return false
    }
    /*
     val admin = SQLiteHelper(this, "Users", null, 1)
     val bd = admin.writableDatabase
     var filas = bd.rawQuery("select name, score from Users", null)
     if (!filas.equals(null)) {
         var r = filas.moveToNext()
         Toast.makeText(this, "Hay 1 registro en la tabla al menos", Toast.LENGTH_LONG).show()
     } else
         Toast.makeText(this, "No existe un artículo con dicha descripción", Toast.LENGTH_SHORT).show()
     bd.close()
*/
//no funciona
    @SuppressLint("Range")
    fun selectAllRegisters(context: Context): List<User> {
        val admin = SQLiteHelper(context,"GameDB", null, 1)
        val db = admin.writableDatabase
        val query = "SELECT * FROM Users"
        val cursor = db.rawQuery(query, null)
        val usersFound = mutableListOf<User>()

        var list = mutableListOf<String>()

        if  (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val score = cursor.getString(cursor.getColumnIndex("score")).toInt()
                var newUser: User = User(name, score)
                usersFound.add(newUser);
                list.add(name)
            }while (cursor.moveToNext());
        }
        db.close()
        return usersFound
    }

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

    @SuppressLint("Range")
    fun getTopTen(context: Context): List<User> {
        val admin = SQLiteHelper(context,"GameDB", null, 1)
        val db = admin.writableDatabase
        val query = "SELECT name, score FROM Users ORDER BY score DESC LIMIT 10"
        val cursor = db.rawQuery(query, null)
        val usersFound = mutableListOf<User>()

        var list = mutableListOf<String>()

        if  (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val score = cursor.getString(cursor.getColumnIndex("score")).toInt()
                var newUser: User = User(name, score)
                usersFound.add(newUser);
                list.add(name)
            }while (cursor.moveToNext());
        }
        db.close()
        return usersFound
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
            Toast.makeText(context, "La contraseña no es la adecuada o el usuario no existe", Toast.LENGTH_LONG)
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

    fun updateScore(context: Context, name: String, score: Int) {
        val admin = SQLiteHelper(context,"GameDB", null, 1)
        val bd = admin.writableDatabase
        val newUpdate = ContentValues()
        val oldScore = getCurrentPlayersScore(context, name)
        val newScore = score + oldScore
        newUpdate.put("score", newScore)
        bd.update("Users", newUpdate, "name=${name}", null)
        bd.close()
    }

    @SuppressLint("Range")
    private fun getCurrentPlayersScore(context: Context, name: String): Int {
        val admin = SQLiteHelper(context, "GameDB", null, 1)
        val db = admin.writableDatabase
        val query = "select score from Users where name = ?"
        val cursor = db.rawQuery(query, arrayOf(name))
        var score = cursor.getString(cursor.getColumnIndex("score")).toInt()
        cursor.close()
        db.close()
        return score
    }

}
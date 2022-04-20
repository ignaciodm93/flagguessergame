package com.example.flagguessergame

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        insertTestUser()
        getRanking()
    }

    fun goToMainActivity(view: View) {
        var intentMain: Intent = Intent(this, MainActivity::class.java)
        intentMain.putExtra("isLogged","true")
        intentMain.putExtra("username","Nakio")
        startActivity(intentMain)
    }

    fun insertNewUser() {
        val admin = SQLiteHelper(this,"GameDB", null, 1)
        val bd = admin.writableDatabase
        val newUser = ContentValues()
        newUser.put("name", "")
        newUser.put("password", "")
        newUser.put("score", 0) //cambiar por el valor tomado de la pantalla
        bd.insert("Users", null, newUser)
        bd.close()
    }

    fun insertTestUser() {
        val admin = SQLiteHelper(this,"GameDB", null, 1)
        val bd = admin.writableDatabase
        val newUser = ContentValues()
        newUser.put("name", "Ditto")
        newUser.put("password", "ditto123")
        newUser.put("score", 100) //cambiar por el valor tomado de la pantalla
        try{
            bd.insert("Users", null, newUser)
        } catch(ex: Exception) {
            print(ex.message)
        }

        bd.close()
    }

    fun getRanking() {
        val query: String = "SELECT name, score FROM Users";

        //https://developer.android.com/training/data-storage/sqlite#kotlin


        val admin = SQLiteHelper(this, "Users", null, 1)
        val bd = admin.writableDatabase

        //var q: String = "SELECT name FROM sqlite_master WHERE type='table' AND name='Users'"
        //var checktable = bd.rawQuery(q, null)

        //if(checktable == null) {
        //    print("no existe")
        //}

        var filas = bd.rawQuery("select name, score from Users", null)
        if (!filas.equals(null)) {
            var r = filas.moveToNext()
            Toast.makeText(this, "Hay 1 registro en la tabla al menos", Toast.LENGTH_LONG).show()
        } else
            Toast.makeText(this, "No existe un artículo con dicha descripción", Toast.LENGTH_SHORT).show()
        bd.close()


    }

}
package com.example.flagguessergame

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun goToMainActivity(view: View) {
        var intentMain: Intent = Intent(this, MainActivity::class.java)
        startActivity(intentMain)
    }

    fun insertNewUser() {
        val admin = SQLiteHelper(this,"GameDB", null, 1)
        val bd = admin.writableDatabase
        val newUser = ContentValues()
        newUser.put("name", "")
        newUser.put("password", "")
        newUser.put("score", 0)
        bd.insert("Users", null, newUser)
        bd.close()
    }

}
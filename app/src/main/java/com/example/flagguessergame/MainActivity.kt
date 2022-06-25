package com.example.flagguessergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    var isLogged: String = "false"
    var currentUser: String = ""
    var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isLogged= intent.getStringExtra("isLogged").toString()
        currentUser= intent.getStringExtra("username").toString()
        score = intent.getIntExtra("score", 0)

        checkIfLogged()
    }

    //Voy al Play Activity evaluando si estoy loggeado o no y pasando el valor del nombre
    fun goToPlayActivity(view: View) {
        if(this.isLogged == "true") {
            var intentPlay: Intent = Intent(this, PlayActivity::class.java)
            intentPlay.putExtra("username", currentUser)
            if (this.score != 0) {
                intentPlay.putExtra("score", score)
            }
            startActivity(intentPlay)
        }
        else {
            Toast.makeText(this, "Debe loggearse para poder jugar", Toast.LENGTH_LONG).show()
        }
    }

    //Voy al Login Activity para loggearme
    fun goToLoginActivity(view: View) {
        var intentLogin: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }

    //Voy al ranking Activity
    fun goToRankingActivity(view: View) {
        var intentRanking: Intent = Intent(this, RankingActivity::class.java)
        startActivity(intentRanking)
    }

    //Voy al Info Activity
    fun goToInfoActivity(view: View) {
        var infoRanking: Intent = Intent(this, InfoActivity::class.java)
        startActivity(infoRanking)
    }

    //Devuelvo si estoy loggueado y modifico las variables de animaciones
    fun checkIfLogged(): Boolean {
        return if(isLogged.toString() == "true") {
            findViewById<ImageView>(R.id.dooricon).isVisible = true
            findViewById<ImageView>(R.id.onicon).isVisible = true
            findViewById<ImageView>(R.id.officon).isVisible = false
            findViewById<TextView>(R.id.username).text = currentUser

            true
        } else {
            findViewById<ImageView>(R.id.dooricon).isVisible = false
            findViewById<ImageView>(R.id.onicon).isVisible = false

            false
        }
    }

    //Me desloggueo
    fun logout(view: View) {
        this.currentUser = ""
        findViewById<TextView>(R.id.username).text = "No loggeado"
        this.score = 0
        this.isLogged = false.toString()
        findViewById<ImageView>(R.id.onicon).isVisible = false
        findViewById<ImageView>(R.id.officon).isVisible = true
        findViewById<ImageView>(R.id.dooricon).isVisible = false
    }
}
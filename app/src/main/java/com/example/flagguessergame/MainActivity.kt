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

        //var helper: SQLiteHelper = SQLiteHelper(applicationContext, "GameDB", null, 1)

        //helper.onCreate(helper.readableDatabase)

    }

    var buttonPlayActivated: Boolean = false

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

    fun goToLoginActivity(view: View) {
        var intentLogin: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }

    fun goToRankingActivity(view: View) {
        var intentRanking: Intent = Intent(this, RankingActivity::class.java)
        startActivity(intentRanking)
    }

    fun goToInfoActivity(view: View) {
        var infoRanking: Intent = Intent(this, InfoActivity::class.java)
        startActivity(infoRanking)
    }

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

    fun logout(view: View) {
        this.currentUser = ""
        findViewById<TextView>(R.id.username).text = "notLogged"
        this.score = 0
        this.isLogged = false.toString()
        findViewById<ImageView>(R.id.onicon).isVisible = false
        findViewById<ImageView>(R.id.officon).isVisible = true
        findViewById<ImageView>(R.id.dooricon).isVisible = false
    }
}
package com.example.flagguessergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var buttonPlayActivated: Boolean = false

    fun goToPlayActivity(view: View) {
        var intentPlay: Intent = Intent(this, PlayActivity::class.java)
        startActivity(intentPlay)
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
/*
    fun redirectTo(intent: Intent, activity: AppCompatActivity) {
        var intent: Intent = Intent(this, activity::class.java)
        startActivity(intent)
    }*/
}
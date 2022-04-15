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
        var intent_play: Intent = Intent(this, PlayActivity::class.java)
        startActivity(intent_play)
        //setContentView(R.layout.activity_play)
    }

    fun goToLoginActivity(view: View) {
        setContentView(R.layout.activity_login)
    }

}
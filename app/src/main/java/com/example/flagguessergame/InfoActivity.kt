package com.example.flagguessergame

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
    }

    //Vuelvo al main
    fun goToMainActivity(view: View) {
        var intentMain: Intent = Intent(this, MainActivity::class.java)
        startActivity(intentMain)
    }
}
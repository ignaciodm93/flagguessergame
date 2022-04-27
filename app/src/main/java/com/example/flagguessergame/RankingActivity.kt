package com.example.flagguessergame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RankingActivity: AppCompatActivity() {

    val userCRUD: UserCRUD = UserCRUD()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        this.userCRUD.selectAllRegisters(this)
    }

}
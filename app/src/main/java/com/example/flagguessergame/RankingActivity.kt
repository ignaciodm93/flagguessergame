package com.example.flagguessergame

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flagguessergame.databinding.ActivityInfoBinding.inflate
import com.example.flagguessergame.databinding.ActivityLoginBinding.inflate
import com.example.flagguessergame.databinding.ActivityRankingBinding
import com.example.flagguessergame.databinding.RankingListBinding
import java.io.IOException

class RankingActivity: AppCompatActivity() {

    //class User(val name: String, val score: Int)


    /*val userCRUD: UserCRUD = UserCRUD()
    //rankingList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)


        var con: SQLiteHelper = SQLiteHelper(applicationContext, "GameDB", null, 1)

        //Lleno la tabla del raning
        this.fillHallOfFame()
    }*/

    /*fun fillHallOfFame() {

        val rankingList = findViewById<ListView>(R.id.ranking)
        val usersList: ArrayList<UserCRUD.User> = arrayListOf()

        try {
            var list = this.userCRUD.getTopTen(this)

            for (n in list) {
                usersList.add(n)
            }



        } catch (ex: IOException) {

        }
    }*/

    private lateinit var binding: ActivityRankingBinding
    lateinit var helper: SQLiteHelper
    val userCRUD: UserCRUD = UserCRUD()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*helper = SQLiteHelper(this, "GameDB", null, 1)

        val db : SQLiteDatabase = helper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM amigos",
            null)
*/
        val admin = SQLiteHelper(this,"GameDB", null, 1)
        val adaptador = CursorAdapterListView(this, this.userCRUD.getTopTenCursor(this, admin.writableDatabase))
        binding.ranking.adapter = adaptador
        admin.close()
    }

    inner class CursorAdapterListView(context: Context, cursor: Cursor) :
        CursorAdapter(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER) {

        override fun newView(context: Context?,
                             cursor: Cursor?, parent: ViewGroup?): View {
            val inflater = LayoutInflater.from(context)
            return inflater.inflate(R.layout.ranking_list,
                parent, false )
        }

        override fun bindView(view: View?,
                              context: Context?, cursor: Cursor?) {
            val bindingItems = RankingListBinding.bind(view!!)
            bindingItems.tvItemNombre.text = cursor!!.getString(1)
            bindingItems.tvItemEmail.text = cursor!!.getString(3)

            /*view.setOnClickListener {
                Toast.makeText(this@ActivityLista,
                    "${bindingItems.tvItemNombre.text}, " +
                            "${bindingItems.tvItemEmail.text}",
                    Toast.LENGTH_SHORT).show()
            }*/

        }

    }


}

package com.example.flagguessergame

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.flagguessergame.databinding.ActivityRankingBinding
import com.example.flagguessergame.databinding.RankingListBinding

class RankingActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRankingBinding
    private val userCRUD: UserCRUD = UserCRUD()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val admin = SQLiteHelper(this,"GameDB", null, 1)
        binding.ranking.adapter = CursorAdapterListView(this, this.userCRUD.getTopTenCursor(this, admin.writableDatabase))
        admin.close()
    }

    inner class CursorAdapterListView(context: Context, cursor: Cursor) :
        CursorAdapter(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER) {

        override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
            val inflater = LayoutInflater.from(context)
            return inflater.inflate(R.layout.ranking_list, parent, false )
        }

        override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
            val bindingItems = RankingListBinding.bind(view!!)
            bindingItems.cname.text = cursor!!.getString(1)
            bindingItems.cscore.text = cursor.getString(3)
        }
    }
}

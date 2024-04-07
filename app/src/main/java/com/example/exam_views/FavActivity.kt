package com.example.exam_views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.exam_views.databinding.ActivityFavBinding
import com.example.exam_views.room.DataBase
import com.example.exam_views.room.Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavBinding
    private lateinit var userDatabase: DataBase.AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDatabase = Room.databaseBuilder(
            applicationContext,
            DataBase.AppDatabase::class.java,
            "user_database"
        ).build()

        val id = intent.getIntExtra("id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO){
                val recyclerView:RecyclerView = findViewById(R.id.recyclerViewFav)
                recyclerView.layoutManager = LinearLayoutManager(this@FavActivity)

                val myFavList:List<Entity.Record> = userDatabase.favDao().getFavRecords(id)!!
                val adapter = AdapterFav(myFavList, id, userDatabase)
                recyclerView.adapter = adapter
            }
            binding.btBackMain.setOnClickListener{
                val intent = Intent(this@FavActivity, MainActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
                finish()
            }
        }
    }
}
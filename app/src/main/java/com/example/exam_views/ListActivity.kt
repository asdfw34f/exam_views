package com.example.exam_views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.exam_views.databinding.ActivityListBinding
import com.example.exam_views.room.DataBase
import com.example.exam_views.room.Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var userDatabase: DataBase.AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDatabase = Room.databaseBuilder(
            applicationContext,
            DataBase.AppDatabase::class.java,
            "user_database"
        ).build()

        val id = intent.getIntExtra("id", 0)

        CoroutineScope(Dispatchers.IO).launch {
            val recyclerView:RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this@ListActivity)

            val myDataList:List<Entity.Record> = userDatabase.recordDao().getAllRecords()
            val adapter = Adapter(myDataList, id, userDatabase)
            recyclerView.adapter = adapter
        }
        binding.btBackMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
            finish()
        }
    }
}
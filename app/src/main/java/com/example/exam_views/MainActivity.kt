package com.example.exam_views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.exam_views.databinding.ActivityMainBinding
import com.example.exam_views.room.DataBase
import com.example.exam_views.room.DataBase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var userDatabase: AppDatabase
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // idUser
        val id = intent.getIntExtra("id", 0)

        // init DB
        userDatabase = Room.databaseBuilder(
            applicationContext,
            DataBase.AppDatabase::class.java,
            "user_database"
        ).build()

        CoroutineScope(Dispatchers.IO).launch {
            val user= userDatabase.userDao().getUser(id)
            binding.etUserName.text = "User name: ${user.login}"
        }

        binding.btExit.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
            finish()
        }

        binding.btLenta.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
            finish()
        }

        binding.btFav.setOnClickListener {
            val intent = Intent(this, FavActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
            finish()
        }
    }
}
/*
INSERT INTO Record VALUES(1,'Москва','mow','Санкт-Петербург','led','2023-07-20T00:00:00Z','2023-07-25T00:00:00Z',2690,'MOW2007LED2507Y100');
INSERT INTO Record VALUES(2,'Москва','mow','Нижний Новгород','goj','2023-08-07T08:15:00Z','2023-08-13T09:10:00Z',3140,'MOW0708GOJ1308Y100');
INSERT INTO Record VALUES(3,'Москва','mow','Махачкала','mcx','2023-10-16T10:00:00Z','2023-10-20T12:00:00Z',4570,'MOW1610MCX2010Y100');
INSERT INTO Record VALUES(4,'Москва','mow','Калининград','kgd','2023-10-10T21:00:00Z','2023-10-15T13:00:00Z',4570,'MOW1010KGD1310Y100');
INSERT INTO Record VALUES(5,'Москва','mow','Казань','kzn','2023-07-21T15:00:00Z','2023-07-30T15:00:00Z',4760,'MOW2106KZN3006Y100');
INSERT INTO Record VALUES(6,'Москва','mow','Самара','kuf','2023-09-06T11:00:00Z','2023-09-11T11:00:00Z',4902,'MOW0609KUF1109Y100');
INSERT INTO Record VALUES(7,'Москва','mow','Краснодар','krr','2023-08-15T09:30:00Z','2023-08-23T10:30:00Z',4914,'MOW1504KRR2304Y100');
INSERT INTO Record VALUES(8,'Москва','mow','Екатеринбург','svx','2023-07-20T12:00:00Z','2023-07-26T12:00:00Z',5096,'MOW2006SVX2606Y100');
INSERT INTO Record VALUES(9,'Москва','mov','Волгоград','vog','2023-07-27T11:10:00Z','2023-08-10T10:20:00Z',5140,'MOW2706VOG1007Y100');
INSERT INTO Record VALUES(10,'Москва','mov','Пермь','pee','2023-07-09T21:00:00Z','2023-07-16T00:00:00Z',5140,'MOW0906PEE1606Y100');*/
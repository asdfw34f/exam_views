package com.example.exam_views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Query
import androidx.room.Room
import com.example.exam_views.databinding.ActivityLoginBinding
import com.example.exam_views.room.DataBase
import com.example.exam_views.room.Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDatabase: DataBase.AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
//        setContentView(R.layout.activity_login)

        userDatabase = Room.databaseBuilder(
            applicationContext,
            DataBase.AppDatabase::class.java,
            "user_database"
        ).build()

        binding.btBack.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.btAction.setOnClickListener {
            if(binding.etLogin.text.isEmpty() || binding.etPassword.text.isEmpty()){
                Toast.makeText(this, "Error empty string", Toast.LENGTH_SHORT).show()
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    val result = userDatabase.userDao().checkUser(binding.etLogin.text.toString(), binding.etPassword.text.toString())

                    withContext(Dispatchers.Main){
                        if (result != null){
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("id", result.idUser)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }
}
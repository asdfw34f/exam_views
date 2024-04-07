package com.example.exam_views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.example.exam_views.databinding.ActivityRegisterBinding
import com.example.exam_views.room.DataBase
import com.example.exam_views.room.Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userDatabase:DataBase.AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDatabase = Room.databaseBuilder(
            applicationContext,
            DataBase.AppDatabase::class.java,
            "user_database"
        ).build()

        binding.btAction.setOnClickListener {
            if(binding.etLogin.text.isEmpty() || binding.etPassword.text.isEmpty()){
                Toast.makeText(this, "Error empty string", Toast.LENGTH_SHORT).show()
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    val userExist = userDatabase.userDao().checkUser(binding.etLogin.text.toString(), binding.etPassword.text.toString())
                    if (userExist != null){
                        Toast.makeText(this@RegisterActivity, "ERROR: Login already exist", Toast.LENGTH_SHORT).show()
                    }else{
                        val user = Entity.User(
                            login =  binding.etLogin.text.toString(),
                            password = binding.etPassword.text.toString()
                        )
                        userDatabase.userDao().insertUser(user)

                        withContext(Dispatchers.Main){
                            Toast.makeText(this@RegisterActivity, "User saved", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }

        binding.btBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
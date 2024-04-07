package com.example.exam_views.room

import androidx.room.Entity
import androidx.room.PrimaryKey

class Entity {
    @Entity
    data class User(
        @PrimaryKey(autoGenerate = true)
        val idUser: Int = 0,
        val login: String,
        val password: String,
    )
    @Entity
    data class Fav(
        @PrimaryKey(autoGenerate = true)
        val idFav: Int = 0,
        val idUser: Int,
        val idRecord: Int,
    )
    @Entity
    data class Record(
        @PrimaryKey(autoGenerate = true)
        val idRecord: Int = 0,
        val startCity: String,
        val startCityCode: String,
        val endCity : String,
        val endCityCode: String,
        val startDate: String,
        val endDate: String,
        val price: Int,
        val searchToken: String
    )
}
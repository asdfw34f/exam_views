package com.example.exam_views.room

import androidx.room.Database
import androidx.room.RoomDatabase

class DataBase {
    @Database(entities = [Entity.User::class,Entity.Fav::class, Entity.Record::class], version = 1)
    abstract class AppDatabase: RoomDatabase() {
        abstract fun userDao(): DAO.UserDao
        abstract fun favDao(): DAO.FavDao
        abstract fun recordDao(): DAO.RecordDao
    }
}
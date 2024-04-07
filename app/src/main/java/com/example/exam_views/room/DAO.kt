package com.example.exam_views.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface DAO {
    @Dao
    interface UserDao{
        @Insert
        fun insertUser(user: Entity.User)
        @Query("SELECT * from User where login = :login and password =:password")
        fun checkUser(login: String, password: String): Entity.User?
        @Query("SELECT * from User where login = :login")
        fun checkLogin(login: String): Entity.User?
        @Query("SELECT * from User where idUser = :id")
        fun getUser(id: Int): Entity.User
    }
    @Dao
    interface FavDao{
        @Insert
        fun insertFav(fav: Entity.Fav)
        @Delete
        fun deleteFav(fav: Entity.Fav)
        @Query("SELECT * FROM Record WHERE idRecord IN (SELECT idRecord FROM Fav WHERE idUser = :idUser)")
        fun getFavRecords(idUser: Int): List<Entity.Record>?
        @Query("SELECT * from Fav where idRecord = :idRecord and idUser =:idUser")
        fun checkFav(idRecord: Int, idUser: Int): Entity.Fav?
    }
    @Dao
    interface RecordDao{
        @Query("SELECT * FROM Record")
        fun getAllRecords(): List<Entity.Record>
    }
}
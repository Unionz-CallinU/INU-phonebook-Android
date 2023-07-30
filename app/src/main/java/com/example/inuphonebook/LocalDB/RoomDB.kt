package com.example.inuphonebook.LocalDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Employee::class], version = 2)
abstract class RoomDB : RoomDatabase(){

    abstract fun RoomDao() : RoomDao

    companion object {
        @Volatile
        private var instance : RoomDB? = null

        fun getInstance(context : Context) : RoomDB = instance ?: synchronized(this){
            instance ?: buildDatabase(context).also {instance = it}
        }

        private fun buildDatabase(context : Context) : RoomDB {
            return Room.databaseBuilder(
                context,
                RoomDB::class.java,
                "Room.db").build()
        }
    }
}
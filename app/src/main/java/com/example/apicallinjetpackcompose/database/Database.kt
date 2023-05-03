package com.example.apicallinjetpackcompose.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.apicallinjetpackcompose.api.GetRequestApiResponseItem
import com.example.apicallinjetpackcompose.api.PostApiCallResponse


@androidx.room.Database(entities = [GetRequestApiResponseItem::class, PostApiCallResponse::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract fun dao() : MyDao

    companion object{
        @Volatile
        private var INSTANCE: Database? = null

        fun getDatabase(context: Context) : Database{
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "my_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
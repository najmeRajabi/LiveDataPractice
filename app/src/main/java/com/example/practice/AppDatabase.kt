package com.example.practice

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Question::class] , version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "myDB")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}
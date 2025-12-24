package com.example.doanltdd.Data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.doanltdd.Data.Entity.Monan
import com.example.doanltdd.Data.Dao.MonAnDao

@Database(entities = [Monan::class], version = 1)
abstract class db_MonAn : RoomDatabase() {

    abstract fun monAnDao(): MonAnDao

    companion object {
        @Volatile
        private var INSTANCE: db_MonAn? = null

        fun getDatabase(context: Context): db_MonAn {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    db_MonAn::class.java,
                    "monan_database" // Tên file vật lý lưu trên điện thoại
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
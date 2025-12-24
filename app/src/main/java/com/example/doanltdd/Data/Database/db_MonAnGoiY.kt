package com.example.doanltdd.Data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.doanltdd.Data.Dao.dao_MonAnGoiY
import com.example.doanltdd.Data.Entity.entity_MonAnGoiY

@Database(entities = [entity_MonAnGoiY::class], version = 1)
abstract class db_MonAnGoiY: RoomDatabase() {
    abstract fun daoMonAnGoiY(): dao_MonAnGoiY

    companion object {
        @Volatile
        private var INSTANCE:db_MonAnGoiY?=null
        fun getInstance(context: Context): db_MonAnGoiY{
            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    db_MonAnGoiY::class.java,
                    "tb_MonAnGoiY"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}
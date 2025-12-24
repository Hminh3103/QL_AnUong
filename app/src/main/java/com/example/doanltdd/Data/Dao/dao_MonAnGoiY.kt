package com.example.doanltdd.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.doanltdd.Data.Entity.entity_MonAnGoiY

@Dao
interface dao_MonAnGoiY {
    @Insert
    suspend fun insert(monAnGoiY: entity_MonAnGoiY)
    @Update
    suspend fun update(monAnGoiY: entity_MonAnGoiY)
    @Delete
    suspend fun delete(monAnGoiY: entity_MonAnGoiY)
    @Query("select * from tb_MonAnGoiY")
    suspend fun dsMonAnGoiY(): List<entity_MonAnGoiY>
}
package com.example.doanltdd.Data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_MonAnGoiY")
data class entity_MonAnGoiY (
    @PrimaryKey(autoGenerate = true)
    val id_MonAnGoiY: Int=0,
    val tenMonAnGoiY: String,
    val buaAn: String,
    val loaiMonAn: String,
    val calories: Int
)
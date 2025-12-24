package com.example.doanltdd.Data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tb_monan")
data class Monan (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val tenmonan: String,
    val buaan: String,
    val socalo: Int
)
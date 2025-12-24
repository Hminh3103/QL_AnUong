package com.example.doanltdd.Data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
    val email: String,
    val role: String // ví dụ: "admin", "user"
)
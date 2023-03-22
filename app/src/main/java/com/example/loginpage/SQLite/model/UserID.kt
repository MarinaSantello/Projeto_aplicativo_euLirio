package com.example.loginpage.SQLite.model

import androidx.room.Entity

@Entity(tableName = "tbl_user")
data class UserID(
    val id: Int = 0
)

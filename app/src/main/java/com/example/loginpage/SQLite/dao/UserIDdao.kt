package com.example.loginpage.SQLite.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.loginpage.SQLite.model.UserID

@Dao
interface UserIDdao {

    // retorna o ID do registro criado
    @Insert
    fun save(userID: UserID): Long

    // retorna a quantidade de registros que foram deletados (opcional)
    @Delete
    fun delete(userID: UserID): Int

    // retorna uma lista de registros
    @Query("SELECT * FROM tbl_user")
    fun getAll(): List<UserID>

}
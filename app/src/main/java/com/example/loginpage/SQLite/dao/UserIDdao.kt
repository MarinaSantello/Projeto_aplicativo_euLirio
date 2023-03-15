package com.example.loginpage.SQLite.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.loginpage.SQLite.model.UserID

@Dao
interface UserIDdao {

    // retorna o ID do registro criado
    @Insert
    fun save(userIDdao: UserIDdao): Int

    // retorna a quantidade de registros que foram deletados (opcional)
    @Delete
    fun delete(userID: UserID): Int

    // retorna uma lista de registros
//    @Query("select * from tbl_product order by name asc")
//    fun getAll(): List<UserIDdao>

}
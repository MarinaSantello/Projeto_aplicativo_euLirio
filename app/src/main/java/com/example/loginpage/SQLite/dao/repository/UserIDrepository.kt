package com.example.loginpage.SQLite.dao.repository

import android.content.Context
import com.example.loginpage.SQLite.dao.UserDB
import com.example.loginpage.SQLite.model.UserID

class UserIDrepository(context: Context) {

    // para saber qual é o banco de dados, fazer a conexão e usar os métodos da interface através de uma instância
    private val db = UserDB.getDatabase(context).productDao()

    // a função não pode estar dentro de um método estático
//    fun getProductsList(): List<UserID> {
//        return db.getAll()
//    }

    fun save(userID: UserID): Long {
        return db.save(userID)
    }

    fun delete(userID: UserID): Int {
        return db.delete(userID)
    }

    fun getAll(): List<UserID> {
        return db.getAll()
    }
}
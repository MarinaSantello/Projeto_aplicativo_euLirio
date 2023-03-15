package com.example.loginpage.SQLite.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.loginpage.SQLite.model.UserID

// classe que herda outra classe
@Database(entities = [UserID::class], version = 1)
abstract class UserDB: RoomDatabase() {

    // 3
    // função que retorna a interface do banco de dados
    abstract fun productDao(): UserIDdao

    companion object {
        // variável que guarda a instância do banco de dados
        private lateinit var instance: UserDB

        fun getDatabase(context: Context): UserDB {
            // verifica se a instância já foi criada
            if(!::instance.isInitialized) {
                // a instância precisa do contexto, classe e nome do banco de dados
                instance = Room
                    .databaseBuilder(
                        context,
                        UserDB::class.java,
                        "db_product"
                    )
                    .allowMainThreadQueries()
                    // se algo do banco de dados mudar, os dados atuais serão destruídos (NÃO USAR EM UMA APLICAÇÃO REAL)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}
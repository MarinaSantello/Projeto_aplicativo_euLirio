package com.example.loginpage.models

data class UserLogin(
    val login: String,
    val senha: String
)

data class RetornoApi (
    val message: String,
    val token: String,
    val id: Int
)
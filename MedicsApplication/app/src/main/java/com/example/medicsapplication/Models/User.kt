package com.example.medicsapplication.Models

data class User(
    var id: String,
    var name: String,
    var email: String,
    var phone: String,
    var birthdate: String,
//    var password:String,
//    var conPassword:String,
    var createdAt: Long = System.currentTimeMillis(),
    var profileImg: String? = null
)

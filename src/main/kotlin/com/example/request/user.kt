package com.example.request

import com.example.entity.User
import org.bson.types.ObjectId

data class UserRequest(
    val name: String,
    val age: Int
)
fun UserRequest.toDomain(): User {
    return User(
        id = ObjectId(),
        name = name,
        age = age
    )
}
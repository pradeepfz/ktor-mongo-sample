package com.example.repository

import com.example.entity.User
import org.bson.BsonValue

interface UserRepository {
    suspend fun insertOne(user: User): BsonValue?
}
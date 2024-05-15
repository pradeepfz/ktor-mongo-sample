package com.example.repository

import com.example.entity.User
import org.bson.BsonValue
import org.bson.types.ObjectId

interface UserRepository {
    suspend fun insertOne(user: User): BsonValue?
    suspend fun deleteById(objectId: ObjectId): Long
    suspend fun findById(objectId: ObjectId): User?
    suspend fun updateOne(objectId: ObjectId, user: User):Long
}
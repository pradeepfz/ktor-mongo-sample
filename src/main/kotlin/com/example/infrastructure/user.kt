package com.example.infrastructure


import com.example.entity.User
import com.example.repository.UserRepository
import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.bson.BsonValue
import org.bson.types.ObjectId

class UserRepositoryImpl(
    private val mongoDatabase: MongoDatabase
) : UserRepository {

    companion object {
        const val USER = "user"
    }

    override suspend fun insertOne(user: User): BsonValue? {
        try {
            val result = mongoDatabase.getCollection<User>(USER).insertOne(
                user
            )
            return result.insertedId
        } catch (e: MongoException) {
            System.err.println("Unable to insert due to an error: $e")
        }
        return null
    }






}
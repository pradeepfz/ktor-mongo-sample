package com.example.infrastructure


import com.example.entity.User
import com.example.repository.UserRepository
import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.bson.BsonValue
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import kotlinx.coroutines.flow.firstOrNull

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
    override suspend fun deleteById(objectId: ObjectId): Long {
        try {
            val result = mongoDatabase.getCollection<User>(USER).deleteOne(Filters.eq("_id", objectId))
            return result.deletedCount
        } catch (e: MongoException) {
            System.err.println("Unable to delete due to an error: $e")
        }
        return 0
    }

    override suspend fun findById(objectId: ObjectId): User? =
        mongoDatabase.getCollection<User>(USER).withDocumentClass<User>()
            .find(Filters.eq("_id", objectId))
            .firstOrNull()

    override suspend fun updateOne(objectId: ObjectId, user: User): Long {
        try {
            val query = Filters.eq("_id", objectId)
            val updates = Updates.combine(
                Updates.set(User::age.name, user.age)
            )
            val options = UpdateOptions().upsert(true)
            val result =
                mongoDatabase.getCollection<User>(USER)
                    .updateOne(query, updates, options)

            return result.modifiedCount
        } catch (e: MongoException) {
            System.err.println("Unable to update due to an error: $e")
        }
        return 0
    }





}
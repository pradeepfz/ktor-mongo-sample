package com.example.entity
import com.example.response.UserResponse
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    @BsonId
    val id: ObjectId,
    val name: String,
    val age: Int
){
    fun toResponse() = UserResponse(
        id = id.toString(),
        name = name,
        age = age
    )
}

package com.example.plugins
import com.example.models.Users
import com.example.repository.UserRepository
import com.example.request.UserRequest
import com.example.request.toDomain
import com.mongodb.reactivestreams.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.*
import org.koin.ktor.ext.inject

data class User(
    val username: String,
    val age: Int
)

fun Application.helloModule() {
    val repository by inject<UserRepository>()
    routing {

        get("/api") {
//            val result = collection.find()
//            println("$result+s")
//            call.respond(result)
        }

        post("/text") {
//            val key = call.request.queryParameters["key"]
            val requestData = call.receive<UserRequest>()
//            val res = User("${requestData.name}", age = requestData.age)
//            collection.insertOne()
            val user = repository.insertOne(requestData.toDomain())
            call.respond(HttpStatusCode.Created, "Created fitness with id $user")
        }

        put("/text/{id}") {
            val id = call.parameters["id"]
            val requestData = call.receive<String>()
            call.respondText("Id: $id, Data: $requestData")
        }

        delete("/delete/{id}") {
            val resourceId = call.parameters["id"]
            call.respondText("Id: $resourceId")
        }
    }
}



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
import org.bson.types.ObjectId
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

        delete("/{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                text = "Missing user id",
                status = HttpStatusCode.BadRequest
            )
            val delete: Long = repository.deleteById(ObjectId(id))
            if (delete == 1L) {
                return@delete call.respondText("user Deleted successfully", status = HttpStatusCode.OK)
            }
            return@delete call.respondText("user not found", status = HttpStatusCode.NotFound)
        }

        get("/{id?}") {
            val id = call.parameters["id"]
            if (id.isNullOrEmpty()) {
                return@get call.respondText(
                    text = "Missing id",
                    status = HttpStatusCode.BadRequest
                )
            }
            repository.findById(ObjectId(id))?.let {
                call.respond(it.toResponse())
            } ?: call.respondText("No records found for id $id")
        }

        put("/{id?}") {
            val id = call.parameters["id"] ?: return@put call.respondText(
                text = "Missing user id",
                status = HttpStatusCode.BadRequest
            )
            val updated = repository.updateOne(ObjectId(id), call.receive())
            call.respondText(
                text = if (updated == 1L) "user updated successfully" else "user not found",
                status = if (updated == 1L) HttpStatusCode.OK else HttpStatusCode.NotFound
            )
        }
    }
}



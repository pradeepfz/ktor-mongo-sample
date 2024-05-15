package com.example

import com.example.infrastructure.UserRepositoryImpl
import com.example.plugins.*
import com.example.repository.UserRepository
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.requestvalidation.*
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.serialization.kotlinx.json.*
import org.koin.dsl.module
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.serialization.gson.gson
import org.koin.ktor.plugin.Koin


fun main(args: Array<String>): Unit = EngineMain.main(args)
fun Application.module() {
    install(ContentNegotiation) {
        gson {
        }
    }
    install(Koin) {
        modules(module {
            single {
                MongoClient.create(
                    environment.config.propertyOrNull("ktor.mongo.url")?.getString()
                        ?: throw RuntimeException("Failed to access mongo")
                )
            }
            single {
                get<MongoClient>().getDatabase(
                    environment.config.property("ktor.mongo.database").getString()
                )
            }
        },
            module {
                single<UserRepository> { UserRepositoryImpl(get()) }
            })
    }
    helloModule()

//    install(RequestValidation)
//    configureSerialization()
//    configureRouting()
//    helloModule()
}

//fun main(args: Array<String>) {
//    io.ktor.server.netty.EngineMain.main(args)
//}
//fun main(args: Array<String>): Unit{
////    val client: MongoClient = MongoClients.create("mongodb+srv://pradeepj:123456789canny@cluster.tmkyfhn.mongodb.net/?retryWrites=true&w=majority&appName=Cluster")
////    val database: MongoDatabase = client.getDatabase("sample-mango")
//    embeddedServer(
//        Netty,
//        port = 8081,
//        host = "0.0.0.0",
//
//    ){
////        module()
//    }.start(wait = true)
//
//}
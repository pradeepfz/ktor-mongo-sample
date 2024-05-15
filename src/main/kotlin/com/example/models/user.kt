package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Users(val name: String, val age: Int, val id: String?=null)



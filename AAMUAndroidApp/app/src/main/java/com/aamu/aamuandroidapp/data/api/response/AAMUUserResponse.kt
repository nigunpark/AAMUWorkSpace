package com.aamu.aamuandroidapp.data.api.response

data class AAMUUserResponse(
    val member : Member,
    val token : String
)

data class Member(
    val username : String,
    val password : String,
    val authority : String
)
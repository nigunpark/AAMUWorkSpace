package com.aamu.aamuandroidapp.data.api.response

data class AAMUUserResponse(
    var member : Member,
    var userprofile : String,
    var token : String
)

data class Member(
    var username : String,
    var password : String,
    var authority : String
)
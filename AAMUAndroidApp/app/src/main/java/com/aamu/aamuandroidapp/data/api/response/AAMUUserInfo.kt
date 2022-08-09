package com.aamu.aamuandroidapp.data.api.response

data class AAMUUserInfo(
    var id : String? = null,
    var email : String? = null,
    var pwd : String? = null,
    var name : String? =null,
    var gender : String? = null,
    var phonenum : String? = null,
    var addrid : String? = null,
    var self : String? = null,
    var joindate : Long? = null,
    var userprofile : String? = null,
    var socialnum : String? = null,
    var theme : List<String>? = null
)
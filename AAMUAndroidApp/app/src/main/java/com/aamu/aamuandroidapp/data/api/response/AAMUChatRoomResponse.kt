package com.aamu.aamuandroidapp.data.api.response

data class AAMUChatRoomResponse (
    var roomno: Int? = 0,
    var fromid: String? = null,
    var toid: String? = null,
    var lastmessage: String? = null,
    var senddate: Long? = null,
    var frompro: String? = null,
    var topro: String? = null
)

data class postRoomResponse(
    var roomno: Int? = 0,
    var chatinglist : List<AAMUChatingMessageResponse>? = null
)

data class AAMUChatingMessageResponse (
    var roomno : Int? = 0,
    var authid : String? = null,
    var missage : String? = null,
    var senddate : Long? = 0L,
    var authpro : String? = null
)
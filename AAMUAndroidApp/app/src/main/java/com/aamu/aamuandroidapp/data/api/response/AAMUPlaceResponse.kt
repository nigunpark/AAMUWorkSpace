package com.aamu.aamuandroidapp.data.api.response

data class AAMUPlaceResponse(
    var areacode : Int? = null,
    var sigungucode : Int? = null,
    var contentid : Int? = null,
    var contenttypeid : Int? = null,
    var mapx : Double? = null,
    var mapy : Double? = null,
    var title : String? = null,
    var addr : String? = null,
    var bigimage : String? = null,
    var smallimage : String? = null,
    var playtime : String? = null,
    var tel : String? = null,
    var resttime : String? = null,
    var checkin : String? = null,
    var checkout : String? = null,
    var url : String? = null,
    var kakaokey : String? = null,
    var menu : String? = null,
    var park : String? = null,
    var charge : String? = null,
    var eventstart : String? = null,
    var eventend : String? = null,
    var eventtime : String? = null,
    var atime : String? = null,
    var table : String? = null,
    var star : Double? = null,
    var commentInfo : List<CommentInfo>? = null
)

data class CommentInfo(
    var aamuPoint : Int? = null,
    var aamuContents : String? = null
)

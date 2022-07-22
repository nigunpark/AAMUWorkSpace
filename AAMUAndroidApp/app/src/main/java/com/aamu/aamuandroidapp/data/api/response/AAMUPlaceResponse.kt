package com.aamu.aamuandroidapp.data.api.response

data class AAMUPlaceResponse(
    var areacode : Int?,
    var sigungucode : Int?,
    var contentid : Int?,
    var contenttypeid : Int?,
    var mapx : Double?,
    var mapy : Double?,
    var title : String?,
    var addr : String?,
    var bigimage : String?,
    var smallimage : String?,
    var playtime : String?,
    var tel : String?,
    var resttime : String?,
    var checkin : String?,
    var checkout : String?,
    var url : String?,
    var kakaokey : String?,
    var menu : String?,
    var park : String?,
    var charge : String?,
    var eventstart : String?,
    var eventend : String?,
    var eventtime : String?,
    var atime : String?,
    var table : String?,
    var star : Double?,
    var commentInfo : List<CommentInfo>?
)

data class CommentInfo(
    var aamuPoint : Int?,
    var aamuContents : String?
)

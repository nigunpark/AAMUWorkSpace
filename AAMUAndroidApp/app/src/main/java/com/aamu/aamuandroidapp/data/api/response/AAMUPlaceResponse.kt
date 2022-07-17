package com.aamu.aamuandroidapp.data.api.response

import java.io.Serializable

data class AAMUPlaceResponse(
    val areacode : Int?,
    val sigungucode : Int?,
    val contentid : Int?,
    val contenttypeid : Int?,
    val mapx : Double?,
    val mapy : Double?,
    val title : String?,
    val addr : String?,
    val bigimage : String?,
    val smallimage : String?,
    val playtime : String?,
    val tel : String?,
    val resttime : String?,
    val checkin : String?,
    val checkout : String?,
    val url : String?,
    val kakaokey : String?,
    val menu : String?,
    val park : String?,
    val charge : String?,
    val eventstart : String?,
    val eventend : String?,
    val eventtime : String?,
    val atime : String?,
    val table : String?,
    val star : Double?,
    val commentInfo : List<CommentInfo>?
) : Serializable

data class CommentInfo(
    val aamuPoint : Int?,
    val aamuContents : String?
)

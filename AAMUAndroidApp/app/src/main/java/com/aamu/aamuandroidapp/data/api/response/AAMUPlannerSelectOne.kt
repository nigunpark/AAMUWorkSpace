package com.aamu.aamuandroidapp.data.api.response

import java.util.*

data class AAMUPlannerSelectOne(
    var title : String?,
    var rbn : Int?,
    var route : Objects?,
    var id : String?,
    var routeDate: Long?,
    var plannerdate : String?,
    var routeMap : Map<String?,List<Place?>?>?,
    var smallImage : String?
)
data class Place(
    var rbn : Int?,
    var contentid : Int?,
    var contenttypeid : Int?,
    var starttime : Int?,
    var dto : AAMUPlaceResponse?,
    var day : Int?,
    var atime : Int?,
    var mtime : Int?,
    var endtime : Int?,
    var comment : String?
)
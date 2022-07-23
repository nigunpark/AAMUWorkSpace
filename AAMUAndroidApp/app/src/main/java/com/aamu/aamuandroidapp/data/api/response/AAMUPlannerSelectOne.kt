package com.aamu.aamuandroidapp.data.api.response

import java.util.*

data class AAMUPlannerSelectOne(
    var title : String? = null,
    var rbn : Int? = null,
    var route : Objects? = null,
    var id : String? = null,
    var routeDate: Long? = null,
    var plannerdate : String? = null,
    var routeMap : Map<String?,List<Place?>?>? = null,
    var smallImage : String? = null
)
data class Place(
    var rbn : Int? = null,
    var contentid : Int? = null,
    var contenttypeid : Int? = null,
    var starttime : Int? = null,
    var dto : AAMUPlaceResponse? = null,
    var day : Int? = null,
    var atime : Int? = null,
    var mtime : Int? = null,
    var endtime : Int? = null,
    var comment : String? = null
)
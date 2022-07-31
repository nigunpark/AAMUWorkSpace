package com.aamu.aamuandroidapp.data.api.response

import java.sql.Date

data class AAMUBBSResponse(
    var rbn: Int? = 0,
    var title: String? = null,
    var content: String? = null,
    var postdate: Long? = null,
    var themeid: Int? = 0,
    var themename: String? = null,
    var themeimg: String? = null,
    var id: String? = null,
    var photo: List<String>? = null,
    var starttime: Long = 0,//출발시간
    var planner: AAMUPlannerSelectOne? = null,
    var routeList: List<Place>? = null,
    var reviewList: List<Review>? = null,
    var rateavg: Double? = 0.0
)

data class Review(
    var rbn: Int? = 0,
    var id: String? = null,
    var rate: Int? = 0,
    var review: String? = null,
    var ratedate: Date? = null,
    var rno: String? = null,
)
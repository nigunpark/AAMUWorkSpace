package com.aamu.aamuandroidapp.data.api.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AAMUKakaoReviewResponse(
    @JsonProperty("basic_info")
    val basicInfo : BasicInfo? = null,
    @JsonProperty("comment_info")
    val commentInfo : List<kakaoCommentInfo>? = null
)
data class BasicInfo(
    val name : String? = null,
    val star : Double? = 0.0,
    val score : Int? = 0,
    val feedback : Int? = 0
)
data class kakaoCommentInfo(
    val username : String? = null,
    val point : Int? = 0,
    val review : String? = null
)

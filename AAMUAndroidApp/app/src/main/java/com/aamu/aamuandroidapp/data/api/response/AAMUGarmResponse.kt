package com.aamu.aamuandroidapp.data.api.response

import java.sql.Date

data class AAMUGarmResponse(
    var lno: String? = null,
    var id: String? = null,
    var ctitle: String? = null,
    var content: String? = null,
    var postdate: Long? = null,
    var likecount: String? = null,
    var islike: Boolean? = null,
    var rcount: String? = null,
    var userprofile: String? = null,
    var commuComment: GramComment? = null,
    var commuCommentList: List<GramComment>? = null,
    var photo: List<String>? = null,
    var contentid: String? = null,
    var title: String? = null,
    var searchtotalcount: Int? = 0,
    var tname: List<String>? = null,
    var name: String? = null,
    var totalcount: Int? = 0,
    var followercount: Int? = 0,
    var followingcount: Int? = 0
)


data class GramComment(
    var cno: String? = null,
    var id: String? = null,
    var lno: String? = null,
    var reply: String? = null,
    var replydate: Long? = null,
    var userprofile: String? = null
)
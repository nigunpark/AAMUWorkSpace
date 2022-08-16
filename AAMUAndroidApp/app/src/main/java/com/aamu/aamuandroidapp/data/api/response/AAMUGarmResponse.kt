package com.aamu.aamuandroidapp.data.api.response

data class AAMUGarmResponse(
    var lno: Int? = null,
    var id: String? = null,
    var ctitle: String? = null,
    var content: String? = null,
    var postdate: Long? = 0,
    var likecount: Int? = 0,
    var islike: Boolean? = false,
    var rcount: String? = null,
    var userprofile: String? = null,
    var commuComment: GramComment? = null,
    var commuCommentList: List<GramComment>? = emptyList(),
    var photo: List<String>? = null,
    var contentid: String? = null,
    var title: String? = null,
    var searchtotalcount: Int? = 0,
    var tname: List<String>? = null,
    var name: String? = null,
    var totalcount: Int? = 0,
    var followercount: Int? = 0,
    var followingcount: Int? = 0,
    var isFollower: Boolean? = false,
    var recommenduser: List<AAMUGarmResponse>? = null
)


data class GramComment(
    var cno: Int? = null,
    var id: String? = null,
    var lno: Int? = null,
    var reply: String? = null,
    var replydate: Long? = null,
    var userprofile: String? = null
)
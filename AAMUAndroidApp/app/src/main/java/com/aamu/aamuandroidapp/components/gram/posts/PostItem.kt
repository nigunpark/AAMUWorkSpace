package com.aamu.aamuandroidapp.components.gram.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aamu.aamuandroidapp.data.DemoDataProvider
import com.aamu.aamuandroidapp.data.model.Tweet
import com.aamu.aamuandroidapp.components.gram.profile.ProfileSection
import com.aamu.aamuandroidapp.components.gram.profile.ProfileSectionSizes
import com.aamu.aamuandroidapp.data.api.response.AAMUGarmResponse
import java.text.SimpleDateFormat

@Composable
fun PostItem(
    gram: AAMUGarmResponse,
    isLiked: Boolean?,
    onLikeClicked: () -> Unit,
    onCommentsClicked: () -> Unit,
    onSendClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ProfileSection(
            firstImageId = gram.userprofile,
            text = gram.id ?: "",
            size = ProfileSectionSizes.medium(),
            iconRight = {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "See more options"
                )
            }
        )
        PostImage(
            imageId = gram.photo?.getOrNull(0),
            contentDescription = gram.title,
            modifier = Modifier.padding(top = 8.dp)
        )
        PostInteractionBar(
            isLiked = isLiked ?: false,
            onLikeClicked = onLikeClicked,
            onCommentsClicked = onCommentsClicked,
            onSendClicked = onSendClicked,
        )
        if(gram.commuCommentList?.size == 0){
            ProfileSection(
                firstImageId = null,
                text = "아직 아무도 좋아요를 누르지 않았어요"
            )
        }
        else {
            ProfileSection(
                firstImageId = gram.commuCommentList?.getOrNull(0)?.userprofile,
                text = "${gram.commuComment?.id}님이 댓글을 달고 ${gram.likecount}명이 좋아요를 눌렀어요"
            )
        }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "${gram.commuCommentList?.size}개의 댓글이 달렸어요",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
            )
            val formatter = SimpleDateFormat("yyyy년 MM월 dd일")
            val time = formatter.format(gram.postdate)
            Text(
                text = "${time} 포스팅됨",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
            )
        }
    }
}

@Preview
@Composable
fun PostItemPreview() {
//    PostItem(
//        post = DemoDataProvider.tweet,
//        isLiked = true,
//        onLikeClicked = {},
//        onCommentsClicked = {},
//        onSendClicked = {}
//    )
}
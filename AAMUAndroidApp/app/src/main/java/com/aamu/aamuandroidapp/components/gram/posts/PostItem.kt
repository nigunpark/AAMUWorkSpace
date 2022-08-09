package com.aamu.aamuandroidapp.components.gram.posts

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aamu.aamuandroidapp.components.gram.profile.ProfilePicture
import com.aamu.aamuandroidapp.data.DemoDataProvider
import com.aamu.aamuandroidapp.data.model.Tweet
import com.aamu.aamuandroidapp.components.gram.profile.ProfileSection
import com.aamu.aamuandroidapp.components.gram.profile.ProfileSectionSize
import com.aamu.aamuandroidapp.components.gram.profile.ProfileSectionSizes
import com.aamu.aamuandroidapp.data.api.response.AAMUGarmResponse
import com.aamu.aamuandroidapp.ui.theme.twitterColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.text.SimpleDateFormat

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostItem(
    gram: AAMUGarmResponse,
    isLiked: Boolean?,
    onLikeClicked: (Int) -> Unit,
    onCommentsClicked: (Int) -> Unit,
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
        if(gram.photo != null) {
            val pagerState = rememberPagerState(
                pageCount = gram.photo?.size ?: 0,
                initialOffscreenLimit = 2,
                infiniteLoop = false,
                initialPage = 0
            )
            HorizontalPager(state = pagerState) { index ->
                PostImage(
                    imageId = gram.photo?.getOrNull(index),
                    contentDescription = gram.title,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        PostInteractionBar(
            lno = gram.lno!!,
            isLiked = isLiked ?: false,
            onLikeClicked = onLikeClicked,
            onCommentsClicked = onCommentsClicked,
            onSendClicked = onSendClicked,
        )
        var showMore by remember { mutableStateOf(false) }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween(100))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { showMore = !showMore },
            verticalAlignment = if(showMore) Alignment.Top else Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(
                imageId = gram.userprofile,
                contentDescription = null,
                size = ProfileSectionSizes.small().profileIconSize
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = gram.id ?: "",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier
                .width(10.dp)
                .height(10.dp))
            if(showMore){
                Column {
                    androidx.compose.material3.Text(
                        text = "${gram.content}",
                        style = ProfileSectionSizes.small().textStyle,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .padding(start = 8.dp),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    androidx.compose.material3.Text(
                        text = "${gram.tname?.joinToString(" ")}",
                        style = ProfileSectionSizes.small().textStyle,
                        color = twitterColor,
                        modifier = Modifier
                            .padding(start = 8.dp),
                    )
                }
            }
            else {

                androidx.compose.material3.Text(
                    text = "${gram.content}",
                    style = ProfileSectionSizes.small().textStyle,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .padding(start = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Row {
                Text(
                    text = "${gram.likecount}명이 좋아요   ",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = 4.dp),
                    color = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
                )
                Text(
                    text = "${gram.commuCommentList?.size}개의 댓글",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = 4.dp),
                    color = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
                )
            }
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
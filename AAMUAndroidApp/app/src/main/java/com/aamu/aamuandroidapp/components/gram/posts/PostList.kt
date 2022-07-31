package com.aamu.aamuandroidapp.components.gram.posts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.aamu.aamuandroidapp.data.api.response.AAMUGarmResponse
import com.aamu.aamuandroidapp.data.model.Tweet
import kotlin.random.Random

@Composable
fun PostList(
    gramList: List<AAMUGarmResponse>,
    onLikeClicked: () -> Unit,
    onCommentsClicked: () -> Unit,
    onSendClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(gramList) {
            PostItem(
                gram = it,
                isLiked = it.islike,
                onLikeClicked = onLikeClicked,
                onCommentsClicked = onCommentsClicked,
                onSendClicked = onSendClicked
            )
            Divider(modifier = Modifier.alpha(0.1f))
        }
    }
}
package com.aamu.aamuandroidapp.components.gram

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModelFactory
import com.aamu.aamuandroidapp.data.DemoDataProvider
import com.aamu.aamuandroidapp.data.model.Tweet
import com.aamu.aamuandroidapp.components.gram.posts.PostList

@Composable
fun AAMUgramHome(
    onLikeClicked: () -> Unit,
    onCommentsClicked: () -> Unit,
    onSendClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    onMessagingClicked: () -> Unit
) {

    val viewModel : AAMUgramViewModel = viewModel(
        factory = AAMUgramViewModelFactory(LocalContext.current)
    )

    val gramList by viewModel.aamuGramList.observeAsState(emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "이런곳은 어때") },
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    elevation = 8.dp,
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_instagram),
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onMessagingClicked) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_send),
                                contentDescription = "Go to messaging screen",
                            )
                        }
                    }
                )
            },
            content = {
                Surface(modifier = Modifier.padding(it)) {
                    Column {
                        PostList(
                            gramList = gramList,
                            onLikeClicked = onLikeClicked,
                            onCommentsClicked = onCommentsClicked,
                            onSendClicked = onSendClicked
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewInstagramHome() {
//    AAMUgramHome(
//        posts = DemoDataProvider.tweetList.filter { it.tweetImageId != 0 },
//        profiles = DemoDataProvider.tweetList,
//        onLikeClicked = {},
//        onCommentsClicked = {},
//        onSendClicked = {},
//        onProfileClicked = {},
//        onMessagingClicked = {}
//    )
}
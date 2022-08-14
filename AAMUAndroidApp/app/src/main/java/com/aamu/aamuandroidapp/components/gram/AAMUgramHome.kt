package com.aamu.aamuandroidapp.components.gram

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.aamu.aamuandroidapp.ui.theme.cyan200

@Composable
fun AAMUgramHome(
    onLikeClicked: (Int) -> Unit,
    onCommentsClicked: (Int) -> Unit,
    onSendClicked: () -> Unit,
    onPostingClicked: () -> Unit,
    onMessagingClicked: () -> Unit
) {

    val viewModel : AAMUgramViewModel = viewModel(
        factory = AAMUgramViewModelFactory(LocalContext.current)
    )

    viewModel.getGramList()

    val gramList by viewModel.aamuGramList.observeAsState(emptyList())
    val error by viewModel.errorLiveData.observeAsState()

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
                        IconButton(onClick = onPostingClicked) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_outlined_add),
                                contentDescription = "포스팅",
                                tint = Color.Black
                            )
                        }
                        IconButton(onClick = onMessagingClicked) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_send),
                                contentDescription = "메시지",
                                tint = Color.Black
                            )
                        }
                    }
                )
            },
            content = {
                Surface(modifier = Modifier.padding(it)) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        if (gramList.isNotEmpty()) {
                            PostList(
                                gramList = gramList,
                                onLikeClicked = onLikeClicked,
                                onCommentsClicked = onCommentsClicked,
                                onSendClicked = onSendClicked
                            )
                        } else {
                            if (error.isNullOrEmpty()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(24.dp),
                                    color = cyan200
                                )
                            } else {
                                Text(
                                    text = error ?: "Unknown error",
                                    modifier = Modifier,
                                    color = MaterialTheme.colors.error
                                )
                            }
                        }
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
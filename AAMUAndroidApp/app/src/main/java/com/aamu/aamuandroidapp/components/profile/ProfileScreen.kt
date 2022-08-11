package com.aamu.aamuandroidapp.components.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.data.api.response.AAMUUserInfo
import com.aamu.aamuandroidapp.ui.theme.aamuorange
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.guru.composecookbook.profile.TopScrollingContent

const val initialImageFloat = 170f
const val name = "Gurupreet Singh"
const val email = "gurpreet.usit@gmail.com"
const val twitterUrl = "https://www.twitter.com/_gurupreet"
const val linkedInUrl = "https://www.linkedin.com/in/gurupreet-singh-491a7668/"
const val githubUrl = "https://github.com"
const val githubRepoUrl = "https://github.com/Gurupreet/ComposeCookBook"

//NOTE: This stuff should usually be in a parent activity/Navigator
// We can pass callback to profileScreen to get the click.
internal fun launchSocialActivity(context: Context, socialType: String) {
    val intent = when (socialType) {
        "github" -> Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
        "repository" -> Intent(Intent.ACTION_VIEW, Uri.parse(githubRepoUrl))
        "linkedin" -> Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
        else -> Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl))
    }
    context.startActivity(intent)
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen() {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { testTag = "Profile Screen" }
        ) {
            val viewModel :ProfileScreenViewModel = viewModel(
                factory = ProfileScreenViewModelFactory(LocalContext.current)
            )
            viewModel.getUserInfo()
            viewModel.getUserGram()
            val userinfo by viewModel.userinfo.observeAsState()
            val userGram by viewModel.userGramdata.observeAsState(emptyList())

            val error by viewModel.errorLiveData.observeAsState()

            if(userinfo?.id != null && userGram.isNotEmpty() ) {
                val scrollState = rememberScrollState(0)
                TopBackground()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = scrollState)
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    TopScrollingContent(scrollState, userinfo!!)
                    Spacer(modifier = Modifier.height(20.dp))
                    BottomScrollingContent(userinfo!!,userGram,viewModel)
                    Spacer(modifier = Modifier.height(100.dp))
                }
                TopAppBarView(scrollState.value.toFloat(),userinfo!!)
            }
            else {
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

@Composable
fun TopAppBarView(scroll: Float, userinfo: AAMUUserInfo) {
    if (scroll > initialImageFloat + 5) {
        TopAppBar(
            title = {
                Text(text = "${userinfo.id}")
            },
            navigationIcon = {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userinfo.userprofile ?: R.drawable.no_image)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                )
            },
            actions = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            },
            backgroundColor = Color.White
        )
    }
}

@Composable
private fun TopBackground() {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color.White, aamuorange.copy(alpha = 0.5f)),
        startY = 100f,  // 1/3
        endY = 1000f
    )
    Spacer(
        modifier = Modifier
            .height(1000.dp)
            .fillMaxWidth()
            .background(gradient)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ShowProfileScreen() {
    ProfileScreen()
}
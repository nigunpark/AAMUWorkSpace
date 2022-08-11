package com.guru.composecookbook.profile

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.profile.initialImageFloat
import com.aamu.aamuandroidapp.components.profile.name
import com.aamu.aamuandroidapp.data.api.response.AAMUUserInfo
import com.aamu.aamuandroidapp.ui.theme.materialTypography

@Composable
fun TopScrollingContent(scrollState: ScrollState, userinfo: AAMUUserInfo) {
    val visibilityChangeFloat = scrollState.value > initialImageFloat - 20
    Row {
        AnimatedImage(scroll = scrollState.value.toFloat(),userinfo)
        Column(
            modifier = Modifier
                .padding(start = 8.dp, top = 48.dp)
                .alpha(animateFloatAsState(if (visibilityChangeFloat) 0f else 1f).value)
        ) {
            Text(
                text = "${userinfo.id}",
                style = materialTypography.headlineSmall.copy(fontSize = 18.sp),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "${userinfo.email}",
                style = materialTypography.labelMedium
            )
        }
    }
}

@Composable
fun AnimatedImage(scroll: Float, userinfo: AAMUUserInfo) {
    val dynamicAnimationSizeValue = (initialImageFloat - scroll).coerceIn(36f, initialImageFloat)
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userinfo.userprofile ?: R.drawable.no_image)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop
        ),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = Modifier
            .padding(start = 16.dp)
            .size(animateDpAsState(Dp(dynamicAnimationSizeValue)).value)
            .clip(CircleShape)
    )
}

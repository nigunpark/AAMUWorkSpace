package com.aamu.aamuandroidapp.components.gram.posts

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.data.DemoDataProvider

@Composable
fun PostImage(
    imageId: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageId ?: R.drawable.no_image)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop
        ),
        modifier = modifier.fillMaxWidth().height(450.dp),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun PostImagePreview() {
    PostImage(
        imageId = null,
        contentDescription = null
    )
}
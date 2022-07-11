package com.aamu.aamuandroidapp.components.gram.posts

import androidx.compose.foundation.layout.Row
import androidx.compose.material.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun PostInteractionBar(
    isLiked: Boolean,
    onLikeClicked: () -> Unit,
    onCommentsClicked: () -> Unit,
    onSendClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        IconToggleButton(checked = isLiked, onCheckedChange = { onLikeClicked() }) {
            val icon = if (isLiked) FaIcons.Heart else FaIcons.HeartRegular
            val tint = if (isLiked) Color.Red else Color.Black
            FaIcon(faIcon = icon, tint = tint,)
        }
        IconToggleButton(checked = false, onCheckedChange = { onCommentsClicked() }) {
            FaIcon(faIcon = FaIcons.CommentAltRegular, tint = Color.Black)
        }
        IconToggleButton(checked = false, onCheckedChange = { onSendClicked() }) {
            FaIcon(faIcon = FaIcons.PaperPlaneRegular, tint = Color.Black)
        }
    }
}

@Preview
@Composable
fun PostInteractionBarPreview() {
    PostInteractionBar(
        isLiked = true,
        onLikeClicked = {},
        onCommentsClicked = {},
        onSendClicked = {}
    )
}
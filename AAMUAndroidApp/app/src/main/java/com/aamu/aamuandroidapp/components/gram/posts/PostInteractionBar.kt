package com.aamu.aamuandroidapp.components.gram.posts

import androidx.compose.foundation.layout.Row
import androidx.compose.material.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aamu.aamuandroidapp.components.gram.AAMUgramViewModel
import com.aamu.aamuandroidapp.components.gram.AAMUgramViewModelFactory
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun PostInteractionBar(
    lno : Int,
    isLiked: Boolean,
    onLikeClicked: (Int) -> Unit,
    onCommentsClicked: (Int) -> Unit,
    onSendClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        val muLike = remember { mutableStateOf(isLiked) }

        IconToggleButton(checked = muLike.value, onCheckedChange = {
            onLikeClicked(lno)
            muLike.value = !muLike.value
        }) {
            val icon = if (muLike.value) FaIcons.Heart else FaIcons.HeartRegular
            val tint = if (muLike.value) Color.Red else Color.Black
            FaIcon(faIcon = icon, tint = tint,)
        }
        IconToggleButton(checked = false, onCheckedChange = { onCommentsClicked(lno) }) {
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
        lno = 0,
        isLiked = true,
        onLikeClicked = {},
        onCommentsClicked = {},
        onSendClicked = {}
    )
}
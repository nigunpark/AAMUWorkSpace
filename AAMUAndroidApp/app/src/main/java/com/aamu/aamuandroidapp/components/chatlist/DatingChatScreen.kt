package com.aamu.aamuandroidapp.components.chatlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.aamu.aamuandroidapp.data.AlbumsDataProvider
import com.aamu.aamuandroidapp.ui.theme.modifiers.verticalGradientBackground
import com.aamu.aamuandroidapp.ui.theme.purple

@Composable
fun DatingChatScreen() {
    val items = AlbumsDataProvider.albums.take(6)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalGradientBackground(listOf(Color.White, purple.copy(alpha = 0.2f)))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = items,
                itemContent = {
                    DatingChatItem(it)
                }
            )
        }

    }
}

@Preview
@Composable
fun PreviewDatingChatScreen() {
    DatingChatScreen()
}

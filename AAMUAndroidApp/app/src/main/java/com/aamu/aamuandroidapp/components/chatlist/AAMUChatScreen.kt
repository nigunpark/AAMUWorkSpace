package com.aamu.aamuandroidapp.components.chatlist

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aamu.aamuandroidapp.components.chatlist.chat.JetchatAppBar
import com.aamu.aamuandroidapp.data.AlbumsDataProvider
import com.aamu.aamuandroidapp.ui.theme.amber200
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.aamu.aamuandroidapp.ui.theme.modifiers.verticalGradientBackground
import com.aamu.aamuandroidapp.ui.theme.purple
import com.google.accompanist.insets.statusBarsPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AAMUChatScreen(onClick : (Int,String) -> Unit,backClick : ()->Unit) {

    val preferences : SharedPreferences = LocalContext.current.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
    val userid : String? = preferences.getString("id",null)

    val viewModel : AAMUChatViewModel = viewModel(
        factory = AAMUChatViewModelFactory(LocalContext.current)
    )
    viewModel.getChatingList(userid ?: "")

    val chatingRoomList by viewModel.chatingRoomList.observeAsState(emptyList())
    val error by viewModel.errorLiveData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalGradientBackground(listOf(Color.White, amber200.copy(alpha = 0.2f)))
    ) {
        JetchatAppBar(
            modifier = Modifier.statusBarsPadding(),
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Channel name
                    androidx.compose.material3.Text(
                        text = "채팅 리스트",
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                    )
                }
            },
            backClick = backClick
        )
        if(chatingRoomList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = chatingRoomList,
                    itemContent = {
                        AAMUChatItem(it,userid ?: "",onClick)
                    }
                )
            }
        }
        else{
            if (error.isNullOrEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(24.dp),
                    color = amber200
                )
            } else {
                Text(
                    text = error ?: "Unknown error",
                    modifier = Modifier
                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewAAMUChatScreen() {
//    AAMUChatScreen()
}

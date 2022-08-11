package com.aamu.aamuandroidapp.components.notification

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aamu.aamuandroidapp.data.api.response.AAMUNotiResponse
import com.aamu.aamuandroidapp.fragment.main.MainViewModel
import com.aamu.aamuandroidapp.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Composable
fun NotificationList(
    viewModel: MainViewModel
){
    val notilist by viewModel.notilist.observeAsState()
    val error by viewModel.errorLiveData.observeAsState()

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(
                        modifier = Modifier.height(
                            WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        Text("알림 내역")
                    }
                }
            },
            modifier = Modifier.height(
                WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 56.dp
            ),
            backgroundColor = Color.White
        )

        if (notilist?.isNotEmpty() == true) {
            LazyColumn {
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                WindowInsets.statusBars
                                    .asPaddingValues()
                                    .calculateTopPadding() + 56.dp
                            )
                    )
                }
                itemsIndexed(
                    items = notilist!!,
                    itemContent = { index, noti ->
                        NotificationListItem(index, noti,{ nano ->
                            viewModel.delNotiList(nano)
                        },{nano ->
                            viewModel.updateNotiList(nano)
                        })
                    }
                )
                item {
                    Spacer(
                        modifier = Modifier
                            .height(90.dp)
                            .fillMaxWidth()
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize().align(alignment = Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            WindowInsets.statusBars
                                .asPaddingValues()
                                .calculateTopPadding() + 70.dp
                        )
                )
                if (error.isNullOrEmpty()) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(24.dp),
                        color = cyan200
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

}

@Composable
fun NotificationListItem(index: Int, noti: AAMUNotiResponse, onItemSwiped: (Int) -> Unit,onItemClick: (Int) -> Unit){
    val visible = remember(noti.nano) { mutableStateOf(true) }

    AnimatedVisibility(visible = visible.value) {
        Box(modifier = Modifier
            .background(amber200)
            .clickable {
                onItemClick.invoke(noti.nano!!)
            }) {
            BackgroundListItem(modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(amber200))
            ForegroundListItem(noti, index) {
                visible.value = false
                onItemSwiped.invoke(noti.nano!!)
            }
        }
    }
}

enum class SwipeState {
    SWIPED, VISIBLE, MIDDLE
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForegroundListItem(noti: AAMUNotiResponse, index: Int, onItemSwiped: (Int) -> Unit){
    val swipeableState = androidx.compose.material.rememberSwipeableState(
        initialValue = SwipeState.VISIBLE,
        confirmStateChange = {
            if (it == SwipeState.SWIPED) {
                onItemSwiped.invoke(noti.nano!!)
            }
            true
        }
    )
    val swipeAnchors =
        mapOf(0f to SwipeState.VISIBLE, -1000f to SwipeState.SWIPED, -500f to SwipeState.MIDDLE)

    Row(
        modifier = Modifier
            .swipeable(
                state = swipeableState,
                anchors = swipeAnchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
            .background(Color.White)
            .height(55.dp)
            .padding(vertical = 5.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f)
        ) {
            Text(
                text = noti.amessage ?: "",
                style = typography.h6.copy(fontSize = 16.sp),
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
            )
            val formatter = SimpleDateFormat("yyyy년 MM월 dd일 a h:mm", Locale.KOREA)
            val time = formatter.format(noti.senddate)
            androidx.compose.material3.Text(
                text = "${time}",
                style = typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (noti.readknow == 0) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = Yellow90,
                modifier = Modifier
                    .padding(4.dp)
                    .size(20.dp)
            )
        }
    }
    Divider()
}

@Composable
fun BackgroundListItem(modifier: Modifier) {
    Row(horizontalArrangement = Arrangement.End, modifier = modifier) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
    }
}
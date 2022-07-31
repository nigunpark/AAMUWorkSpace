/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aamu.aamuandroidapp.components.chatlist.chat

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.data.api.response.AAMUChatingMessageResponse
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.aamu.aamuandroidapp.ui.theme.cyan700
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationContent(
    viewModel : ConversationViewModel,
    modifier: Modifier = Modifier,
) {

    val messageList by viewModel.messageList.observeAsState(emptyList())

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Surface(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize().background(cyan200.copy(alpha = 0.1f))) {
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                Messages(
                    messages = messageList,
                    modifier = Modifier.weight(1f),
                    scrollState = scrollState
                )
                UserInput(
                    onMessageSent = { content ->
                        viewModel.sendMessage(content)
                    },
                    resetScroll = {
                        scope.launch {
                            scrollState.scrollToItem(0)
                        }
                    },
                    // Use navigationBarsPadding() imePadding() and , to move the input panel above both the
                    // navigation bar, and on-screen keyboard (IME)
                    modifier = Modifier,
                )
            }
            // Channel name bar floats above the messages
            ChannelNameBar(
                channelName = "ㅁㄴㅇㄻㄴㅇㄹ",
                channelMembers = 1,
                // Use statusBarsPadding() to move the app bar content below the status bar
                modifier = Modifier.statusBarsPadding().background(cyan200.copy(alpha = 0.3f)),
            )
        }
    }
}

@Composable
fun ChannelNameBar(
    channelName: String,
    channelMembers: Int,
    modifier: Modifier = Modifier,
) {
    JetchatAppBar(
        modifier = modifier,
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Channel name
                Text(
                    text = channelName,
                    style = MaterialTheme.typography.titleMedium
                )
                // Number of members
                Text(
                    text = channelMembers.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}

const val ConversationTestTag = "ConversationTestTag"

@Composable
fun Messages(
    messages: List<AAMUChatingMessageResponse>,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        val preferences : SharedPreferences = LocalContext.current.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
        val authorMe : String? = preferences.getString("id",null)
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            // Add content padding so that the content can be scrolled (y-axis)
            // below the status bar + app bar
            // TODO: Get height from somewhere
            contentPadding =
            WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues(),
            modifier = Modifier
                .testTag(ConversationTestTag)
                .fillMaxSize()
        ) {
            for (index in messages.indices) {
                val prevAuthor = messages.getOrNull(index - 1)?.authid
                val nextAuthor = messages.getOrNull(index + 1)?.authid
                val prevDay = messages.getOrNull(index)?.senddate
                val nextDay = messages.getOrNull(index + 1)?.senddate
                val content = messages[index]
                val isFirstMessageByAuthor = prevAuthor != content.authid
                val isLastMessageByAuthor = nextAuthor != content.authid

                var isToday = prevDay.let {
                    if (nextDay == null) {
                        return@let true
                    } else if(it == null){
                        return@let false
                    }
                    else{
                        return@let (nextDay + (1000*60*60*9)) / (1000 * 60 * 60 * 24) != (it + (1000*60*60*9)) / (1000 * 60 * 60 * 24)
                    }
                }

                var isCurrentTime = nextDay?.let { it / (1000*60) } != prevDay?.let { it / (1000*60) }

                item {
                    Message(
                        msg = content,
                        isUserMe = content.authid == authorMe,
                        isFirstMessageByAuthor = isFirstMessageByAuthor,
                        isLastMessageByAuthor = isLastMessageByAuthor,
                        isToday = isToday,
                        isCurrentTime = isCurrentTime
                    )
                }
                if (isToday == true) {
                    item {
                        val formatter = SimpleDateFormat("yyyy년 MM월 dd일")
                        val time = formatter.format(messages.get(index).senddate!!)
                        DayHeader(time)
                    }
                }
            }
        }
        // Jump to bottom button shows up when user scrolls past a threshold.
        // Convert to pixels:
        val jumpThreshold = with(LocalDensity.current) {
            JumpToBottomThreshold.toPx()
        }

        // Show the button if the first visible item is not the first one or if the offset is
        // greater than the threshold.
        val jumpToBottomButtonEnabled by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex != 0 ||
                    scrollState.firstVisibleItemScrollOffset > jumpThreshold
            }
        }

        JumpToBottom(
            // Only show if the scroller is not at the bottom
            enabled = jumpToBottomButtonEnabled,
            onClicked = {
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun Message(
    msg: AAMUChatingMessageResponse,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    isToday: Boolean,
    isCurrentTime: Boolean,
) {
    val borderColor = if (isUserMe) {
        cyan200
    } else {
        cyan700
    }

    val spaceBetweenAuthors = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
    val spaceArrangement = if(isUserMe) Arrangement.End else Arrangement.Start
    Row(modifier = spaceBetweenAuthors.fillMaxWidth(), horizontalArrangement = spaceArrangement) {
        if (isLastMessageByAuthor && !isUserMe || isToday && !isUserMe || isCurrentTime && !isUserMe) {
            // Avatar
            Image(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .border(1.5.dp, borderColor, CircleShape)
                    .border(3.dp, cyan700, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Top),
                painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(msg.authpro ?: R.drawable.no_image)
                            .crossfade(true)
                            .build(),
                contentScale = ContentScale.Crop
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        } else {
            // Space under avatar
            Spacer(modifier = Modifier.width(74.dp))
        }
        AuthorAndTextMessage(
            msg = msg,
            isUserMe = isUserMe,
            isFirstMessageByAuthor = isFirstMessageByAuthor,
            isLastMessageByAuthor = isLastMessageByAuthor,
            isToday = isToday,
            isCurrentTime = isCurrentTime,
            modifier = Modifier
                .padding(end = 16.dp)
        )
    }
}

@Composable
fun AuthorAndTextMessage(
    msg: AAMUChatingMessageResponse,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    isToday: Boolean,
    isCurrentTime: Boolean,
    modifier: Modifier = Modifier
) {
    val spaceArrangement = if(isUserMe) Alignment.End else Alignment.Start
    Column(modifier = modifier, horizontalAlignment = spaceArrangement) {
        if (isToday || isLastMessageByAuthor || isCurrentTime) {
            AuthorNameTimestamp(msg,isUserMe)
        }
        ChatItemBubble(msg, isUserMe)
        if (isFirstMessageByAuthor) {
            // Last bubble before next author
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            // Between bubbles
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun AuthorNameTimestamp(msg: AAMUChatingMessageResponse,isUserMe: Boolean) {
    // Combine author and timestamp for a11y.
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = msg.authid ?: "",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.width(8.dp))
        val formatter = SimpleDateFormat("a h:mm", Locale.KOREA)
        val time = formatter.format(msg.senddate)
        Text(
            text = time,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alignBy(LastBaseline),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DayHeader(dayString: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(16.dp)
    ) {
        DayHeaderLine()
        Text(
            text = dayString,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DayHeaderLine()
    }
}

@Composable
private fun RowScope.DayHeaderLine() {
    Divider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}

@Composable
fun ChatItemBubble(
    message: AAMUChatingMessageResponse,
    isUserMe: Boolean
) {
    val spaceArrangement = if(isUserMe) Alignment.End else Alignment.Start
    val backgroundBubbleColor = if (isUserMe) {
        cyan700.copy(alpha = 0.5f)
    } else {
        cyan200.copy(alpha = 0.5f)
    }

    val ChatBubbleShape = if(isUserMe) RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp) else RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)

    Column(horizontalAlignment =  spaceArrangement) {
        Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShape
        ) {
            ClickableMessage(
                message = message,
                isUserMe = isUserMe
            )
        }
    }
}

@Composable
fun ClickableMessage(
    message: AAMUChatingMessageResponse,
    isUserMe: Boolean
) {
    val uriHandler = LocalUriHandler.current

    val styledMessage = messageFormatter(
        text = message.missage ?: "",
        primary = isUserMe
    )

    ClickableText(
        text = styledMessage,
        style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
        modifier = Modifier.padding(16.dp),
        onClick = {
            styledMessage
                .getStringAnnotations(start = it, end = it)
                .firstOrNull()
                ?.let { annotation ->
                    when (annotation.tag) {
                        SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
                        else -> Unit
                    }
                }
        }
    )
}

@Preview
@Composable
fun channelBarPrev() {
    ChannelNameBar(channelName = "composers", channelMembers = 52)
}

@Preview
@Composable
fun DayHeaderPrev() {
    DayHeader("Aug 6")
}

private val JumpToBottomThreshold = 56.dp

private fun ScrollState.atBottom(): Boolean = value == 0

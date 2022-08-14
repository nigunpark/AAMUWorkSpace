package com.aamu.aamuandroidapp.components.gram.posting

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.ui.theme.aamuorange
import com.aamu.aamuandroidapp.ui.theme.twitterColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AfterPosting(
    viewModel: GramPostingViewModel,
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    title: TextFieldValue,
    ontitleChange: (TextFieldValue) -> Unit,
    content: TextFieldValue,
    oncontentChange: (TextFieldValue) -> Unit,
    location: TextFieldValue,
    onlocationChange: (TextFieldValue) -> Unit,
    hashtag: TextFieldValue,
    onhashtagChange: (TextFieldValue) -> Unit,
    onlocationContentidChange: (Int) -> Unit,
    hashtags: SnapshotStateList<String>
) {

    val context = LocalContext.current

    val contentFocusRequester = FocusRequester()

    val titleInteractionState = remember { MutableInteractionSource() }
    val contentInteractionState = remember { MutableInteractionSource() }
    val locationInteractionState = remember { MutableInteractionSource() }
    val hashtagInteractionState = remember { MutableInteractionSource() }
    val rowInteraction = remember { MutableInteractionSource() }



    Column(modifier = Modifier
        .fillMaxSize()
        .background(aamuorange.copy(alpha = 0.1f))) {
        TextInput(
            title,
            ontitleChange,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(painter = painterResource(R.drawable.ic_title), contentDescription = null, modifier = Modifier.size(20.dp))
            },
            label ={
                Text(text = "제목")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                contentFocusRequester.requestFocus()
            }),
            interactionSource = titleInteractionState
        )
        Spacer(modifier = Modifier.height(20.dp))
        GramContentInput(viewModel,context,content,oncontentChange,contentInteractionState,coroutineScope,pagerState)
        Spacer(modifier = Modifier.height(20.dp))
        var locationMore by remember { mutableStateOf(false) }
        TextInput(
            location,
            onlocationChange,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { locationMore = !locationMore },
            leadingIcon = {
                FaIcon(
                    faIcon = FaIcons.MapMarkerAlt,
                    tint = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
            },
            label ={
                Text(text = "위치 추가")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(),
            interactionSource = locationInteractionState,
            enabled = false
        )
        viewModel.getGramRcentPlaces()
        val recentPlaces by viewModel.recentPlaces.observeAsState(emptyList())
        Column(modifier = Modifier
            .animateContentSize(animationSpec = tween(100))
            .fillMaxWidth()) {
            if(locationMore && recentPlaces.isNotEmpty()){
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(300.dp)) {
                    items(recentPlaces){ place->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onlocationContentidChange.invoke(place.contentid ?: 0)
                                onlocationChange.invoke(
                                    TextFieldValue(
                                        "${place.title}",
                                        location.selection
                                    )
                                )
                                locationMore = false
                            }
                            .padding(horizontal = 40.dp, vertical = 5.dp)
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "${place.title}")
                        }
                        Divider()
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        var hashTagError : String? = null
        KoohaHashTagEditor(
            modifier= Modifier
                .background(Color.White)
                .onKeyEvent {
                    if (it.key.keyCode == Key.Backspace.keyCode) {
                        if (hashtags.isNotEmpty()) {
                            hashtags.removeLast()
                        }
                    }
                    false
                },
            textFieldValue =  hashtag,
            onValueChanged = {
                hashTagError = null
                val values = it.text.split("\\s".toRegex())
                if (values.size >= 2) {
                    if (hashTagError == null) {
                        hashtags.add(values[0])
                        onhashtagChange.invoke(hashtag.copy(text = ""))
                    }
                } else {
                    onhashtagChange.invoke(it)
                }
            },
            focusRequester = FocusRequester(),
            focusedFlow = true,
            textFieldInteraction= hashtagInteractionState,
            label = null,
            placeholder = "해쉬태그",
            rowInteraction = rowInteraction,
            errorMessage = hashTagError,
            listOfChips = hashtags,
            onChipClick={chipIndex ->
                hashtags.removeAt(chipIndex)
            }
        )
    }
}

@Composable
fun TextInput(
    value : TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon : @Composable (()->Unit) = {},
    label : @Composable (()->Unit) = {},
    focusRequester: FocusRequester? = null,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions(),
    interactionSource : MutableInteractionSource,
    trailingIcon: @Composable (()->Unit) = {},
    maxLines : Int = 1,
    singleLine : Boolean = true,
    enabled : Boolean = true,
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .focusRequester(focusRequester ?: FocusRequester()),
        leadingIcon = leadingIcon,
        label = label,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = aamuorange,
            focusedLabelColor = aamuorange,
            disabledTextColor = Color.Black),
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions ,
        interactionSource = interactionSource,
        trailingIcon = trailingIcon,
        maxLines = maxLines,
        enabled = enabled
    )
}



@OptIn(ExperimentalPagerApi::class)
@Composable
fun GramContentInput(
    viewModel: GramPostingViewModel,
    context: Context,
    content: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    contentInteractionState: MutableInteractionSource,
    coroutineScope: CoroutineScope,
    pagerState: PagerState
) {

    val uriArray by viewModel.uriArray.observeAsState(emptyList())

    val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
    val profile : String? = preferences.getString("profile",null)
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .height(200.dp)
        .background(Color.White)
        .fillMaxWidth()) {
        TextInput(
            content,
            onValueChange,
            modifier = Modifier.height(200.dp),
            leadingIcon = {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(profile ?: R.drawable.no_image)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop
                    ),
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            },
            label ={
                Text(text = "내용")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
            keyboardActions = KeyboardActions(),
            interactionSource = contentInteractionState,
            trailingIcon ={},
            maxLines = 20,
            singleLine = false
        )
        Column(modifier = Modifier.width(130.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(uriArray.getOrNull(0) ?: R.drawable.no_image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop
                ),
                modifier = Modifier
                    .size(100.dp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            TextButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = twitterColor
                )
            ) {
                Text(text = "수정")
            }
        }
    }
}


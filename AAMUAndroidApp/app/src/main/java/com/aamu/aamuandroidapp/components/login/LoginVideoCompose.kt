package com.aamu.aamuandroidapp.components.login

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.ui.theme.amber700
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.aamu.aamuandroidapp.ui.theme.cyan500
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons

private fun Context.buildExoPlayer(uri: Uri) =
    ExoPlayer.Builder(this).build().apply {
        setMediaItem(MediaItem.fromUri(uri))
        repeatMode = Player.REPEAT_MODE_ALL
        playWhenReady = true
        prepare()
    }

private fun Context.buildPlayerView(exoPlayer: ExoPlayer) =
    StyledPlayerView(this).apply {
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        useController = false
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }

@Composable
fun LoginVideo(videoUri: Uri,loginfail : Boolean,onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val exoPlayer = remember { context.buildExoPlayer(videoUri) }

    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var hasError by remember { mutableStateOf(false) }

    val emailInteractionState = remember { MutableInteractionSource() }
    val passwordInteractionState = remember { MutableInteractionSource() }

    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory()
    )

    var passwordVisualTransformation by remember {
        mutableStateOf<VisualTransformation>(
            PasswordVisualTransformation()
        )
    }

    DisposableEffect(
        AndroidView(
            factory = { it.buildPlayerView(exoPlayer) },
            modifier = Modifier.fillMaxSize()
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    ProvideWindowInsets {
        Column(
            Modifier
                .navigationBarsWithImePadding()
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.aamulogo)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.FillWidth
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            var loading by remember { mutableStateOf(false) }
            TextInput(username,{username = it},InputType.Name, keyboardActions = KeyboardActions(onNext = {
                passwordFocusRequester.requestFocus()
            }), interactionSource =  emailInteractionState)
            TextInput(password,{password = it},InputType.Password, keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                if (invalidInput(username.text, password.text)) {
                    hasError = true
                    loading = false
                } else {
                    loading = true
                    hasError = false
                    onLoginSuccess.invoke()
                    viewModel.doLogin(username.text, password.text)
                }
            }), focusRequester = passwordFocusRequester, interactionSource =  passwordInteractionState,
                trailingIcon = {
                FaIcon(
                    faIcon = FaIcons.EyeSlash,
                    tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                    modifier = Modifier.clickable(onClick = {
                        passwordVisualTransformation =
                            if (passwordVisualTransformation != VisualTransformation.None) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            }
                    })
                )
            },visualTransformation= passwordVisualTransformation)

            if(loginfail){
                hasError = true
                loading = false
                Text(
                    text = "로그인이 실패하였습니다 아이디 또는 비밀번호를 확인해 주세요",
                    style = TextStyle.Default.copy(
                        color = Color.Red,
                        fontSize = 10.sp
                    )
                )
            }

            Button(onClick = {
                if (invalidInput(username.text, password.text)) {
                    hasError = true
                    loading = false
                } else {
                    loading = true
                    hasError = false
                    onLoginSuccess.invoke()
                    viewModel.doLogin(username.text, password.text)
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(50.dp)
                .clip(CircleShape),colors = ButtonDefaults.buttonColors(cyan500, contentColor = Color.White)) {
                if (loading) {
                    HorizontalDottedProgressBar()
                } else {
                    Text(text = "로그인", fontWeight = FontWeight.Bold)
                }
            }

            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 48.dp)
            )
            androidx.compose.material3.OutlinedButton(
                onClick = { }, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Icon(painter = painterResource(R.drawable.kakaologo), contentDescription = null)
                androidx.compose.material3.Text(
                    text = "카카오톡 로그인",
                    style = TextStyle.Default.copy(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

data class logindata(
    var username : TextFieldValue,
    var password : TextFieldValue,
    var onLoginSuccess: () -> Unit,
    var viewModel: LoginViewModel,
    var hasError : Boolean,
    var loading : Boolean
)

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    object Name : InputType(
        label = "아이디",
        icon = Icons.Default.Person,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    object Password : InputType(
        label = "비밀번호",
        icon = Icons.Default.Lock,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun TextInput(
    value : TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    interactionSource : MutableInteractionSource,
    trailingIcon: @Composable (()->Unit) = {},
    visualTransformation : VisualTransformation? = null
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, null) },
        label = { Text(text = inputType.label) },
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = cyan500,
            focusedLabelColor = cyan500
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = visualTransformation ?: inputType.visualTransformation,
        keyboardActions = keyboardActions ,
        interactionSource = interactionSource,
        trailingIcon = trailingIcon,
    )
}
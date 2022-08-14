package com.aamu.aamuandroidapp.components.gram.posting

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aamu.aamuandroidapp.components.login.HorizontalDottedProgressBar
import com.aamu.aamuandroidapp.pluck.PluckConfiguration
import com.aamu.aamuandroidapp.pluck.ui.Pluck
import com.aamu.aamuandroidapp.pluck.ui.permission.Permission
import com.aamu.aamuandroidapp.ui.theme.twitterColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GramPosting(
    viewModel : GramPostingViewModel,
    goToAppSettings : ()-> Unit,
    navPopBackStack : ()-> Unit
){
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = 2
        , initialOffscreenLimit = 2
        , infiniteLoop = false
        , initialPage = 0)

    val tabIndex = pagerState.currentPage

    var title by remember { mutableStateOf(TextFieldValue("")) }
    var content by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }
    var hashtag by remember { mutableStateOf(TextFieldValue("")) }

    var locationContentid by remember { mutableStateOf(0) }

    val hashtags = remember { mutableStateListOf<String>() }

    val keyboardController = LocalSoftwareKeyboardController.current

    var loading by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "새 게시글 쓰기") },
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    elevation = 8.dp,
                    navigationIcon = {
                        IconButton(onClick = {
                            if(tabIndex == 1){
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            }
                            else if (tabIndex == 0){
                                navPopBackStack.invoke()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        if(tabIndex == 1) {
                            TextButton(
                                onClick = {
                                    loading = true
                                    keyboardController?.hide()
                                    viewModel.postGram(title.text,content.text,locationContentid,hashtags,navPopBackStack)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White,
                                    contentColor = twitterColor,
                                    disabledBackgroundColor = Color.White
                                ),
                                enabled = !loading
                            ) {
                                if(!loading)
                                    Text(text = "포스팅")
                                else{
                                    HorizontalDottedProgressBar(modifier = Modifier, color = twitterColor)
                                }
                            }
                        }
                    }
                )
            },
            content ={
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    HorizontalPager(state = pagerState, dragEnabled = false) {index ->
                        if (index == 0) {
                            Permission(
                                permissions = listOf(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ),
                                goToAppSettings = { goToAppSettings.invoke() }
                            ) {
                                Pluck(
                                    pluckConfiguration = PluckConfiguration(true),
                                    onPhotoSelected = {
                                        if(it.isNotEmpty()){
                                            viewModel.seturiArry(it)
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(1)
                                            }
                                        }
                                        else{
                                            Toast.makeText(context,"사진을 선택해주세요", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                )
                            }
                        }
                        else{
                            AfterPosting(
                                viewModel,
                                coroutineScope,
                                pagerState,
                                title,
                                { title = it },
                                content,
                                { content = it },
                                location,
                                { location = it },
                                hashtag,
                                { hashtag = it },
                                {locationContentid = it},
                                hashtags)
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewGramPosting(){
    GramPosting(GramPostingViewModel(context = LocalContext.current),{},{})
}
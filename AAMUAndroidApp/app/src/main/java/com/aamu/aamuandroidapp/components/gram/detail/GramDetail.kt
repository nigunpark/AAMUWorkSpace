package com.aamu.aamuandroidapp.components.gram.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.gram.profile.ProfilePicture
import com.aamu.aamuandroidapp.components.gram.profile.ProfileSizes
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.google.accompanist.insets.LocalWindowInsets
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GramDetail(lno : Int){
    val viewModel : GramDetailViewModel = viewModel(
        factory = GramDetailViewModelFactory(LocalContext.current)
    )

    viewModel.getGramDetail(lno)

    val gramDetail by viewModel.gramDetail.observeAsState()
    val error by viewModel.errorLiveData.observeAsState()
    if(gramDetail?.lno != null) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            TopAppBar(title = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "백버튼"
                            )
                        }
                        Text(gramDetail?.ctitle ?: "")
                    }
                }
            },
            modifier = Modifier.height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()+56.dp),
            backgroundColor = Color.White)
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
                item {
                    Surface(elevation = 2.dp) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ProfilePicture(
                                imageId = gramDetail?.userprofile,
                                contentDescription = null,
                                size = ProfileSizes.medium
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()) {
                                Row {
                                    Text(
                                        text = gramDetail?.id ?: "",
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = gramDetail?.content ?: "")
                                }
                                Row {
                                    val formatter = SimpleDateFormat("yyyy년 MM월 dd일")
                                    val time = formatter.format(gramDetail?.postdate)
                                    Text(text = "${time}", color = Color.LightGray)
                                }

                            }
                        }
                    }

                }
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                    )
                }
                if (gramDetail?.commuCommentList != null && gramDetail?.commuCommentList?.size != 0) {
                    items(items = gramDetail?.commuCommentList!!) { gramComment ->
                        Row(modifier = Modifier.padding(horizontal = 30.dp, vertical = 7.dp)) {
                            ProfilePicture(
                                imageId = gramComment.userprofile,
                                contentDescription = null,
                                size = ProfileSizes.medium
                            )
                            Column {
                                Row {
                                    Text(
                                        text = gramComment.id ?: "",
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                    Text(text = gramComment.reply ?: "")
                                }
                                Row {
                                    val formatter = SimpleDateFormat("yyyy년 MM월 dd일")
                                    val time = formatter.format(gramDetail?.postdate)
                                    Text(text = "${time}", color = Color.LightGray)
                                }

                            }
                        }
                    }
                } else {

                }
            }
        }
    }
    else{
        if (error.isNullOrEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.padding(24.dp),
                color = cyan200
            )
        } else {
            Text(
                text = error ?: "Unknown error",
                modifier = Modifier,
                color = MaterialTheme.colors.error
            )
        }
    }
}

@Preview
@Composable
fun PreviewGramDetail(){
    GramDetail(lno = 0)
}
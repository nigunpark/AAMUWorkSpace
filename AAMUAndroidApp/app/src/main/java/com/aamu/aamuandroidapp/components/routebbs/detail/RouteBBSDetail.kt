package com.aamu.aamuandroidapp.components.routebbs.detail

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModelFactory
import com.aamu.aamuandroidapp.components.routebbs.InterestTag
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.aamu.aamuandroidapp.ui.theme.modifiers.verticalGradientBackground
import com.aamu.aamuandroidapp.ui.theme.typography
import java.text.SimpleDateFormat

@Composable
fun RouteBBSDetail(
    rbn : Int
){
    val expand = remember { mutableStateOf(false) }
    val viewModel : RouteBBSDetailViewModel = viewModel(
        factory = RouteBBSDetailViewModelFactory(LocalContext.current)
    )
    viewModel.getBBSListOne(rbn)

    val bbsDetail by viewModel.bbsDetail.observeAsState()
    val error by viewModel.errorLiveData.observeAsState()

    if(bbsDetail?.rbn != null){

        val dominantColors = listOf(Color.White, cyan200)

        LazyColumn(
            modifier = Modifier
                .verticalGradientBackground(dominantColors)
                .padding(
                    animateDpAsState(
                        if (expand.value) 1.dp else 120.dp,
                        tween(350)
                    ).value
                )
        ) {
            item {
                val painter=rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(bbsDetail!!.photo?.getOrNull(0)?: R.drawable.no_image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painter,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .height(
                            600.dp
                        )
                        .fillMaxWidth(),
                )
                when (painter.state) {
                    is AsyncImagePainter.State.Success -> expand.value = true
                    else -> expand.value = false
                }
            }
            item {
                Column(modifier = Modifier.background(Color.White)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = bbsDetail!!.title ?: "",
                            modifier = Modifier.padding(8.dp),
                            style = typography.h6
                        )
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                    Row {
                        InterestTag(text = bbsDetail!!.themename ?: "")
                    }
                    val formatter = SimpleDateFormat("yyyy년 MM월 dd일")
                    val time = formatter.format(bbsDetail!!.postdate)
                    Text(
                        text = "${time} 에 포스팅됨",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = typography.h6.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = "평점  •  ${bbsDetail!!.rateavg}/5",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = typography.h6.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = bbsDetail!!.content ?: "",
                        modifier = Modifier
                            .padding(8.dp),
                        style = typography.subtitle2
                    )
                    Spacer(modifier = Modifier.height(20.dp))
//                    SimilarMoviesSection(movie, viewModel)
                    Spacer(modifier = Modifier.height(50.dp))
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
fun PreviewRouteBBSDetail(){
    RouteBBSDetail(1)
}
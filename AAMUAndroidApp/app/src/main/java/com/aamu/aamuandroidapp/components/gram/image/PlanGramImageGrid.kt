package com.aamu.aamuandroidapp.components.gram.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.aamuplan.PlaceDetail.PlaceDetailViewModel
import com.aamu.aamuandroidapp.components.aamuplan.PlaceDetail.PlaceDetailViewModelFactory
import com.aamu.aamuandroidapp.components.gram.posts.PostList
import com.aamu.aamuandroidapp.ui.theme.cyan200

@Composable
fun PlanGramImageGrid(contentid: Int,size : Dp){

    val viewModel : PlanGramImageGridViewModel = viewModel(
        factory = PlanGramImageGridViewModelFactory()
    )

    viewModel.getGramPhotoList(contentid)

    val contentidByGarmList by viewModel.contentidByGarmList.observeAsState(emptyList())
    val error by viewModel.errorLiveData.observeAsState()

    Box(modifier = Modifier.fillMaxSize()){
        if (contentidByGarmList.isNotEmpty()) {
            LazyHorizontalGrid(rows = GridCells.Adaptive(minSize = size)){
                items(contentidByGarmList!!){gramitem->
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(gramitem ?: R.drawable.no_image)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.size(size)
                    )
                }
            }
        } else {
            if (error.isNullOrEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(24.dp),
                    color = cyan200
                )
            } else {
                Text(
                    text = error ?: "리뷰가 없습니다",
                    modifier = Modifier
                )
            }
        }

    }
}
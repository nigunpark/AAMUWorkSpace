package com.aamu.aamuandroidapp.components.routebbs

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aamu.aamuandroidapp.util.carousel.Pager
import com.aamu.aamuandroidapp.util.carousel.PagerState
import kotlin.math.abs

@Composable
fun RoutePager(
    imageId: MutableState<Int>,
) {
    val viewModel: RouteViewModel = viewModel(
        factory = RouteViewModelFactory(LocalContext.current)
    )
    val bbsList by viewModel.aamuBBSList.observeAsState(emptyList())
    val error by viewModel.errorLiveData.observeAsState()

    if (bbsList.isNotEmpty()) {
        val pagerState = remember { PagerState(maxPage = bbsList.size - 1) }

        Pager(state = pagerState, modifier = Modifier.height(645.dp), orientation = Orientation.Horizontal ,offscreenLimit = 2) {
            val bbs = bbsList[commingPage]
//            imageId.value = imageIds[pagerState.currentPage]
            val isSelected = pagerState.currentPage == commingPage

            // Only one page before and one page after the selected page needs to receive non zero offset
            val filteredOffset = if (abs(pagerState.currentPage - commingPage) < 2) {
                currentPageOffset
            } else 0f

            RoutePagerItem(
                bbs,
                isSelected,
                filteredOffset,
            )
//                { moviesHomeInteractionEvents(MoviesHomeInteractionEvents.AddToMyWatchlist(movie)) }
//            ) {
//                moviesHomeInteractionEvents(
//                    MoviesHomeInteractionEvents.OpenMovieDetail(movie, imageId.value)
//                )
//            }
        }
    } else {
        if (error.isNullOrEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.padding(24.dp)
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
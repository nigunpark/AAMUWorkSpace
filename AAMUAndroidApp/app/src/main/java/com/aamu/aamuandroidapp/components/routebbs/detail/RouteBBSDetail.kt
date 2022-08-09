package com.aamu.aamuandroidapp.components.routebbs.detail

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModelFactory
import com.aamu.aamuandroidapp.components.routebbs.InterestTag
import com.aamu.aamuandroidapp.data.api.response.Review
import com.aamu.aamuandroidapp.ui.theme.aamublue
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.aamu.aamuandroidapp.ui.theme.modifiers.verticalGradientBackground
import com.aamu.aamuandroidapp.ui.theme.typography
import com.aamu.aamuandroidapp.ui.theme.yellow
import com.aamu.aamuandroidapp.util.contextL
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RouteBBSDetail(
    rbn : Int
) {
    val expand = remember { mutableStateOf(false) }
    val viewModel: RouteBBSDetailViewModel = viewModel(
        factory = RouteBBSDetailViewModelFactory(LocalContext.current)
    )
    viewModel.getBBSListOne(rbn)

    val bbsDetail by viewModel.bbsDetail.observeAsState()
    val error by viewModel.errorLiveData.observeAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()){
        if (bbsDetail?.rbn != 0 && bbsDetail?.rbn != null) {
            val gradient = Brush.verticalGradient(
                colors = listOf(Color.White, Color.Transparent),
                startY = maxHeight.value/3,  // 1/3
                endY = maxHeight.value
            )
            val imgLoader = ImageLoader(LocalContext.current).newBuilder().components {
                if (Build.VERSION.SDK_INT >= 28){
                    add(ImageDecoderDecoder.Factory())
                }
                else{
                    add(GifDecoder.Factory())
                }
            }.build()
            Box{
                Image(painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.deepdarkocean)
                        .crossfade(true)
                        .build(),
                    imageLoader = imgLoader,
                    placeholder = null,
                    contentScale = ContentScale.FillHeight
                ),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                    , contentScale = ContentScale.FillHeight)
                Box(modifier = Modifier.matchParentSize().background(gradient))
            }
            LazyColumn(
                modifier = Modifier
                    .padding(
                        animateDpAsState(
                            if (expand.value) 1.dp else 120.dp,
                            tween(350)
                        ).value
                    )
            ) {
                item {
                    if(bbsDetail!!.photo != null) {
                        val pagerState = rememberPagerState(
                            pageCount = bbsDetail!!.photo?.size ?: 0,
                            initialOffscreenLimit = 2,
                            infiniteLoop = false,
                            initialPage = 0
                        )
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(bbsDetail!!.photo?.getOrNull(0) ?: R.drawable.no_image)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop
                        )
                        HorizontalPager(state = pagerState) { index ->
                            val painterOther = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        bbsDetail!!.photo?.getOrNull(index) ?: R.drawable.no_image
                                    )
                                    .crossfade(true)
                                    .build(),
                                contentScale = ContentScale.Crop
                            )
                            Image(
                                painter = if (index == 0) painter else painterOther,
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(
                                        600.dp
                                    )
                                    .fillMaxWidth(),
                            )
                        }
                        when (painter.state) {
                            is AsyncImagePainter.State.Success -> expand.value = true
                            else -> expand.value = false
                        }
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
                        PlannerDetail(viewModel)
                    }
                }
                if (bbsDetail!!.reviewList != null && bbsDetail!!.reviewList?.size != 0) {
                    items(items = bbsDetail!!.reviewList!!) { review ->
                        Box(Modifier.padding(5.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color.White)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .padding(5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    FaIcon(
                                        faIcon = FaIcons.Star,
                                        size = 25.dp,
                                        tint = yellow,
                                        modifier = Modifier.fillMaxWidth(0.1f)
                                    )
                                    Text(
                                        text = "${review.rate}점",
                                        modifier = Modifier.fillMaxWidth(0.1f)
                                    )
                                    Text(
                                        text = review.id ?: "",
                                        modifier = Modifier.fillMaxWidth(0.3f)
                                    )
                                    Text(text = review.review ?: "", maxLines = 1)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                } else {
                    item {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.White)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "아직 리뷰가 없어요!!",
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colors.secondary
                            )
                        }
                    }
                }
                item{Spacer(modifier = Modifier.height(130.dp))}

            }

        } else {
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
        var isReviewUser : Boolean by remember { mutableStateOf(false) }

        val preferences : SharedPreferences = LocalContext.current.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
        val userid : String? = preferences.getString("id",null)

        if (bbsDetail?.rbn != null && bbsDetail!!.reviewList != null && bbsDetail!!.reviewList?.size != 0) {
            for (review in bbsDetail!!.reviewList!!) {
                if (review.id == userid) {
                    isReviewUser = true
                    break
                }
            }
        }
        if(isReviewUser.not()) {
            var rating: Float by remember { mutableStateOf(0.0F) }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                Column() {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                            .background(Color.White)
                            .padding(15.dp)
                    ) {
                        RatingBar(
                            value = rating,
                            onValueChange = { rating = it },
                            onRatingChanged = {},
                            config = RatingBarConfig().style(RatingBarStyle.HighLighted)
                                .stepSize(StepSize.HALF)
                        )
                    }
                    RouteBBSUserInput(onMessageSent = { content ->
                        viewModel.postReview(
                            rbn = bbsDetail!!.rbn!!,
                            rating = rating,
                            reviewContent = content
                        )
                    })
                }
            }
        }
        else{
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
                .background(Color.White), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "리뷰를 작성한 사용자는 또 리뷰를 작성할 수 없어요", color = Color.LightGray)
            }
        }
    }
}

@Composable
fun PlannerDetail(
    viewModel: RouteBBSDetailViewModel
){
    val plannerDetail by viewModel.plannerDetail.observeAsState()
    val error by viewModel.errorplannerLiveData.observeAsState()

    if(plannerDetail?.rbn != null){
        LazyColumn(modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(aamublue)){
            for(key in plannerDetail!!.routeMap?.keys!!){

                val dayday = (((key?.replace("day",""))?.toInt()?.minus(1))?.times(1000*60*60*24) ?: 0)

                val formatter = SimpleDateFormat("MM월 dd일 E")
                val time = formatter.format(plannerDetail!!.routeDate?.plus(dayday) ?: 0)
                item { 
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(5.dp)
                        .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(5.dp))) {
                        Column(modifier = Modifier
                            .padding(1.dp)
                            .background(Color.White)
                            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Row(modifier = Modifier.height(30.dp)) {
                                Text(text = "${key?.replace("day","")}일차 : ", fontWeight = FontWeight.Bold)
                                Text("${time}", color = Color.Black.copy(alpha = 0.5f))
                            }
                            
                            Box(modifier = Modifier
                                .background(aamublue)
                                .padding(5.dp)
                                .fillMaxWidth()) {
                                LazyRow() {
                                    items(plannerDetail!!.routeMap?.get(key)!!) { place ->
                                        Box(modifier = Modifier.clip(RoundedCornerShape(5.dp))) {
                                            Column(
                                                modifier = Modifier.background(Color.White),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Image(
                                                    painter = rememberAsyncImagePainter(
                                                        model = ImageRequest.Builder(LocalContext.current)
                                                            .data(
                                                                place?.dto?.smallimage
                                                                    ?: R.drawable.no_image
                                                            )
                                                            .crossfade(true)
                                                            .build(),
                                                        contentScale = ContentScale.Crop
                                                    ),
                                                    contentDescription = null,
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .size(130.dp)
                                                        .shadow(1.dp)
                                                )
                                                Column(
                                                    modifier = Modifier
                                                        .padding(horizontal = 4.dp)
                                                ) {
                                                    Text(
                                                        text = place?.dto?.title ?: "",
                                                        style = typography.h6.copy(fontSize = 12.sp),
                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(5.dp))
                                    }
                                }
                            }
                        }
                    }
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
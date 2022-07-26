package com.aamu.aamuandroidapp.components.aamuplan.PlanDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.ui.theme.orange200
import com.aamu.aamuandroidapp.ui.theme.typography
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun PlanBottomViewPager(mapviewModel: AAMUPlanViewModel,
                        coroutineScope: CoroutineScope,
                        topbarhide : MutableState<Boolean>,
                        bottomSheetScaffoldState: BottomSheetScaffoldState,){
    val pagerState = rememberPagerState(pageCount = 2
        , initialOffscreenLimit = 2
        , infiniteLoop = false
        , initialPage = 0)
    val tabIndex = pagerState.currentPage
    Column() {
        TabRow(selectedTabIndex = tabIndex
            , contentColor = orange200
            , backgroundColor = Color.Transparent
            , indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(it[tabIndex]),
                    color = Color(0xFFDE5D83),
                    height = TabRowDefaults.IndicatorHeight * 1.5F
                )
            }) {
            Tab(selected = tabIndex == 0, onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            }, text = { Text(text = "내 일정", fontWeight = FontWeight.Bold) })
            Tab(selected = tabIndex == 1, onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            }, text = { Text(text = "공유된 일정", fontWeight = FontWeight.Bold)})
        }
        HorizontalPager(state = pagerState) {index ->
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                if (index == 0) {
                    PlannerRoute(mapviewModel,coroutineScope,topbarhide,bottomSheetScaffoldState)
                } else {
                    PlannerShareRoute()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlannerRoute(mapviewModel: AAMUPlanViewModel,
                 coroutineScope: CoroutineScope,
                 topbarhide : MutableState<Boolean>,
                 bottomSheetScaffoldState: BottomSheetScaffoldState,){
    val plannerSelectList by mapviewModel.plannerSelectList.observeAsState(emptyList())
    val listState = rememberLazyListState()
    LazyColumn(state = listState){
        itemsIndexed(items = plannerSelectList,
        itemContent = { index, planner->
            Surface(
                modifier = Modifier.clickable {
                    mapviewModel.getPlannerSelectOne(planner.rbn!!)
                    topbarhide.value = true
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            ){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(planner.smallImage ?: R.drawable.no_image)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                            .shadow(1.dp)
                    )
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = planner.title ?: "",
                            style = typography.h6.copy(fontSize = 16.sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = planner.plannerdate ?: "",
                            style = typography.subtitle2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Divider(modifier = Modifier.alpha(0.1f))
            }
        })
    }
}

@Composable
fun PlannerShareRoute(){
//    LazyColumn(content = ){}
}

@Preview
@Composable
fun PreViewPlanBottomViewPager() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .background(Color.White)) {
//        PlanBottomViewPager(rememberCoroutineScope())
    }
}
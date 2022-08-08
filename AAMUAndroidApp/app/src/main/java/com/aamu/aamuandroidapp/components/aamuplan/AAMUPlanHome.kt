package com.aamu.aamuandroidapp.components.aamuplan

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Shapes
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.aamuplan.PlaceDetail.PlaceDetail
import com.aamu.aamuandroidapp.components.aamuplan.PlanDetails.PlanBottomViewPager
import com.aamu.aamuandroidapp.components.aamuplan.PlanDetails.PlanDetails
import com.aamu.aamuandroidapp.components.aamuplan.PlanDetails.SideContent
import com.aamu.aamuandroidapp.components.aamuplan.PlanItems.PlanMove
import com.aamu.aamuandroidapp.ui.theme.amber200
import com.aamu.aamuandroidapp.ui.theme.amber700
import com.aamu.aamuandroidapp.ui.theme.orange700
import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AAMUPlanHome(navController : NavController,mapviewModel: AAMUPlanViewModel){
    val context : Context = LocalContext.current
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    var topbarhide = remember { mutableStateOf(false) }
    var startMove = remember { mutableStateOf(false) }

    val isPlaceDetail by mapviewModel.isPlaceDetail.observeAsState()

    Box(modifier = Modifier.fillMaxSize()){
        BottomSheetScaffold(
            sheetElevation = 0.dp,
            sheetBackgroundColor = Color.Transparent,
            content = {
                Box(contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        KakaoMap(mapviewModel = mapviewModel)
                    }
                    if(!topbarhide.value && isPlaceDetail == false) {
                    ActionButton(mapviewModel,
                        Modifier
                            .statusBarsPadding()
                            .align(Alignment.TopEnd))
                    }
                }
            },
            sheetContent = {
                if(isPlaceDetail==true)
                    PlaceDetail(mapviewModel,coroutineScope,isPlaceDetail,topbarhide,startMove,bottomSheetScaffoldState)
                else
                    PlanBottomSheet(mapviewModel,coroutineScope,topbarhide,bottomSheetScaffoldState)
            },
            drawerGesturesEnabled = bottomSheetScaffoldState.drawerState.isOpen,
            drawerContent = {
                Spacer(modifier = Modifier.height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()+56.dp))
                SideContent(mapviewModel) },
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = if(topbarhide.value) 0.dp else if(isPlaceDetail==true) 900.dp else 40.dp,

        )
        val modifierBottom = Modifier.align(Alignment.BottomCenter)
        val modifierBottomStart = Modifier.align(Alignment.BottomStart)
        PlanDetails(context,mapviewModel,modifierBottom,modifierBottomStart,bottomSheetScaffoldState,topbarhide,startMove,coroutineScope)
    }

}




@Composable
fun KakaoMap(
    mapviewModel: AAMUPlanViewModel
) {
    AndroidView({ mapviewModel.mapView })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanBottomSheet(mapviewModel: AAMUPlanViewModel,
                    coroutineScope: CoroutineScope,
                    topbarhide : MutableState<Boolean>,
                    bottomSheetScaffoldState: BottomSheetScaffoldState,){
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .background(Color.White)
    ) {
        Divider(
            color = Color.Gray,
            thickness = 5.dp,
            modifier = Modifier
                .padding(top = 15.dp)
                .align(Alignment.TopCenter)
                .width(80.dp)
                .clip(RoundedCornerShape(50.dp))
        )
        Box(
            Modifier
                .padding(top = 40.dp)
                .fillMaxHeight(0.4f))
        {
            PlanBottomViewPager(mapviewModel,coroutineScope,topbarhide,bottomSheetScaffoldState)
        }
    }
}

val otherMapPlace = listOf(
    R.drawable.destination_icon,R.drawable.restaurant_icon,R.drawable.parking_icon,R.drawable.hospital_icon,R.drawable.drug_hospital_icon
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActionButton(
    mapviewModel : AAMUPlanViewModel,
    modifier: Modifier
){
    var visible by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        FloatingActionButton(
            onClick = {
                visible = !visible
            },
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp)
                .offset(x = (-20).dp, y = 5.dp)
                .align(Alignment.TopEnd),
            backgroundColor = Color.White,
            shape = CircleShape
        ) {
            Row() {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            }
        }
        val statusBars = WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.toInt()
        Column(
            modifier = Modifier.offset(x = (-20).dp, y = 55.dp),
            horizontalAlignment = Alignment.End
        ) {
            otherMapPlace.forEachIndexed { index, otheritem ->
                Spacer(modifier = Modifier.height(5.dp))
                AnimatedVisibility(
                    visible,
                    enter = slideIn(tween(100, easing = LinearOutSlowInEasing)) { fullSize ->
                        IntOffset(0, -fullSize.height)
                    },
                    exit = slideOut(tween(100, easing = LinearOutSlowInEasing)) {fullSize->
                        IntOffset(0, -fullSize.height)
                    },
                ) {
                    Row(
                        Modifier.height(50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(5.dp))
                        FloatingActionButton(
                            onClick = {
                                when (index) {
                                    0->mapviewModel.getRcentPlaces()
                                    1->mapviewModel.getRecentDiner("FD6")
                                    2->mapviewModel.getRecentDiner("PK6")
                                    3->mapviewModel.getRecentDiner("HP8")
                                    4->mapviewModel.getRecentDiner("PM9")
                                    else->{}
                                }

                            },
                            modifier = Modifier
                                .size(width = 50.dp, height = 50.dp),
                            backgroundColor = Color.White,
                            shape = CircleShape
                        ) {
                            Row() {
                                androidx.compose.material3.Icon(
                                    painter = painterResource(id = otheritem),
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAAMUPlanHome() {
//    AAMUPlanHome()
}




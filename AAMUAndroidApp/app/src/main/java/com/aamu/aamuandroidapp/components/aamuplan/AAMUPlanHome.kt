package com.aamu.aamuandroidapp.components.aamuplan

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aamu.aamuandroidapp.components.aamuplan.PlanDetails.PlanBottomViewPager
import com.aamu.aamuandroidapp.components.aamuplan.PlanDetails.PlanDetails
import com.aamu.aamuandroidapp.components.aamuplan.PlanDetails.SideContent
import com.aamu.aamuandroidapp.components.aamuplan.PlanItems.PlanMove
import com.aamu.aamuandroidapp.ui.theme.orange700
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AAMUPlanHome(){
    val context : Context = LocalContext.current
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    val mapviewModel : AAMUPlanViewModel = viewModel(
        factory = AAMUPlanViewModelFactory(LocalContext.current)
    )
    var topbarhide = remember { mutableStateOf(false) }
    var startMove = remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()){
        BottomSheetScaffold(
            sheetElevation = 0.dp,
            sheetBackgroundColor = Color.Transparent,
            content = {
                Box(contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        KakaoMap(mapviewModel = mapviewModel)
                    }
                    if(!topbarhide.value) {
                    ActionButton(mapviewModel,
                        Modifier
                            .statusBarsPadding()
                            .align(Alignment.TopEnd),
                        topbarhide,
                        bottomSheetScaffoldState,
                        coroutineScope)
                    }
                }
            },
            sheetContent = {
                PlanBottomSheet(mapviewModel,coroutineScope,topbarhide,bottomSheetScaffoldState)
            },
            drawerGesturesEnabled = bottomSheetScaffoldState.drawerState.isOpen,
            drawerContent = {
                Spacer(modifier = Modifier.height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding()+56.dp))
                SideContent(mapviewModel) },
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = if(topbarhide.value) 0.dp else 40.dp,

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActionButton(
    mapviewModel : AAMUPlanViewModel,
    modifier: Modifier,
    topbarhide : MutableState<Boolean>,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope
){
    Box(modifier = modifier) {
        FloatingActionButton(
            onClick = {
                mapviewModel.getRcentPlaces()
            },
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp)
                .offset(x = (-20).dp, y = 5.dp),
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
    }
}

@Preview
@Composable
fun PreviewAAMUPlanHome() {
    AAMUPlanHome()
}




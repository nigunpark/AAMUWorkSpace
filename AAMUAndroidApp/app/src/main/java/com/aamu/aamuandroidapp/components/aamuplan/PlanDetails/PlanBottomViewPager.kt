package com.aamu.aamuandroidapp.components.aamuplan.PlanDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aamu.aamuandroidapp.ui.theme.orange200
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PlanBottomViewPager(/*mapviewModel: AAMUPlanViewModel*/){
    val pagerState = rememberPagerState(pageCount = 2
        , initialOffscreenLimit = 2
        , infiniteLoop = false
        , initialPage = 0)
    val tabIndex = pagerState.currentPage

    TabRow(selectedTabIndex = tabIndex, contentColor = orange200) {
        Tab(selected = tabIndex == 0, onClick = {

        }, text = { Text(text = "내 일정", fontWeight = FontWeight.Bold)})
        Tab(selected = tabIndex == 0, onClick = {

        }, text = { Text(text = "공유된 일정", fontWeight = FontWeight.Bold)})
    }
}

@Preview
@Composable
fun PreViewPlanBottomViewPager() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .background(Color.White)) {
        PlanBottomViewPager()
    }
}
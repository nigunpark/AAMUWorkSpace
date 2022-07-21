package com.aamu.aamuandroidapp.components.aamuplan.PlanDetails

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.ui.theme.amber200
import com.aamu.aamuandroidapp.ui.theme.amber500
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanDetails(
//    mapviewModel : AAMUPlanViewModel,
    scaffoldState : BottomSheetScaffoldState,
    topbarhide : MutableState<Boolean>,
    coroutineScope: CoroutineScope
){
    PlanListScroll(scaffoldState,coroutineScope)
    TopAppBar() {
        Column(Modifier.statusBarsPadding()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    coroutineScope.launch { scaffoldState.drawerState.close() }
                    topbarhide.value = false
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
                Text(text = "ㅎㅇㅎ")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanListScroll(
    scaffoldState : BottomSheetScaffoldState,
    coroutineScope: CoroutineScope
){
    val lists : MutableList<Int> = arrayListOf()
    for(i : Int in 1..50)
        lists.add(i)
    val lazyListState = rememberLazyListState()
    val lazyListState2 = rememberLazyListState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                coroutineScope.launch {
                    if(lazyListState.firstVisibleItemIndex <= 0){
                        lazyListState.animateScrollToItem(0)
                        lazyListState2.animateScrollToItem(0)
                    }
                    else {
                        lazyListState.animateScrollToItem(lazyListState.firstVisibleItemIndex)
                        lazyListState2.animateScrollToItem(lazyListState.firstVisibleItemIndex)
                    }
                }
                return super.onPreScroll(available, source)
            }
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                coroutineScope.launch {
                    lazyListState2.scrollToItem(lazyListState.firstVisibleItemIndex,lazyListState.firstVisibleItemScrollOffset)
                    Log.i("com.aamu.aamu","ScrollOffset"+lazyListState.firstVisibleItemScrollOffset.toString())
                    Log.i("com.aamu.aamu","ItemIndex"+lazyListState.firstVisibleItemIndex.toString())
                }
                return super.onPostScroll(consumed, available, source)
            }
        }
    }
    if(scaffoldState.drawerState.isClosed) {
        BoxWithConstraints {
            LazyColumn(
                modifier = Modifier
                    .width(90.dp)
                    .background(color = amber200)
                    .padding(top = 112.dp)
                    .nestedScroll(nestedScrollConnection),
                state = lazyListState
            ) {

                itemsIndexed(items = lists,
                    itemContent = { index, list ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(text = "Item : " + list.toString())
                        }
                    }
                )
                item {
                    Spacer(modifier = Modifier.padding(top = maxHeight-50.dp))
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 56.dp)
                .height(56.dp)
                .background(amber500)
                .nestedScroll(nestedScrollConnection),
            state = lazyListState2
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Item : " + 0)
                }
            }
            itemsIndexed(items = lists,
                itemContent = { index, list ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Item : " + list.toString())
                    }
                }
            )
        }
    }
}
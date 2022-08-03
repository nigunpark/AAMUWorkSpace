package com.aamu.aamuandroidapp.components.routebbs.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModelFactory

@Composable
fun RouteBBSDetail(
    rbn : Int
){
    val viewModel : RouteBBSDetailViewModel = viewModel(
        factory = RouteBBSDetailViewModelFactory(LocalContext.current,rbn)
    )
}

@Preview
@Composable
fun PreviewRouteBBSDetail(){
    RouteBBSDetail(1)
}
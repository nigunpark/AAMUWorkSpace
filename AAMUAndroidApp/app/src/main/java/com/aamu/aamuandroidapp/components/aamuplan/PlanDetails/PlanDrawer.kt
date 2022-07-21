package com.aamu.aamuandroidapp.components.aamuplan.PlanDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SideContent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement
            .SpaceBetween
    ) {
        Text(text = "Item 1")
        Icon(imageVector = Icons.Default.List, contentDescription = "List")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement
            .SpaceBetween
    ) {
        Text(text = "Item 2")
        Icon(imageVector = Icons.Default.List, contentDescription = "List")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement
            .SpaceBetween
    ) {
        Text(text = "Item 3")
        Icon(imageVector = Icons.Default.List, contentDescription = "List")
    }
}
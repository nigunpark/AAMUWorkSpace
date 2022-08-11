package com.aamu.aamuandroidapp.components.profile

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aamu.aamuandroidapp.components.routebbs.InterestTag
import com.aamu.aamuandroidapp.data.DemoDataProvider.item
import com.aamu.aamuandroidapp.data.api.response.AAMUUserInfo
import com.aamu.aamuandroidapp.ui.theme.aamuorange
import com.aamu.aamuandroidapp.ui.theme.typography
import com.aamu.aamuandroidapp.util.StaggerdGridColumn

@Composable
fun InterestsSection(userinfo: AAMUUserInfo) {
    Text(
        text = "관심있는 테마",
        style = typography.h6,
        color = aamuorange,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
    Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2),
//        modifier = Modifier.height(50.dp)
//    ) {
//        items(userinfo.theme!!) { item->
//            InterestTag(item)
//        }
//    }
    Row(modifier = Modifier.padding(start = 8.dp, top = 8.dp).height(100.dp)) {
        StaggerdGridColumn(
            columns = 3
        ) {
            userinfo.theme?.forEach {
                InterestTag(it)
            }

        }
    }
}

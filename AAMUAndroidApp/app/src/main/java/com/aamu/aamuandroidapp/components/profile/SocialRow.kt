package com.aamu.aamuandroidapp.components.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.data.api.response.AAMUGarmResponse
import com.aamu.aamuandroidapp.ui.theme.*
import com.aamu.aamuandroidapp.ui.theme.components.Material3Card

@Composable
fun SocialRow(userGram: List<AAMUGarmResponse>?) {
    Material3Card(elevation = 8.dp, modifier = Modifier.padding(8.dp), backgroundColor = amber200) {
        val context = LocalContext.current
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text( "${userGram?.size}", style = typography.h6, color = orange )
                Text( "글  수", style = typography.h6, color = orange )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text( "${userGram?.get(0)?.followercount}", style = typography.h6, color = orange )
                Text( "팔로워", style = typography.h6, color = orange )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text( "${userGram?.get(0)?.followingcount}", style = typography.h6, color = orange )
                Text( "팔로잉", style = typography.h6, color = orange )
            }
        }
    }
}

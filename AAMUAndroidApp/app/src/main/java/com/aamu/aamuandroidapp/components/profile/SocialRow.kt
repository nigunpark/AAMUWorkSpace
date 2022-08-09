package com.aamu.aamuandroidapp.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.ui.theme.aamuorange
import com.aamu.aamuandroidapp.ui.theme.aamuorange200
import com.aamu.aamuandroidapp.ui.theme.amber200
import com.aamu.aamuandroidapp.ui.theme.components.Material3Card

@Composable
fun SocialRow() {
    Material3Card(elevation = 8.dp, modifier = Modifier.padding(8.dp), backgroundColor = amber200) {
        val context = LocalContext.current
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            IconButton(onClick = { launchSocialActivity(context, "github") }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_github_square_brands),
                    contentDescription = null,
                    tint = aamuorange
                )
            }
            IconButton(onClick = { launchSocialActivity(context, "twitter") }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_twitter_square_brands),
                    contentDescription = null,
                    tint = aamuorange
                )
            }
            IconButton(onClick = { launchSocialActivity(context, "linkedin") }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_linkedin_brands),
                    contentDescription = null,
                    tint = aamuorange
                )
            }
        }
    }
}

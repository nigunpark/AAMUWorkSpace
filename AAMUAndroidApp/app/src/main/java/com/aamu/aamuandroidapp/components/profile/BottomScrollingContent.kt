package com.aamu.aamuandroidapp.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.ui.theme.aamuorange
import com.aamu.aamuandroidapp.ui.theme.typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomScrollingContent() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp)
    ) {
        SocialRow()
        Text(
            text = "About Me",
            style = typography.h6,
            color = aamuorange,
            modifier = Modifier.padding(start = 8.dp, top = 12.dp)
        )
        Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        Text(
            text = "isme",
            style = typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        InterestsSection()
        MyPhotosSection()
        Text(
            text = "About Project",
            style = typography.h6,
            color = aamuorange,
            modifier = Modifier.padding(start = 8.dp, top = 16.dp)
        )
        Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        Text(
            text = "aboutproject",
            style = typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        MoreInfoSection()
    }
}

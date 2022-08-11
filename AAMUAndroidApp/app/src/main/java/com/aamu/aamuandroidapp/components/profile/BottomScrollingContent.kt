package com.aamu.aamuandroidapp.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aamu.aamuandroidapp.data.api.response.AAMUGarmResponse
import com.aamu.aamuandroidapp.data.api.response.AAMUUserInfo
import com.aamu.aamuandroidapp.ui.theme.aamuorange
import com.aamu.aamuandroidapp.ui.theme.typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomScrollingContent(
    userinfo: AAMUUserInfo,
    userGram: List<AAMUGarmResponse>?,
    viewModel: ProfileScreenViewModel
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp)
    ) {
        SocialRow(userGram)
        Text(
            text = "자기소개",
            style = typography.h6,
            color = aamuorange,
            modifier = Modifier.padding(start = 8.dp, top = 12.dp)
        )
        Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        Row(Modifier.padding(start = 8.dp, top = 8.dp).height(100.dp)) {
            Text(
                text = "${userinfo.self}",
                style = typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }
        InterestsSection(userinfo)
        MyPhotosSection(viewModel)
//        Text(
//            text = "About Project",
//            style = typography.h6,
//            color = aamuorange,
//            modifier = Modifier.padding(start = 8.dp, top = 16.dp)
//        )
//        Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
//        Text(
//            text = "aboutproject",
//            style = typography.body1,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//        )
        MoreInfoSection(viewModel)
    }
}

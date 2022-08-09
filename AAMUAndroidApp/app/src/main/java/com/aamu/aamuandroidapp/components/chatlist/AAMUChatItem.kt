package com.aamu.aamuandroidapp.components.chatlist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.data.api.response.AAMUChatRoomResponse
import com.aamu.aamuandroidapp.data.model.Album
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.aamu.aamuandroidapp.ui.theme.purple
import com.aamu.aamuandroidapp.ui.theme.typography
import com.aamu.aamuandroidapp.util.displayedAt
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@Composable
fun AAMUChatItem(item: AAMUChatRoomResponse,userid : String,onClick : (Int,String) -> Unit) {

    val otheruser = AAMUChatRoomResponse()
    otheruser.roomno = item.roomno
    otheruser.senddate = item.senddate
    otheruser.lastmessage = item.lastmessage
    if (item.fromid == userid) {
        otheruser.fromid = item.toid
        otheruser.frompro = item.topro
    } else {
        otheruser.fromid = item.fromid
        otheruser.frompro = item.frompro
    }

    Card(
        modifier = Modifier
            .padding(6.dp)
            .clickable(onClick = {
                onClick.invoke(otheruser.roomno?:0,otheruser.fromid ?: "")
            }), elevation = 2.dp
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Surface(elevation = 5.dp, shape = CircleShape) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(otheruser.frompro ?: R.drawable.no_image)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(50.dp).clip(CircleShape)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = otheruser.fromid ?: "",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = typography.h6.copy(fontSize = 14.sp)
                )
                Text(
                    text = otheruser.lastmessage ?: "",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = typography.subtitle2
                )
            }
            if (otheruser.senddate != 0L) {
                Text(
                    text = "${displayedAt(otheruser.senddate!!)}",
                    color = cyan200,
                    style = typography.body2,
                )
            }
        }
    }
}

package com.aamu.aamuandroidapp.components.aamuplan.PlanItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.ui.theme.components.Material3Card
import com.aamu.aamuandroidapp.ui.theme.cyan700

@Composable
fun PlanMove(mapviewModel : AAMUPlanViewModel){
    val place by mapviewModel.place.observeAsState()
    Material3Card(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Surface(elevation = 5.dp) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(place?.dto?.smallimage?:R.drawable.no_image)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .padding(5.dp),
                )
            }
            Column(modifier = Modifier.fillMaxWidth(0.7f).fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = place?.dto?.title!!, fontSize = 10.sp)
                Text(text = place?.mtime.toString(), fontSize = 30.sp, color = cyan700)
                Text(text = "설정한 이동시간 : "+place?.mtime.toString(), fontSize = 10.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { },modifier = Modifier.size(50.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.navigation_pin_icon),
                        contentDescription = "안내시작 버튼",
                        tint = Color.Unspecified
                    )
                }
                Text(text = "안내시작", fontSize = 9.sp, fontWeight = FontWeight.Bold , color = Color.Gray)
            }
        }
    }
}




@Preview
@Composable
fun PreviewPlanMove() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)) {
        PlanMove(AAMUPlanViewModel(LocalContext.current))
    }
}
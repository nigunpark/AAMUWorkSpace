package com.aamu.aamuandroidapp.components.routebbs

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.data.api.response.AAMUBBSResponse
import com.aamu.aamuandroidapp.ui.theme.aamuorange
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.aamu.aamuandroidapp.ui.theme.cyan700
import com.aamu.aamuandroidapp.ui.theme.typography
import java.text.SimpleDateFormat
import kotlin.math.abs
import kotlin.math.min

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutePagerItem(
    bbs: AAMUBBSResponse,
//    genres: List<Genre>,
    isSelected: Boolean,
    offset: Float,
//    addToWatchList: () -> Unit,
    openBBSDetail: () -> Unit
) {
    val animateHeight = getOffsetBasedValue(
        selectedValue = 645,
        nonSelectedValue = 360,
        isSelected = isSelected,
        offset = offset
    ).dp
    val animateWidth = getOffsetBasedValue(
        selectedValue = 340,
        nonSelectedValue = 320,
        isSelected = isSelected,
        offset = offset
    ).dp
    val animateElevation = getOffsetBasedValue(
        selectedValue = 12,
        nonSelectedValue = 2,
        isSelected = isSelected,
        offset = offset
    ).dp

    Card(
        elevation = animateDpAsState(animateElevation).value,
        modifier = Modifier
            .width(animateWidth)
            .height(animateHeight)
            .padding(24.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        contentColor = Color.Black,
        onClick = { openBBSDetail.invoke() },
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(bbs.photo?.getOrNull(0) ?: R.drawable.no_image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val clicked = remember { mutableStateOf(false) }
                Text(
                    text = bbs.title ?: "",
                    maxLines = 1,
                    modifier = Modifier.padding(8.dp),
                    style = typography.h6
                )
            }
            Row {
                InterestTag(text = bbs.themename ?: "")
            }
            val formatter = SimpleDateFormat("yyyy년 MM월 dd일")
            val time = formatter.format(bbs.postdate)
            Text(
                text = "${time} 포스팅 됨",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = typography.h6.copy(fontSize = 12.sp)
            )
            Text(
                text = "평점  •  ${bbs.rateavg}/5",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = typography.h6.copy(fontSize = 12.sp, fontWeight = FontWeight.Medium)
            )
            Text(
                text = bbs.content ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
                    .weight(1f),
                style = typography.subtitle2
            )
            Button(onClick = {}, modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = aamuorange, contentColor = Color.White)) {
                Text(text = "자세히 보기", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun getOffsetBasedValue(
    selectedValue: Int,
    nonSelectedValue: Int,
    isSelected: Boolean,
    offset: Float,
): Float {
    val actualOffset = if (isSelected) 1 - abs(offset) else abs(offset)
    val delta = abs(selectedValue - nonSelectedValue)
    val offsetBasedDelta = delta * actualOffset

    return min(selectedValue, nonSelectedValue) + offsetBasedDelta
}

@Stable
interface TagColors {
    @Composable
    fun backgroundColor(enabled: Boolean): State<Color>

    @Composable
    fun contentColor(enabled: Boolean): State<Color>
}

@Immutable
private class DefaultTagColors(
    private val backgroundColor: Color,
    private val contentColor: Color
) : TagColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(newValue = backgroundColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(newValue = contentColor)
    }
}

object TagDefaults {
    @Composable
    fun tagColors(
        backgroundColor: Color = cyan200.copy(alpha = 0.2f),
        contentColor: Color = cyan700
    ): TagColors = DefaultTagColors(backgroundColor = backgroundColor, contentColor = contentColor)
}

@Composable
fun InterestTag(
    text: String,
    modifier: Modifier = Modifier,
    colors: TagColors = TagDefaults.tagColors(),
    shape: Shape = RoundedCornerShape(4.dp),
    style: TextStyle = typography.body2.copy(fontWeight = FontWeight.Bold),
    onClick: () -> Unit = {}
) {
    val tagModifier = modifier
        .padding(4.dp)
        .clickable(onClick = onClick)
        .clip(shape = shape)
        .background(colors.backgroundColor(enabled = true).value)
        .padding(horizontal = 8.dp, vertical = 4.dp)
    Text(
        text = text,
        color = colors.contentColor(enabled = true).value,
        modifier = tagModifier,
        style = style
    )
}
package com.aamu.aamuandroidapp.util

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlin.math.max

@Composable
fun TextWithShadow(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color
) {
    Text(
        text = text,
        style = style,
        color = Color.DarkGray,
        modifier = modifier
            .offset(
                x = 5.dp,
                y = 5.dp
            )
            .alpha(0.3f)
    )
    Text(
        text = text,
        style = style,
        modifier = modifier,
        color = color
    )
}

@Composable
fun LottieLoadingView(
    context: Context,
    file: Int,
    modifier: Modifier = Modifier,
    iterations: Int = 10
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(file))
    LottieAnimation(
        composition,
        modifier = modifier.defaultMinSize(300.dp),
        iterations = iterations,
    )
}

@Composable
fun CustomChips(text: String, modifier: Modifier = Modifier){
    Surface(
        color = androidx.compose.material3.MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.05f),
        contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 12.dp,
            )
        )
    }
}

@Composable
fun StaggerdGridColumn(
    modifier: Modifier = Modifier,
    columns: Int = 3,
    content: @Composable () -> Unit,
) {
    Layout(content = content, modifier = modifier) { measurables, constraints ->
        val columnWidths = IntArray(columns) { 0 }
        val columnHeights = IntArray(columns) { 0 }

        val placables = measurables.mapIndexed { index, measurable ->
            val placable = measurable.measure(constraints)

            val col = index % columns
            columnHeights[col] += placable.height
            columnWidths[col] = max(columnWidths[col], placable.width)
            placable
        }

        val height = columnHeights.maxOrNull()
            ?.coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))
            ?: constraints.minHeight

        val width =
            columnWidths.sumOf { it }.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))

        val colX = IntArray(columns) { 0 }
        for (i in 1 until columns) {
            colX[i] = colX[i - 1] + columnWidths[i - 1]
        }

        layout(width, height) {
            val colY = IntArray(columns) { 0 }
            placables.forEachIndexed { index, placeable ->
                val col = index % columns
                placeable.placeRelative(
                    x = colX[col],
                    y = colY[col]
                )
                colY[col] += placeable.height
            }
        }
    }
}

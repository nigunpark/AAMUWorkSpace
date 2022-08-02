/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aamu.aamuandroidapp.components.chatlist.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetchatAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    title: @Composable () -> Unit
) {
    val backgroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val backgroundColor = backgroundColors.containerColor(
//        scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    0f
    ).value
    val foregroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent
    )
    Box(modifier = Modifier.background(backgroundColor)) {
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = title,
            scrollBehavior = scrollBehavior,
            colors = foregroundColors,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun JetchatAppBarPreview() {
    JetchatAppBar(title = { Text("Preview!") })
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun JetchatAppBarPreviewDark() {
    JetchatAppBar(title = { Text("Preview!") })
}

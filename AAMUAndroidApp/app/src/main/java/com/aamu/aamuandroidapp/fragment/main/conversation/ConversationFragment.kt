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

package com.aamu.aamuandroidapp.fragment.main.conversation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aamu.aamuandroidapp.components.chatlist.chat.ConversationContent
import com.aamu.aamuandroidapp.components.chatlist.chat.ConversationViewModel
import com.aamu.aamuandroidapp.components.chatlist.chat.ConversationViewModelFactory


class ConversationFragment : Fragment() {

    private lateinit var conversationViewModel: ConversationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.setDecorFitsSystemWindows(false)
            container?.rootView?.setOnApplyWindowInsetsListener { _, insets ->
                val imeHeight = insets.getInsets(android.view.WindowInsets.Type.ime()).bottom
                val bottomInset : Int = if (imeHeight == 0) 0 else imeHeight
                container?.rootView?.setPadding(0, 0, 0, bottomInset)
                insets
            }
        } else {
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        setContent {
            conversationViewModel = viewModel(
                factory = ConversationViewModelFactory("8")
            )
            ConversationContent(
                viewModel= conversationViewModel,
                // Add padding so that we are inset from any navigation bars
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets
                        .navigationBars
                        .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.setDecorFitsSystemWindows(true)
        } else {
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED or WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)
        }
        conversationViewModel.unSubscribe()
    }
}

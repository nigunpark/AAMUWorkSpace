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

package com.aamu.aamuandroidapp.components.gram.detail

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.aamu.aamuandroidapp.components.chatlist.chat.BackPressHandler
import com.aamu.aamuandroidapp.components.gram.profile.ProfilePicture
import com.aamu.aamuandroidapp.components.gram.profile.ProfileSizes
import com.aamu.aamuandroidapp.ui.theme.aamublue
import com.aamu.aamuandroidapp.ui.theme.cyan200
import com.aamu.aamuandroidapp.ui.theme.cyan700
import com.aamu.aamuandroidapp.util.contextL

enum class InputSelector {
    NONE,
}
@Preview
@Composable
fun GramInputPreview() {
    GramInput(onMessageSent = {})
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun GramInput(
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
    resetScroll: () -> Unit = {},
) {
    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    val dismissKeyboard = { currentInputSelector = InputSelector.NONE }

    // Intercept back navigation if there's a InputSelector visible
    if (currentInputSelector != InputSelector.NONE) {
        BackPressHandler(onBackPressed = dismissKeyboard)
    }

    var textState by remember { mutableStateOf(TextFieldValue()) }

    // Used to decide if the keyboard should be shown
    var textFieldFocusState by remember { mutableStateOf(false) }

    Surface(tonalElevation = 2.dp) {
        Column(modifier = modifier.background(Color.White)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                val preferences : SharedPreferences = LocalContext.current.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
                val profile : String? = preferences.getString("profile",null)
                Spacer(modifier = Modifier.width(12.dp))
                ProfilePicture(
                    imageId = profile,
                    contentDescription = null,
                    size = 40.dp
                )
                val keyboardController = LocalSoftwareKeyboardController.current
                UserInputText(
                    textFieldValue = textState,
                    onTextChanged = { textState = it },
                    // Only show the keyboard if there's no input selector and text field has focus
                    keyboardShown = currentInputSelector == InputSelector.NONE && textFieldFocusState,
                    // Close extended selector if text field receives focus
                    onTextFieldFocused = { focused ->
                        if (focused) {
                            currentInputSelector = InputSelector.NONE
                            resetScroll()
                        }
                        textFieldFocusState = focused
                    },
                    focusState = textFieldFocusState,
                    sendMessageEnabled = textState.text.isNotBlank(),
                    onMessageSent = {
                        keyboardController?.hide()
                        onMessageSent(textState.text)
                        // Reset text field and close keyboard
                        textState = TextFieldValue()
                        // Move scroll to bottom
                        resetScroll()
                        dismissKeyboard()
                    }
                )
            }

        }
    }
}

val KeyboardShownKey = SemanticsPropertyKey<Boolean>("KeyboardShownKey")
var SemanticsPropertyReceiver.keyboardShownProperty by KeyboardShownKey

@ExperimentalFoundationApi
@Composable
private fun UserInputText(
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (TextFieldValue) -> Unit,
    textFieldValue: TextFieldValue,
    keyboardShown: Boolean,
    onTextFieldFocused: (Boolean) -> Unit,
    focusState: Boolean,
    sendMessageEnabled: Boolean,
    onMessageSent: () -> Unit,
) {
    val a11ylabel = "Text input"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(10.dp)
            .semantics {
                contentDescription = a11ylabel
                keyboardShownProperty = keyboardShown
            }
            .background(Color.White)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(64),
                color = Color.Black.copy(alpha = 0.2f)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
                    .align(Alignment.Bottom)
                    .background(Color.White)
            ) {
                var lastFocusState by remember { mutableStateOf(false) }
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { onTextChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp)
                        .align(Alignment.CenterStart)
                        .onFocusChanged { state ->
                            if (lastFocusState != state.isFocused) {
                                onTextFieldFocused(state.isFocused)
                            }
                            lastFocusState = state.isFocused
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = ImeAction.Done
                    ),
                    maxLines = 1,
                    cursorBrush = SolidColor(LocalContentColor.current),
                    textStyle = LocalTextStyle.current.copy(
                        color = LocalContentColor.current,
                        textAlign = TextAlign.Start
                    ),
                    keyboardActions = KeyboardActions(onDone = { onMessageSent() })
                )

                val disableContentColor =
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                if (textFieldValue.text.isEmpty() && !focusState) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 32.dp),
                        text = "댓글을 달아주세요",
                        style = MaterialTheme.typography.bodyLarge.copy(color = disableContentColor)
                    )
                }
            }
        }

        val border = null
        val disabledContentColor = aamublue.copy(alpha = 0.3f)

        val buttonColors = ButtonDefaults.buttonColors(
            disabledContainerColor = Color.Transparent,
            disabledContentColor = disabledContentColor,
            containerColor = Color.Transparent,
            contentColor = aamublue
        )

        // Send button
        Button(
            modifier = Modifier
                .height(36.dp)
                .padding(end = 12.dp),
            enabled = sendMessageEnabled,
            onClick = onMessageSent,
            colors = buttonColors,
            border = border,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "게시",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

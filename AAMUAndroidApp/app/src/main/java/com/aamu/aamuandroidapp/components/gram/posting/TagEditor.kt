package com.aamu.aamuandroidapp.components.gram.posting

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aamu.aamuandroidapp.ui.theme.aamuorange
import com.google.accompanist.flowlayout.FlowRow
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KoohaHashTagEditor(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    focusedFlow: Boolean,
    textFieldInteraction: MutableInteractionSource,
    label: String?,
    placeholder: String,
    readOnly: Boolean = false,
    message: String? = null,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Default
    ),
    rowInteraction: MutableInteractionSource,
    listOfChips: List<String> = emptyList(),
    onChipClick: (Int) -> Unit
) {
    val isLight = MaterialTheme.colors.isLight

    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .wrapContentHeight()
            .padding(
                vertical = 5.dp,
                horizontal = 15.dp
            )
            .clickable(
                indication = null,
                interactionSource = rowInteraction,
                onClick = {
                    focusRequester.requestFocus()
                    keyboardManager?.show()
                }
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center
            ) {

                if (label != null) {
                    Text(
                        text = "$label:",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (focusedFlow) MaterialTheme.colors.secondary else if (isLight) Color.Gray else Color.White
                        )
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FaIcon(
                        faIcon = FaIcons.Hashtag,
                        tint = Color.Black.copy(alpha = 0.5f),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    TextFieldContent(
                        textFieldValue = textFieldValue,
                        placeholder = placeholder,
                        onValueChanged = onValueChanged,
                        focusRequester = focusRequester,
                        textFieldInteraction = textFieldInteraction,
                        readOnly = readOnly,
                        keyboardOptions = keyboardOptions,
                        focusManager = focusManager,
                        listOfChips = listOfChips,
                        modifier = modifier,
                        emphasizePlaceHolder = false,
                        onChipClick = onChipClick
                    )
                }

            }

            ErrorSection(
                message = message,
                errorMessage = errorMessage
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextFieldContent(
    textFieldValue: TextFieldValue,
    placeholder: String,
    onValueChanged: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    textFieldInteraction: MutableInteractionSource,
    readOnly: Boolean,
    keyboardOptions: KeyboardOptions,
    focusManager: FocusManager,
    listOfChips: List<String>,
    emphasizePlaceHolder: Boolean = false,
    modifier: Modifier,
    onChipClick: (Int) -> Unit
) {
    Box {
        val isFocused = textFieldInteraction.collectIsFocusedAsState()

        if (textFieldValue.text.isEmpty() && listOfChips.isEmpty()) {
            Text(
                text = placeholder,
                color = if (emphasizePlaceHolder && !isFocused.value) {
                    Color.Gray.copy(alpha = 0.8f)
                } else {
                    if (MaterialTheme.colors.isLight) {
                        Color.Gray.copy(alpha = 0.8f)
                    } else {
                        Color.Gray.copy(alpha = 0.8f)
                    }
                },
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
                    .padding(start = 3.dp),
                fontSize = 16.sp
            )
        }

        FlowRow(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            mainAxisSpacing = 5.dp,
        ) {
            repeat(times = listOfChips.size) { index ->
                Chip(
                    onClick = { onChipClick(index) },
                    modifier = Modifier.wrapContentWidth(),
                    leadingIcon = {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(3.dp)
                        ) {
                            Icon(
                                painter = rememberVectorPainter(image = Icons.Default.Close),
                                contentDescription = null,
                                modifier = Modifier.size(12.dp),
                                tint = Color.Black
                            )
                        }
                    },
                    colors = ChipDefaults
                        .chipColors(
                            backgroundColor = aamuorange,
                            contentColor = MaterialTheme.colors.onSecondary
                        )
                ) {
                    Text(text = listOfChips[index])
                }
            }

            BasicTextField(
                value = textFieldValue,
                onValueChange = onValueChanged,
                modifier = modifier
                    .focusRequester(focusRequester)
                    .width(IntrinsicSize.Min),
                singleLine = false,
                textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
                            .defaultMinSize(minHeight = 48.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier.wrapContentWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                modifier = Modifier
                                    .defaultMinSize(minWidth = 4.dp)
                                    .wrapContentWidth(),
                            ) {
                                innerTextField()
                            }
                        }
                    }
                },
                interactionSource = textFieldInteraction,
                cursorBrush = SolidColor(aamuorange),
                readOnly = readOnly,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ErrorSection(
    message: String?,
    errorMessage: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.End
        ) {

            if (message != null) {
                val color = if (MaterialTheme.colors.isLight) {
                    Color.Gray
                } else {
                    Color.White
                }
                Text(
                    text = message,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.body1.copy(color = color)
                )
            }
            if (errorMessage != null) {
                Chip(
                    onClick = {
                    },
                    colors = ChipDefaults
                        .chipColors(
                            backgroundColor = Color.Red,
                            contentColor = Color.White
                        ),
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Info),
                            contentDescription = null
                        )
                    }
                ) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }
        }
    }
}
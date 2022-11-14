package net.inferno.ygocardmaster.view

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    cursorBrush: Brush = SolidColor(LocalContentColor.current),
    textStyle: TextStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
    maxLines: Int = 1,
    singleLine: Boolean = true,
) {
    BasicTextField(
        value,
        onValueChange = onValueChange,
        decorationBox = {
            ProvideTextStyle(
                textStyle.copy(color = LocalContentColor.current.copy(alpha = 0.38f)),
            ) {
                if (value.isEmpty()) {
                    hint()
                }
            }

            it()
        },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        cursorBrush = cursorBrush,
        textStyle = textStyle,
        maxLines = maxLines,
        singleLine = singleLine,
        modifier = modifier,
    )
}
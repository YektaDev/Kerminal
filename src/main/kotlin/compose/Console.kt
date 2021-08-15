package compose

import ConsoleHandler.processInput
import ConsoleHandler.shouldNotChange
import ConsoleState
import State
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import appConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun Console(
    modifier: Modifier = Modifier,
    state: ConsoleState = remember { State.console },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    afterValueChange: () -> Unit = {},
) {
    val colors = with(appConfig.theme.color) {
        val front = Color(front)
        val back = Color(back)
        val primary = Color(primary)
        val error = Color(error)

        TextFieldDefaults.textFieldColors(
            textColor = front,
            disabledTextColor = front,
            cursorColor = primary,
            backgroundColor = back,
            placeholderColor = back,
            focusedIndicatorColor = back,
            disabledIndicatorColor = back,
            disabledLabelColor = back,
            disabledLeadingIconColor = back,
            disabledPlaceholderColor = back,
            disabledTrailingIconColor = back,
            errorIndicatorColor = back,
            errorLabelColor = back,
            errorLeadingIconColor = back,
            errorTrailingIconColor = back,
            focusedLabelColor = back,
            leadingIconColor = back,
            trailingIconColor = back,
            unfocusedIndicatorColor = back,
            unfocusedLabelColor = back,
            errorCursorColor = error,
        )
    }
    val style = TextStyle(
        color = Color(appConfig.theme.color.front),
        fontFamily = FontFamily.Monospace,
        fontStyle = FontStyle.Normal,
        fontSize = 18.sp,
        lineHeight = 2.5.em,
    )

    TextField(
        modifier = modifier.HandleKeyEvents(),
        value = state.textFieldValue,
        singleLine = false,
        maxLines = Int.MAX_VALUE,
        colors = colors,
        textStyle = style,
        shape = RectangleShape,
        enabled = true,
        readOnly = false,
        label = null,
        placeholder = null,
        leadingIcon = null,
        trailingIcon = null,
        isError = false,
        visualTransformation = {
            TransformedText(
                processInput(state.textFieldValue),
                OffsetMapping.Identity
            )
        },
        onValueChange = { value: TextFieldValue ->
            if (!shouldNotChange(value.text)) {
                val result = processInput(value).toString()
                state.setTextFieldValue(
                    TextFieldValue(result, TextRange(result.length))
                )
                afterValueChange()
            }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Send,
        ),
    )

    coroutineScope.launch {
        state.printMessageFlow().collect {
            state.setText(processInput(TextFieldValue(state.textFieldValue.text + it)).toString(), true)
        }
    }
}

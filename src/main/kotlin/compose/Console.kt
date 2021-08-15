package compose

import ConsoleHandler.processInput
import ConsoleState
import State
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import appConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun Console(modifier: Modifier, state: ConsoleState = remember { State.console }) {
    val coroutineScope = rememberCoroutineScope()

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
        modifier = modifier,
        value = state.text,
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
                processInput(state.text),
                OffsetMapping.Identity
            )
        },
        onValueChange = {
            state.setText(processInput(it).toString())
        },
    )

    coroutineScope.launch {
        state.printMessageFlow().collect {
            state.setText(processInput(state.text + it).toString())
        }
    }
}

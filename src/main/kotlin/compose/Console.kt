package compose

import ConsoleHandler.processInput
import ConsoleManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun Console() {
    val state = remember { ConsoleManager.state }

    TextField(
        modifier = Modifier.fillMaxSize(),
        value = state.text,
        singleLine = false,
        maxLines = Int.MAX_VALUE,
        textStyle = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            lineHeight = 2.5.em
        ),
        visualTransformation = {
            TransformedText(
                processInput(ConsoleManager.state.text),
                OffsetMapping.Identity
            )
        },
        onValueChange = {
            ConsoleManager.state.setText(processInput(it).toString())
        }
    )
}

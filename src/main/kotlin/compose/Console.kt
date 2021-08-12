package compose

import ConsoleHandler.processInput
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun Console() {
    var text by remember { mutableStateOf("") }

    TextField(
        modifier = Modifier.fillMaxSize(),
        value = text,
        singleLine = false,
        textStyle = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            lineHeight = 2.5.em
        ),
        visualTransformation = {
            TransformedText(
                processInput(text),
                OffsetMapping.Identity
            )
        },
        onValueChange = {
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append(it.split("ccc")[0])
                }

                withStyle(style = SpanStyle(color = Color.Red)) {
                    it.split("ccc").forEachIndexed { index, s ->
                        if (index != 0)
                            this.append(s)
                    }
                }
            }.toString()
        }
    )
}

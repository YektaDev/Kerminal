package page

import Resource.transparentBackColor
import State
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import appConfig
import compose.BitaTheme
import compose.Console
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun MainPage() = BitaTheme(appConfig.theme) {
    val coroutineScope = rememberCoroutineScope()
    val consoleState = remember { State.console }
    val verticalScrollState = rememberScrollState(0)
    val backgroundColor = Color(appConfig.theme.color.back)

    Row(
        modifier = Modifier.background(backgroundColor)
    ) {
        Console(
            modifier = Modifier
                .background(backgroundColor)
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(verticalScrollState),
            state = consoleState,
            coroutineScope = coroutineScope,
            afterValueChange = {
                coroutineScope.launch {
                    delay(1)
                    verticalScrollState.scrollTo(verticalScrollState.maxValue)
                }
            },
        )
        VerticalScrollbar(
            modifier = Modifier.padding(top = 32.dp).background(transparentBackColor).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(verticalScrollState),
            style = ScrollbarStyle(
                minimalHeight = 300.dp,
                thickness = 8.dp,
                shape = RoundedCornerShape(percent = 50),
                hoverDurationMillis = 250,
                unhoverColor = backgroundColor,
                hoverColor = Color(appConfig.theme.color.secondary)
            )
        )
    }
}

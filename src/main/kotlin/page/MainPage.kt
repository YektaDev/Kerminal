package page

import Theme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import appConfig
import compose.BitaTheme
import compose.Console

@Preview
@Composable
fun MainPage() = BitaTheme(appConfig.theme) {
    Surface(
        modifier = Modifier
            .background(Color(appConfig.theme.color.back))
            .fillMaxSize(),
        contentColor = Color(appConfig.theme.color.front)
    ) {
        Console()
    }
}
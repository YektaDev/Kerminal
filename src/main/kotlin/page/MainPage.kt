package page

import Theme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import compose.BitaTheme
import compose.Console

@Preview
@Composable
fun MainPage(themeData: Theme) = BitaTheme(themeData) {
    Surface(
        modifier = Modifier
            .background(Color(themeData.color.back))
            .fillMaxSize(),
        contentColor = Color(themeData.color.front)
    ) {
        Console()
    }
}
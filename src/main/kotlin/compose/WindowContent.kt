package compose

import Theme
import androidx.compose.runtime.Composable

@Composable
fun WindowContent(themeConfig: Theme?, content: @Composable () -> Unit) = if (themeConfig != null) {
    BitaTheme(themeConfig) {
        content()
    }
} else {
    content()
}
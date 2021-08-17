package compose

import Theme
import androidx.compose.runtime.Composable

@Composable
fun WindowContent(themeConfig: Theme?, content: @Composable () -> Unit) = if (themeConfig != null) {
    KonsoleTheme(themeConfig) {
        content()
    }
} else {
    content()
}

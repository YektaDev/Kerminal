package compose

import Theme
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun KonsoleTheme(themeConfig: Theme, content: @Composable () -> Unit) {
    DesktopMaterialTheme(
        colors = Colors(
            isLight = !themeConfig.isDark,

            primary = Color(themeConfig.color.primary),
            primaryVariant = Color(themeConfig.color.primary),
            secondary = Color(themeConfig.color.secondary),
            secondaryVariant = Color(themeConfig.color.secondary),

            onSurface = Color(themeConfig.color.front),
            onBackground = Color(themeConfig.color.front),
            onPrimary = Color(themeConfig.color.front),
            onSecondary = Color(themeConfig.color.front),
            onError = Color(themeConfig.color.front),

            background = Color(themeConfig.color.back),
            surface = Color(themeConfig.color.back),

            error = Color(themeConfig.color.error),
        )
    ) {
        content()
    }
}

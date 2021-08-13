package compose

import App
import EventHandler
import Resource
import Theme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Toolkit

fun launchAppWindow(themeConfig: Theme?, content: @Composable () -> Unit) {
    EventHandler.onStart()

    val barColor = if (themeConfig == null || themeConfig.isDark) {
        Color(255, 255, 255, 30)
    } else {
        Color(0, 0, 0, 30)
    }

    application {
        val density = LocalDensity.current.fontScale
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val screenWidth = screenSize.width / density
        val screenHeight = screenSize.height / density
        val icon = if (Resource.icon != null) loadSvgPainter(Resource.icon!!.openStream(), LocalDensity.current) else null
        val state = rememberWindowState(
            placement = WindowPlacement.Floating, position = WindowPosition(Alignment.Center),
            width = (screenWidth * 0.8).dp,
            height = (screenHeight * 0.8).dp
        )

        Window(
            title = App.name,
            icon = icon,
            undecorated = true,
            onCloseRequest = ::exit,
            state = state
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                WindowContent(themeConfig, content)
                WindowBar(barColor, state, ::exit)
            }
        }
    }
}

fun ApplicationScope.exit() {
    EventHandler.onEnd()
    exitApplication()
}

fun WindowState.minimize() {
    isMinimized = true
}

fun WindowState.switchMaximize() {
    placement = if (placement != WindowPlacement.Maximized && placement != WindowPlacement.Fullscreen)
        WindowPlacement.Maximized
    else
        WindowPlacement.Floating
}


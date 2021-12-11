package compose

import App
import EventHandler
import Resource
import Resource.transparentBackColor
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
import appConfig
import appScope
import java.awt.Toolkit
import kotlin.math.roundToInt

fun launchAppWindow(content: @Composable () -> Unit) {
    val config = try {
        appConfig
    } catch (_: UninitializedPropertyAccessException) {
        null
    }
    config?.let { EventHandler.onStart() }

    val barColor = if (config == null) {
        // A fallback color in the case the config isn't loaded (InitialError)
        Color(255, 255, 255, 30)
    } else {
        transparentBackColor
    }

    application {
        appScope = this
        val density = LocalDensity.current.fontScale
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val screenWidth = screenSize.width / density
        val screenHeight = screenSize.height / density
        val icon = if (Resource.icon != null) loadSvgPainter(Resource.icon!!.openStream(), LocalDensity.current) else null

        val defaultWindowSizeOfTotal = 0.8f to 0.8f
        val sizeOfTotal = config?.let {
            val width = config.window.widthP.toFloat() / 100f
            val height = config.window.heightP.toFloat() / 100f

            if (width in .3f..1f && height in .3f..1f) {
                width to height
            } else {
                defaultWindowSizeOfTotal
            }
        } ?: defaultWindowSizeOfTotal

        val windowPlacement = if (sizeOfTotal.first == 1f && sizeOfTotal.second == 1f) {
            WindowPlacement.Maximized
        } else {
            WindowPlacement.Floating
        }

        val state = rememberWindowState(
            placement = windowPlacement,
            position = WindowPosition(Alignment.Center),
            width = (screenWidth * sizeOfTotal.first).roundToInt().dp,
            height = (screenHeight * sizeOfTotal.second).roundToInt().dp,
        )

        Window(
            title = App.name,
            icon = icon,
            undecorated = true,
            onCloseRequest = ::exit,
            state = state,
            alwaysOnTop = config?.window?.alwaysOnTop ?: false
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                WindowContent(config?.theme, content)
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

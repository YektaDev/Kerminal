import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Toolkit

fun launchAppWindow(content: @Composable () -> Unit) {
    EventHandler.onStart()

    application {
        val density = LocalDensity.current.fontScale
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val screenWidth = screenSize.width / density
        val screenHeight = screenSize.height / density
        val icon = if (Resource.icon != null) loadSvgPainter(Resource.icon!!.openStream(), LocalDensity.current) else null

        Window(
            title = App.name,
            icon = icon,
            state = rememberWindowState(
                placement = WindowPlacement.Floating, position = WindowPosition(Alignment.Center),
                width = (screenWidth * 0.8).dp,
                height = (screenHeight * 0.8).dp
            ),
            onCloseRequest = ::exit
        ) {
            Scaffold(
                topBar = {
                    TopAppBar {
                        WindowDraggableArea {
                            IconButton(onClick = ::exit) {}
                        }
                    }
                },
                content = { content() }
            )
        }
    }
}

fun ApplicationScope.exit() {
    EventHandler.onEnd()
    exitApplication()
}
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.int
import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.PropertySource
import java.awt.Toolkit

// TODO: Handle the use of Clikt
class Hello : CliktCommand() {
    val count: Int by option(help = "Number of greetings").int().default(1)
    val name: String by option(help = "The person to greet").prompt("Your name")

    override fun run() {
        repeat(count) {
            echo("Hello $name!")
        }
    }
}

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    Button(onClick = {
        text = "Hello, Desktop!"
    }) {
        Text(text)
    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val config = ConfigLoader.Builder()
                .addSource(PropertySource.resource("config.toml"))
                .strict()
                .build()
                .loadConfigOrThrow<Config>()

            launchAppWindow {
                BitaTheme(config.theme) {
                    App()
                }
            }
        } catch (e: Exception) {
            launchAppWindow {
                DesktopMaterialTheme {
                    Box(
                        modifier = Modifier.background(Color.Black).fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = e.message ?: "Error: Could not find config file config.toml",
                            style = MaterialTheme.typography.h6,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}


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
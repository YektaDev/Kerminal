import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.int
import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.PropertySource

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
fun App(themeData: Theme) = BitaTheme(themeData) {
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
        val config = try {
            ConfigLoader.Builder()
                .addSource(PropertySource.resource("config.toml"))
                .strict()
                .build()
                .loadConfigOrThrow<Config>()
        } catch (e: Exception) {
            null
        }

        launchAppWindow {
            if (config != null) {
                App(config.theme)
            } else {
                InitialErrorPage(error = "Error: Could not find the file: config.toml")
            }
        }
    }
}

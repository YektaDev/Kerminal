import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.int
import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.toml.TomlPropertySource
import compose.launchAppWindow
import page.InitialErrorPage
import page.MainPage
import java.io.File

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

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        var error: String? = null
        val config = try {
            ConfigLoader.Builder()
                .addSource(TomlPropertySource(File(Resource.configPath).readText()))
                .build()
                .loadConfigOrThrow<Config>()
        } catch (e: Exception) {
            error = e.message
            null
        }

        if (config != null) {
            appConfig = config

            launchAppWindow {
                MainPage()
            }
        } else {
            launchAppWindow {
                InitialErrorPage(error = error ?: "Error: Could not find the file: ${Resource.configPath}")
            }
        }
    }
}

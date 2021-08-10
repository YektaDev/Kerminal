import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.int
import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.PropertySource
import compose.launchAppWindow
import page.InitialErrorPage
import page.MainPage

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
                .addSource(PropertySource.resource(Resource.configPath))
                .strict()
                .build()
                .loadConfigOrThrow<Config>()
        } catch (e: Exception) {
            error = e.message
            null
        }

        if (config != null) {
            launchAppWindow(config.theme) {
                MainPage(config.theme)
            }
        } else {
            launchAppWindow(null) {
                InitialErrorPage(error = error ?: "Error: Could not find the file: ${Resource.configPath}")
            }
        }
    }
}

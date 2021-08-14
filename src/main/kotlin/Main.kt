import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.toml.TomlPropertySource
import compose.launchAppWindow
import page.InitialErrorPage
import page.MainPage
import java.io.File

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

import androidx.compose.ui.graphics.Color
import java.net.URL
import java.util.*

lateinit var appConfig: Config

object App {
    val name: String by lazy {
        Resource.properties?.getProperty("name") ?: "[UNRESOLVED_NAME]"
    }
    val version: String by lazy {
        Resource.properties?.getProperty("version") ?: "[UNRESOLVED_VERSION]"
    }
    val group: String by lazy {
        Resource.properties?.getProperty("group") ?: "[UNRESOLVED_GROUP]"
    }
}

object Resource {
    const val configPath = "config.toml"
    private const val iconPath = "icon.svg"
    private const val propertiesPath = "app.properties"

    val transparentBackColor = if (appConfig.theme.isDark) {
        Color(255, 255, 255, 30)
    } else {
        Color(0, 0, 0, 30)
    }

    val properties: Properties? by lazy {
        val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(propertiesPath)
        val props = Properties()
        props.load(stream)
        props
    }
    val icon: URL? by lazy {
        get(iconPath)
    }

    fun get(path: String): URL? = Resource::class.java.getResource(path) ?: null
}

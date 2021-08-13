data class Config(val theme: Theme, val window: Window)
data class Theme(val isDark: Boolean, val color: Color)
data class Window(val alwaysOnTop: Boolean)
data class Color(
    val front: Long, val back: Long, val primary: Long, val primaryVariant: Long, val secondary: Long,
    val success: Long, val warning: Long, val error: Long
)

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ConsoleState {
    private val initialText = """
        |
        |**************************************************************************************
        |                          oooooooooo  o88    o8
        |                           888    888 oooo o888oo  ooooooo
        |                           888oooo88   888  888    ooooo888
        |                           888    888  888  888  888    888
        |                          o888ooo888  o888o  888o 88ooo88 8o
        |**************************************************************************************
        |=> Welcome Sir!
        |
    """.trimMargin(marginPrefix = "|")

    var text by mutableStateOf(initialText)
        private set

    @JvmName("setConsoleText")
    fun setText(newText: String) {
        if (newText.count { it == '\n' } >= text.count { it == '\n' })
            text = newText
    }

    fun clean() {
        text = initialText
    }

    fun print(line: String) {
        text += "$line\n"
    }
}
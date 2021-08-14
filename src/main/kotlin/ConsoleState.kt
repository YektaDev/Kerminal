import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ConsoleState {
    val colorChangeIndexList = hashMapOf<Int, Long>()
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

    init {
        colorChangeIndexList[0] = appConfig.theme.color.primaryVariant
        colorChangeIndexList[87] = appConfig.theme.color.secondary
        colorChangeIndexList[375] = appConfig.theme.color.primaryVariant
        colorChangeIndexList[462] = appConfig.theme.color.warning
        colorChangeIndexList[478] = appConfig.theme.color.front
    }

    @JvmName("setConsoleText")
    fun setText(newText: String) {
        if (newText.count { it == '\n' } >= text.count { it == '\n' })
            text = newText
    }

    fun clean() {
        text = initialText
    }

    fun print(text: String, colorCode: Long? = null) {
        if (colorCode != null) {
            colorChangeIndexList[this.text.length] = colorCode
        }

        this.text += text
    }

    fun printLine(line: String? = null) {
        if (line != null) {
            text += "$line"
        }
        text += '\n'
    }

    fun printError(error: String?) = if (error != null) printLine("> Error: $error") else Unit
    fun printInfo(info: String?) = if (info != null) printLine("> Info: $info") else Unit
    fun printWarning(warning: String?) = if (warning != null) printLine("> Warning: $warning") else Unit
    fun printSuccess(success: String?) = if (success != null) printLine("> Success: $success") else Unit
}

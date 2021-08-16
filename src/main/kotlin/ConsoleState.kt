import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class ConsoleState {
    private var printQueue = ""

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
        |=> Hey There!
        |
    """.trimMargin(marginPrefix = "|")

    var textFieldValue by mutableStateOf(TextFieldValue(initialText, selection = TextRange(initialText.length)))
        private set

    var prevText by mutableStateOf(initialText)
        private set

    init {
        colorChangeIndexList[0] = appConfig.theme.color.primaryVariant
        colorChangeIndexList[87] = appConfig.theme.color.secondary
        colorChangeIndexList[375] = appConfig.theme.color.primaryVariant
        colorChangeIndexList[462] = appConfig.theme.color.warning
        colorChangeIndexList[477] = appConfig.theme.color.front
    }

    @JvmName("setConsoleTextFieldValue")
    fun setTextFieldValue(textFieldValue: TextFieldValue) {
        if (textFieldValue.text.count { it == '\n' } >= this.textFieldValue.text.count { it == '\n' }) {
            this.textFieldValue = textFieldValue
        }
    }

    fun setText(newText: String, jumpToEnd: Boolean) {
        if (newText.count { it == '\n' } >= this.textFieldValue.text.count { it == '\n' }) {
            this.textFieldValue = textFieldValue.copy(
                text = newText,
                selection = if (jumpToEnd) TextRange(newText.length) else this.textFieldValue.selection
            )
        }
    }

    fun setTextRange(selection: TextRange) {
        this.textFieldValue.text.length.let { length ->
            if (selection.max <= length)
                textFieldValue = textFieldValue.copy(textFieldValue.text, selection)
            else if (selection.min <= length)
                textFieldValue = textFieldValue.copy(textFieldValue.text, TextRange(selection.min, length))
        }
    }

    @JvmName("setConsolePrevText")
    fun setPrevText(text: String) {
        prevText = text
    }

    fun clean() {
        textFieldValue = textFieldValue.copy(initialText)
        prevText = initialText
    }

    fun print(text: String?, colorCode: Long? = null) {
        text?.let {
            printQueue += it
        }
        colorCode?.let {
            colorChangeIndexList[this.textFieldValue.text.length] = it
        }
    }

    fun printLine(line: String? = null) = print((line ?: "") + '\n')

    fun printError(error: String?) = if (error != null) printLine("> Error: $error") else Unit
    fun printInfo(info: String?) = if (info != null) printLine("> Info: $info") else Unit
    fun printWarning(warning: String?) = if (warning != null) printLine("> Warning: $warning") else Unit
    fun printSuccess(success: String?) = if (success != null) printLine("> Success: $success") else Unit

    fun printMessageFlow() = flow {
        while (true) {
            if (printQueue.isNotEmpty()) {
                emit(printQueue)
                printQueue = ""
            }
            delay(50)
        }
    }
}

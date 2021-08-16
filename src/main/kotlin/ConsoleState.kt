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
    private val initialText = ""

    var textFieldValue by mutableStateOf(TextFieldValue(initialText, selection = TextRange(initialText.length)))
        private set

    var prevText by mutableStateOf(initialText)
        private set

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
        if (text == null) {
            return
        }
        if (colorCode != null) {
            val startIndex = textFieldValue.text.length + printQueue.length
            colorChangeIndexList[startIndex] = colorCode
            colorChangeIndexList[startIndex + text.length] = appConfig.theme.color.front
        }
        printQueue += text
    }

    fun printLine(line: String? = null, colorCode: Long? = null) = print((line ?: "") + '\n', colorCode)

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

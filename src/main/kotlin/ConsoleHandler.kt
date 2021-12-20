import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle

object ConsoleHandler {
    private lateinit var builder: AnnotatedString.Builder

    fun shouldNotChange(newText: String) = ::builder.isInitialized && (
            State.console.prevText == newText ||
                    !identicalLinesExceptLastLineAreEqual(newText, State.console.prevText) ||
                    isAtTheBeginningOfLinesExceptOne(State.console.prevText, newText)
            )

    fun processInput(textFieldValue: TextFieldValue): AnnotatedString {
        val text = textFieldValue.text.replace("\r", "")

        if (shouldNotChange(text)) {
            return builder.toAnnotatedString()
        }

        if (text.isNotEmpty() && text[text.lastIndex] == '\n') {
            val rawUserLine = State.console.prevText.substring(State.console.prevText.lastIndexOf('\n') + 1)
            CommandProcessor.run(rawUserLine)
        }

        State.console.setPrevText(text)

        return buildAnnotatedStringWithColors(text)
    }

    private fun buildAnnotatedStringWithColors(text: String): AnnotatedString {
        builder = AnnotatedString.Builder()
        builder.process(text)
        return builder.toAnnotatedString()
    }
}

private fun String.exceptLastLine(): String {
    val lastNewLine = lastIndexOf('\n')
    return if (lastNewLine == -1) this
    else this.substring(0, lastNewLine)
}

private fun identicalLinesExceptLastLineAreEqual(s1: String, s2: String): Boolean {
    val s1LineCount = s1.count { it == '\n' } + 1
    val s2LineCount = s2.count { it == '\n' } + 1

    return if (s1LineCount == 1 && s2LineCount == 1) true
    else if (s1LineCount == s2LineCount) s1.exceptLastLine() == s2.exceptLastLine()
    else {
        val smallestIdenticalIndex = if (s1LineCount < s2LineCount)
            s1.lastIndexOf('\n')
        else
            s2.lastIndexOf('\n')

        if (smallestIdenticalIndex == -1) return true

        s1.substring(0, smallestIdenticalIndex + 1) == s2.substring(0, smallestIdenticalIndex + 1)
    }
}

private fun isAtTheBeginningOfLinesExceptOne(prevText: String, newText: String) =
    if (prevText.isEmpty() || newText.isEmpty())
        false
    else
        prevText[prevText.lastIndex] == '\n' && newText.length < prevText.length

private fun AnnotatedString.Builder.process(fullText: String) {
    val generator = StyleGenerator()

    fullText.forEachIndexed { index, char ->
        val style = generator.generateFor(fullText, index)

        if (style == null) append(char)
        else withStyle(style = style) { append(char) }
    }
}

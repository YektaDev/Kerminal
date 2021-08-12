import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle

object ConsoleHandler {
    private lateinit var builder: AnnotatedString.Builder
    private var prevText = ""

    fun processInput(text: String): AnnotatedString {
        if (::builder.isInitialized)
            if (
                prevText == text ||
                (prevText.lines().size > 1 && text.lines().size > 1 && text.elementAt(text.lastIndex) != '\n' && prevText.exceptLastLine() != text.exceptLastLine())
            )
                return builder.toAnnotatedString()

        prevText = text

        return buildAnnotatedStringWithColors(text)
    }

    private fun buildAnnotatedStringWithColors(text: String): AnnotatedString {
        builder = AnnotatedString.Builder()
        val colors = listOf(Color.Red, Color.Black, Color.Yellow, Color.Blue)
        val lines = text.getLines()

        for ((lineIndex, line) in lines.withIndex()) {
            if (line.isBlank()) {
                builder.append(line)
            } else {
                val words = line.split(" ")

                words.forEachIndexed { index, word ->
                    if (word.isBlank() && word.isNotEmpty())
                        builder.append(word)
                    else if (index == words.lastIndex) {
                        builder.withStyle(SpanStyle(color = colors[lineIndex % 4])) {
                            builder.append(word)
                        }
                    } else {
                        builder.append("$word ")
                    }
                }
            }

            if (lineIndex != lines.lastIndex) {
                builder.append("\n")
            }
        }

        return builder.toAnnotatedString()
    }
}

private fun String.getLines() = replace("\r", "").split("\n")

private fun String.exceptLastLine(): String {
    val lastNewLine = lastIndexOf('\n')
    return if (lastNewLine == -1)
        this
    else {
        this.substring(0, lastNewLine)
    }
}

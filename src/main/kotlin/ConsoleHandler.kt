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
                prevText == text || !identicalLinesExceptLastLineAreEqual(text, prevText) || isAtTheBeginningOfLinesExceptOne(prevText, text)
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

private fun identicalLinesExceptLastLineAreEqual(s1: String, s2: String): Boolean {
    val s1LineCount = s1.count { it == '\n' } + 1
    val s2LineCount = s2.count { it == '\n' } + 1

    return if (s1LineCount == 1 && s2LineCount == 1)
        true
    else if (s1LineCount == s2LineCount) {
        s1.exceptLastLine() == s2.exceptLastLine()
    } else {
        val smallestIdenticalIndex = if (s1LineCount < s2LineCount)
            s1.lastIndexOf('\n')
        else
            s2.lastIndexOf('\n')

        if (smallestIdenticalIndex == -1) {
            return true
        }

        s1.substring(0, smallestIdenticalIndex + 1) == s2.substring(0, smallestIdenticalIndex + 1)
    }
}

private fun isAtTheBeginningOfLinesExceptOne(prevText: String, newText: String) =
    if (prevText.isEmpty() || newText.isEmpty())
        false
    else
        prevText[prevText.lastIndex] == '\n' && newText.length < prevText.length

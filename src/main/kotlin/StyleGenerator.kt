import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle

object StyleGenerator {
    private var stringPoints = false
    private var shouldChangeColor = false

    fun AnnotatedString.Builder.forLine(line: String) {
        val words = line.split(" ")

        words.forEachIndexed { wordIndex, word ->
            if (word.isBlank() && word.isNotEmpty())
                append(word)
            else if (wordIndex == words.lastIndex) {
                stylize(word, wordIndex)
            } else {
                stylize("$word ", wordIndex)
            }
        }

        stringPoints = false
        shouldChangeColor = false
    }

    private fun AnnotatedString.Builder.stylize(word: String, index: Int) {


        word.forEachIndexed { charIndex, char ->
            if (shouldChangeColor) {
                stringPoints = false
                shouldChangeColor = false
            }

            if (char == '\"' && (charIndex == 0 || word[charIndex - 1] != '\\')) {
                if (!stringPoints) {
                    stringPoints = true
                } else {
                    shouldChangeColor = true
                }
            }

            if (stringPoints) {
                withStyle(SpanStyle(color = Green)) {
                    append(char)
                }
            } else {
                append(char)
            }
        }
    }
}

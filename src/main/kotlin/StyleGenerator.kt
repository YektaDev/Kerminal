import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.text.SpanStyle

class StyleGenerator {
    // Only to detect strings quoted with "
    private var isInsideString = false
    private var shouldChangeColor = false

    // For user-defined color
    private var lastColor: Color? = null

    fun generateFor(text: String, index: Int): SpanStyle? {
        if (shouldChangeColor) {
            isInsideString = false
            shouldChangeColor = false
        }

        if (text[index] == '\"' && (index == 0 || text[index - 1] != '\\')) {
            if (!isInsideString) {
                isInsideString = true
            } else {
                shouldChangeColor = true
            }
        }

        if (isInsideString) {
            return SpanStyle(color = Green)
        }

        State.console.colorChangeIndexList[index]?.let {
            lastColor = Color(it)
        }

        if (lastColor != null) {
            return SpanStyle(color = lastColor!!)
        }

        return null
    }
}

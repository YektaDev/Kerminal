import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.text.SpanStyle

class StyleGenerator {
    // Only for detection of "
    private var stringPoints = false
    private var shouldChangeColor = false

    // For user-defined color
    private var lastColor: Color? = null

    fun generateFor(text: String, index: Int): SpanStyle? {
        if (shouldChangeColor) {
            stringPoints = false
            shouldChangeColor = false
        }

        if (text[index] == '\"' && (index == 0 || text[index - 1] != '\\')) {
            if (!stringPoints) {
                stringPoints = true
            } else {
                shouldChangeColor = true
            }
        }

        if (stringPoints) {
            return SpanStyle(color = Green)
        }

        ConsoleManager.state.colorChangeIndexList[index]?.let {
            lastColor = Color(it)
        }

        if (lastColor != null) {
            return SpanStyle(color = lastColor!!)
        }

        return null
    }
}

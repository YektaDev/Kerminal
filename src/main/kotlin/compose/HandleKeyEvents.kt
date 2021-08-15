package compose

import State
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange

@Composable
fun Modifier.HandleKeyEvents() = onKeyEvent { event ->
    var handled = false

    with(State.console) {
        textFieldValue.selection.let { selection ->
            when (event.key) {
                Key.DirectionRight -> {
                    if (event.isShiftPressed) {
                        setTextRange(TextRange(selection.start, selection.end + 1))
                    } else {
                        setTextRange(TextRange(selection.end + 1))
                    }
                }

                Key.DirectionLeft -> {
                    if (event.isShiftPressed) {
                        setTextRange(TextRange(selection.start, selection.end - 1))
                    } else {
                        setTextRange(TextRange(selection.end - 1))
                    }
                }

                Key.ShiftLeft -> {
                }
                Key.ShiftRight -> {
                }

                else -> {
                    setTextRange(TextRange(textFieldValue.text.length))
                    handled = false
                }
            }
        }
    }

    handled
}


package compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState

@Composable
fun WindowScope.WindowBar(color: Color, windowState: WindowState, exitClick: () -> Unit) = this.WindowDraggableArea {
    TopAppBar(
        modifier = Modifier
            .height(32.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 32.dp),
        backgroundColor = color,
        elevation = 0.dp,
    ) {
        Row {
            WindowBarButton(Color(0xffff1744), exitClick)
            Spacer(Modifier.width(8.dp))
            WindowBarButton(Color(0xff00e676)) { windowState.switchMaximize() }
            Spacer(Modifier.width(8.dp))
            WindowBarButton(Color(0xffffea00)) { windowState.minimize() }
        }
    }
}

@Composable
fun WindowBarButton(color: Color, onClick: () -> Unit) = IconButton(
    onClick = onClick,
    modifier = Modifier
        .background(
            color = color,
            shape = RoundedCornerShape(100)
        )
        .size(12.dp)
) {}

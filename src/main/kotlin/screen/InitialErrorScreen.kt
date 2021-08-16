package screen

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun InitialErrorPage(error: String) = DesktopMaterialTheme {
    Box(
        modifier = Modifier.background(Color.Black).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.h6,
            color = Color(41, 171, 226)
        )
    }
}

import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.value.MutableValue

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    val showingWindow = remember { mutableStateOf(true) }

    Tray(
        ColorPainter(Color.Red),
        onAction = {
            showingWindow.value = true
        }
    )

    if (showingWindow.value) {
        DisposableEffect(Unit) {
            MutableValue(1).value = 2
            onDispose {}
        }

        Window(onCloseRequest = { showingWindow.value = false }) {
            App()
        }
    }
}

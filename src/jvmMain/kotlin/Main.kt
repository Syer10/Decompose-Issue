import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.value.MutableValue
import java.awt.Desktop
import java.awt.desktop.AppReopenedEvent
import java.awt.desktop.AppReopenedListener

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
    DisposableEffect(Unit) {
        Desktop.getDesktop().addAppEventListener(
            object : AppReopenedListener {
                override fun appReopened(e: AppReopenedEvent?) {
                    showingWindow.value = true
                }
            }
        )
        onDispose {  }
    }
    // Have a coroutine scope so the app remains open
    val coroutineScope = rememberCoroutineScope()

    if (showingWindow.value) {
        Window(onCloseRequest = { showingWindow.value = false }) {
            DisposableEffect(showingWindow.value) {
                println(Thread.currentThread().let {
                    it.id to it.name
                })
                MutableValue(1).value = 2
                onDispose {}
            }
            App()
        }
    }
}

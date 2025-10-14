package org.aasoft.easypos

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "esayPOS",
    ) {
        App()
    }
}
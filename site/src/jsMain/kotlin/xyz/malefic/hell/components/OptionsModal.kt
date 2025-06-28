package xyz.malefic.hell.components

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.hell.theme.ThemeManager

@Composable
fun OptionsModal(onClose: () -> Unit) {
    var musicMuted by remember { mutableStateOf(false) }
    var sfxMuted by remember { mutableStateOf(false) }
    
    val themeOptions = listOf("Auto", "Day", "Night", "Cloudy")

    Div {
        Text("Options")
        
        Button({
            onClick { musicMuted = !musicMuted }
        }) {
            Text(if (musicMuted) "Music: Muted" else "Music: On")
        }
        
        Button({
            onClick { sfxMuted = !sfxMuted }
        }) {
            Text(if (sfxMuted) "Sound Effects: Muted" else "Sound Effects: On")
        }
        
        Button({
            onClick { 
                val currentIndex = themeOptions.indexOf(ThemeManager.themeMode)
                val nextIndex = (currentIndex + 1) % themeOptions.size
                ThemeManager.themeMode = themeOptions[nextIndex]
            }
        }) {
            Text("Theme: ${ThemeManager.themeMode}")
        }
        
        Button({
            onClick { onClose() }
        }) {
            Text("Close")
        }
    }
} 
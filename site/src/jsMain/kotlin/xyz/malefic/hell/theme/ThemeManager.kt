package xyz.malefic.hell.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object ThemeManager {
    var themeMode by mutableStateOf("Auto")
    
    fun getCurrentTheme(): AppTheme {
        return when(themeMode) {
            "Auto" -> {
                val currentHour = js("new Date().getHours()").unsafeCast<Int>()
                if (currentHour >= 6 && currentHour < 19) AppTheme.DAY_SUNNY else AppTheme.NIGHT
            }
            "Day" -> AppTheme.DAY_SUNNY
            "Night" -> AppTheme.NIGHT
            "Cloudy" -> AppTheme.DAY_CLOUDY
            else -> AppTheme.DAY_SUNNY
        }
    }
} 
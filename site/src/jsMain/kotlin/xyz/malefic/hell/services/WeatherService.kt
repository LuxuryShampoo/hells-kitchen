package xyz.malefic.hell.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.browser.window
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.await

// 1. WeatherData class (augmented)
data class WeatherData(
    val temperature: String,
    val description: String,
    val iconUrl: String,
    val cloudCover: Int? = null // Percentage of cloud cover
)

// 2. WeatherService object (singleton)
@OptIn(DelicateCoroutinesApi::class)
object WeatherService {
    // Public states
    var weatherDataResult by mutableStateOf<Result<WeatherData>?>(null)
        private set // Expose only for reading
    var isLoadingWeather by mutableStateOf(false)
        private set

    // Initial fetch trigger (can be called from AppEntry or similar)
    fun initialFetch() {
        if (weatherDataResult == null && !isLoadingWeather) { // Fetch only if not already fetched or loading
            console.log("[WeatherService] Initial weather fetch triggered.")
            fetch()
        }
    }

    fun refreshWeather() {
        console.log("[WeatherService] Refresh weather triggered.")
        fetch()
    }

    // 3. fetchWeather function (adapted from FooterBar.kt)
    private fun fetch() {
        isLoadingWeather = true
        console.log("[WeatherService] fetchWeather Called - Using WeatherAPI.com")
        val navigatorDynamic = window.navigator.asDynamic()

        if (js("typeof navigatorDynamic.geolocation === 'undefined' || typeof navigatorDynamic.geolocation.getCurrentPosition !== 'function'")) {
            console.error("[WeatherService] Geolocation API is not available.")
            weatherDataResult = Result.failure(Exception("Geolocation API not available"))
            isLoadingWeather = false
            return
        }

        val geolocation = navigatorDynamic.geolocation
        console.log("[WeatherService] Attempting to get current position (timeout: 10s, low accuracy)...")
        val options = js("{ enableHighAccuracy: false, timeout: 10000, maximumAge: 600000 }")

        geolocation.getCurrentPosition(
            successCallback = { positionDynamic ->
                console.log("[WeatherService] Geolocation success. Position:", positionDynamic)
                val coords = positionDynamic.coords
                if (coords == null || js("typeof coords.latitude === 'undefined' || typeof coords.longitude === 'undefined'")) {
                    console.error("[WeatherService] Geolocation coordinates are missing or invalid.")
                    weatherDataResult = Result.failure(Exception("Geolocation coordinates missing"))
                    isLoadingWeather = false
                    return@getCurrentPosition
                }
                val lat = coords.latitude
                val lon = coords.longitude
                val apiKey = "3c1f971c84874bd78de20739250406"
                val url = "https://api.weatherapi.com/v1/current.json?key=$apiKey&q=$lat,$lon&aqi=no"
                console.log("[WeatherService] Fetching weather from: $url")
                console.log("[WeatherService] Preparing to launch coroutine for API call...")

                GlobalScope.launch { // Use GlobalScope for the service, manage cancellation if service is destroyed (not typical for singleton object)
                    console.log("[WeatherService] Coroutine launched. Inside GlobalScope.launch.")
                    try {
                        console.log("[WeatherService] Entering try block for fetch operation. About to call window.fetch.")
                        val response = window.fetch(url).await() // Await fetch directly

                        console.log("[WeatherService] API Response status: ${response.status} (${response.statusText})")

                        if (!response.ok) {
                            val errorBody = response.text().await() // Await error body text
                            val errorMsg = "API Error ${response.status} (${response.statusText}). Body: $errorBody"
                            console.error("[WeatherService] HTTP Error: $errorMsg")
                            throw Exception(errorMsg)
                        }

                        val responseText = response.text().await() // Await success body text
                        
                        console.log("[WeatherService] API Response text received (length: ${responseText.length}):\n'$responseText'")
                        val parsedData: Any? = JSON.parse(responseText)
                        console.log("[WeatherService] Weather API JSON data successfully parsed:", parsedData)

                        val current = parsedData?.asDynamic()?.current // current holds the JS object for "current"
                        if (current != null) {
                            // Access properties directly on 'current' as it's a JS object
                            val tempDynamic: Any? = current.temp_c
                            val conditionObj: Any? = current.condition // This will be the nested JS 'condition' object
                            val cloudDynamic: Any? = current.cloud

                            // Access properties on 'conditionObj'
                            // Make sure conditionObj itself is not null before trying to access its properties
                            val textFromConditionDynamic: Any? = if (conditionObj == null || js("typeof conditionObj === 'undefined'")) null else conditionObj.asDynamic().text
                            val iconFromConditionDynamic: Any? = if (conditionObj == null || js("typeof conditionObj === 'undefined'")) null else conditionObj.asDynamic().icon

                            if (tempDynamic != null && textFromConditionDynamic != null && iconFromConditionDynamic != null) {
                                val tempStr = tempDynamic.toString()
                                val descriptionStr = textFromConditionDynamic.toString()
                                var iconUrlStr = iconFromConditionDynamic.toString()
                                
                                if (iconUrlStr.startsWith("//")) iconUrlStr = "https:$iconUrlStr"
                                // Cloud cover is optional, handle its potential nullness or cast error carefully
                                val cloudInt: Int? = try { cloudDynamic?.unsafeCast<Int>() } catch (e: Exception) { null }

                                console.log("[WeatherService] Parsed weather - Temp: $tempStr, Desc: $descriptionStr, Icon: $iconUrlStr, Cloud: $cloudInt")
                                weatherDataResult = Result.success(WeatherData(tempStr, descriptionStr, iconUrlStr, cloudInt))
                            } else {
                                // Log 'current' directly
                                console.error("[WeatherService] Essential weather data fields (temp_c, condition.text, or condition.icon) are missing or null. Full 'current' object: ", current) 
                                weatherDataResult = Result.failure(Exception("Essential weather data fields missing or null"))
                            }
                        } else {
                            console.error("[WeatherService] 'current' field is null or undefined in parsed data.")
                            weatherDataResult = Result.failure(Exception("Weather data is incomplete ('current' is null)"))
                        }
                    } catch (e: dynamic) {
                        val errorMessage = e.message?.toString() ?: "Unknown error in fetch/parse chain"
                        console.error("[WeatherService] Fetch/parse error: $errorMessage", e)
                        weatherDataResult = Result.failure(Exception(errorMessage))
                    }
                    isLoadingWeather = false
                }
            },
            errorCallback = { errorDynamic ->
                val code = errorDynamic.asDynamic().code
                val message = errorDynamic.asDynamic().message?.toString() ?: "Unknown geolocation error"
                val publicMessage = when (code?.unsafeCast<Int>()) {
                    1 -> "Location permission denied."
                    2 -> "Location position unavailable."
                    3 -> "Location request timed out."
                    else -> "Could not get location."
                }
                console.error("[WeatherService] Geolocation error. Code: $code, Message: $message. Public: $publicMessage", errorDynamic)
                weatherDataResult = Result.failure(Exception(publicMessage))
                isLoadingWeather = false
            },
            options = options
        )
    }
} 
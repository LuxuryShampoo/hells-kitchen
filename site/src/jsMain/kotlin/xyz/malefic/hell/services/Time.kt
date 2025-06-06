package xyz.malefic.hell.services

import kotlinx.browser.window
import kotlin.js.Date

/**
 * Updates the current date and time periodically and passes the formatted values to the provided callback function.
 *
 * @param update A callback function that accepts two parameters:
 *               - `time`: The current time in "HH:mm" format.
 *               - `day`: The current day in "Day MM/DD" format (e.g., "Mon 10/23").
 */
fun updateDateTime(update: (String, String) -> Unit) {
    val updateTime = {
        val date = Date()
        val hours = date.getHours().toString().padStart(2, '0')
        val minutes = date.getMinutes().toString().padStart(2, '0')
        val time = "$hours:$minutes"

        val days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        val dayOfWeek = days[date.getDay()]
        val month = (date.getMonth() + 1).toString()
        val dayOfMonth = date.getDate().toString()
        val day = "$dayOfWeek $month/$dayOfMonth"

        update(time, day)
    }

    updateTime()
    window.setInterval(updateTime, 60000)
}

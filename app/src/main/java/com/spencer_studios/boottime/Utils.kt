package com.spencer_studios.boottime

import android.os.Build
import android.os.SystemClock
import android.text.Spanned
import androidx.core.text.HtmlCompat.*
import org.joda.time.DateTime
import org.joda.time.Period
import java.text.DateFormat
import java.util.*

fun getInfo(): Array<String> {
    val uptime = System.currentTimeMillis() - SystemClock.elapsedRealtime()
    val device = "${Build.MANUFACTURER} ${Build.MODEL}"
    val date = formatDate(uptime)
    val time = DateFormat.getTimeInstance().format(uptime)
    return arrayOf(device, date[0],  date[1], time)
}

fun formatDate(millis: Long): Array<String> {
    val now = Calendar.getInstance()
    val bootTime = Calendar.getInstance().apply {
        timeInMillis = millis
    }

    if (now.get(Calendar.YEAR) == bootTime.get(Calendar.YEAR)) {
        val compareDay = now.get(Calendar.DAY_OF_YEAR) - bootTime.get(Calendar.DAY_OF_YEAR)
        if (compareDay == 0)
            return arrayOf("was booted", "TODAY")
        if (compareDay == 1)
            return arrayOf("was booted", "YESTERDAY")
    }
    return arrayOf("was booted on", DateFormat.getDateInstance().format(millis))
}

fun formatUptime(): String {
    val now = System.currentTimeMillis()
    val period = Period(DateTime(now - SystemClock.elapsedRealtime()), DateTime(now))
    val units = arrayOf(period.hours, period.minutes, period.seconds)

    return if (units[0] > 0) {
        String.format("%02d:%02d:%02d", units[0], units[1], units[2])
    }
    else {
        String.format("%02d:%02d", units[1], units[2])
    }
}
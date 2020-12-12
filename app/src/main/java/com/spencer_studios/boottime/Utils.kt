package com.spencer_studios.boottime

import android.os.Build
import android.os.SystemClock
import android.text.Spanned
import androidx.core.text.HtmlCompat.*
import org.joda.time.DateTime
import org.joda.time.Period
import java.text.DateFormat

fun getInfo(): Spanned {
    val now = System.currentTimeMillis() - SystemClock.elapsedRealtime()
    return fromHtml(
        "this<br><big><big><b>${Build.MANUFACTURER} ${Build.MODEL}<br></b></big></big><b>was booted on<br><big><big><b>${DateFormat.getDateInstance()
            .format(now)}</b></big></big><br>at<br><big><big><b>${DateFormat.getTimeInstance()
            .format(now)}</b></big></big>", FROM_HTML_MODE_LEGACY
    )
}

fun formatUptime(): String {
    val now = System.currentTimeMillis()
    val period = Period(DateTime(now - SystemClock.elapsedRealtime()), DateTime(now))

    val units = arrayOf(period.hours, period.minutes, period.seconds)
    return if (units[0] > 0) {
        String.format(
            "%02d   hours\n%02d minutes\n%02d seconds\nago",
            units[0],
            units[1],
            units[2]
        )
    } else {
        String.format(
            "%02d minutes\n%02d seconds\nago",
            units[1],
            units[2]
        )
    }
}
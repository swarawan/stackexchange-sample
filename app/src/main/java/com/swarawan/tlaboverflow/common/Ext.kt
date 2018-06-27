package com.swarawan.tlaboverflow.common

import java.util.Calendar

/**
 * Created by Rio Swarawn on 6/27/18.
 */

fun Long.convertDate(): String {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = this@convertDate
    }

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    return "$dayOfMonth / $month / $year"
}
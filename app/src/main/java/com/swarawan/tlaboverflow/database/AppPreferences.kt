package com.swarawan.tlaboverflow.database

import com.swarawan.corelibrary.sharedprefs.CorePreferences
import com.swarawan.corelibrary.utils.TextUtils

/**
 * Created by Rio Swarawn on 6/27/18.
 */
class AppPreferences(private val corePreferences: CorePreferences) {

    companion object {
        const val TAG = "tag"
        const val PER_PAGE = "per-page"
        const val FROM_DATE = "from-date"
        const val TO_DATE = "to-date"
    }

    var tag: String
        get() = corePreferences.getString(TAG, TextUtils.BLANK)
        set(value) = corePreferences.setString(TAG, value)

    var perPage: String
        get() = corePreferences.getString(PER_PAGE, "10")
        set(value) = corePreferences.setString(PER_PAGE, value)

    var fromDate: String
        get() = corePreferences.getString(FROM_DATE, TextUtils.BLANK)
        set(value) = corePreferences.setString(FROM_DATE, value)

    var toDate: String
        get() = corePreferences.getString(TO_DATE, TextUtils.BLANK)
        set(value) = corePreferences.setString(TO_DATE, value)

    fun clear() {
        corePreferences.clear()
    }
}
package wittie.test.sundaychill.domain

import android.content.Context

private const val KEY_SHARED_PREF = "wittie.sundaychill.preferences.app"
private const val KEY_SEE_BEST = "KEY_SEE_BEST"

class AppSharedPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(KEY_SHARED_PREF, Context.MODE_PRIVATE)!!

    var isSeeBestChecked: Boolean
        get() = preferences.getBoolean(KEY_SEE_BEST, false)
        set(value) = preferences.edit().putBoolean(KEY_SEE_BEST, value).apply()

}
package com.kishannareshpal.bottomlistsheet.utils

import android.content.Context
import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * Prevent multiple rapid clicks on a view, within a given interval.
 * @see <a>https://medium.com/@simonkarmy2004/solving-android-multiple-clicks-problem-kotlin-b99c06135da0</a>
 */
private class SafeClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

/**
 * An extension to set a click listener with a click interval, to prevent spam.
 */
internal fun View.setSafeClickListener(defaultInterval: Int = 1600, onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener(defaultInterval = defaultInterval) {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}
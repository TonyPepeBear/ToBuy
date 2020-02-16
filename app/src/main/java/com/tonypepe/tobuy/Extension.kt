package com.tonypepe.tobuy

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun Any.logd(message: Any?) {
    Log.d(this::class.java.simpleName, message.toString())
}

fun View.snackBar(message: Any?) {
    Snackbar.make(this, message.toString(), Snackbar.LENGTH_SHORT)
}

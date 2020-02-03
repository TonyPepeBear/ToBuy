package com.tonypepe.tobuy

import android.util.Log

fun Any.logd(message: Any) {
    Log.d(this::class.java.simpleName, message.toString())
}

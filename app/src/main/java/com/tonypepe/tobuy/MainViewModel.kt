package com.tonypepe.tobuy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tonypepe.tobuy.data.AppDatabase

class MainViewModel(val app: Application) : AndroidViewModel(app) {
    val database = AppDatabase.getInstance(app.applicationContext)

}

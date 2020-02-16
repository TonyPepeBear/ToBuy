package com.tonypepe.tobuy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.tonypepe.tobuy.data.AppDatabase
import com.tonypepe.tobuy.data.Item

class MainViewModel(val app: Application) : AndroidViewModel(app) {
    val database = AppDatabase.getInstance(app.applicationContext)

    fun getAllLiveData(): LiveData<PagedList<Item>> {
        return database.getAll()
    }

    fun insertItem(item: Item) {
        database.insertItem(item)
    }

    fun updateItem(item: Item) {
        database.updateItem(item)
    }

    fun deleteItem(item: Item) {
        database.deleteItem(item)
    }
}

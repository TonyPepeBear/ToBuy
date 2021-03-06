package com.tonypepe.tobuy.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.runBlocking

@Database(entities = [Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "my-db")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }

    fun getAll(): LiveData<PagedList<Item>> {
        val dataSource = itemDao().getAllItem()
        return LivePagedListBuilder(dataSource, 30).build()
    }

    fun insertItem(item: Item) {
        runBlocking {
            itemDao().insertItem(item)
        }
    }

    fun updateItem(item: Item) {
        runBlocking {
            itemDao().updateItem(item.name, item.count)
        }
    }

    fun deleteItem(item: Item) {
        runBlocking {
            itemDao().deleteItem(item)
        }
    }

}

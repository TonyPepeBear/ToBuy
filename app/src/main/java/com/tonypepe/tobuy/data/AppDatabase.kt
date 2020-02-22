package com.tonypepe.tobuy.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.runBlocking

@Database(entities = [Item::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        private var instance: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table item add column alertTime integer not null default 0")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "my-db")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
            return instance!!
        }
    }

    fun getAllPagedData(): LiveData<PagedList<Item>> {
        val dataSource = itemDao().getAllItem()
        return LivePagedListBuilder(dataSource, 30).build()
    }

    fun getItem(name: String) = itemDao().getItem(name)

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

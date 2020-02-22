package com.tonypepe.tobuy.data

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface ItemDao {

    @Query("select * from item order by count desc")
    fun getAllItem(): DataSource.Factory<Int, Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Query("update item set count = :count where name = :name")
    suspend fun updateItem(name: String, count: Int)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("select * from item where name = :name")
    fun getItem(name: String): Item
}

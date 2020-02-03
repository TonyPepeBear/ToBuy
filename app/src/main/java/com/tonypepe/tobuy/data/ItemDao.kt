package com.tonypepe.tobuy.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query

@Dao
interface ItemDao {

    @Query("select * from item")
    fun getAllItem(): DataSource.Factory<Int, Item>
}

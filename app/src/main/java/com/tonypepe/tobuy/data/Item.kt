package com.tonypepe.tobuy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey var name: String,
    var count: Int
)

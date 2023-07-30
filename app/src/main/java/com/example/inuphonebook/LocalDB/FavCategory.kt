package com.example.inuphonebook.LocalDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavCategory")
data class FavCategory(
    @ColumnInfo val category : String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id : Int = 0
)

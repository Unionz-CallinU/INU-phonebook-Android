package com.example.inuphonebook.LocalDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Professor")
data class Professor(
    @ColumnInfo val name : String,
    @ColumnInfo val phone : String,
    @ColumnInfo val email : String,
    @ColumnInfo val photo : Int,
    @ColumnInfo val lab : String,
    @ColumnInfo val isFavorite : Boolean,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id : Int
)

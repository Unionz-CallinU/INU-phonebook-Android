package com.example.inuphonebook.LocalDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.reflect.Constructor

@Entity(tableName = "Employee")
data class Employee(
    @ColumnInfo val name : String,
    @ColumnInfo val role : String,
    @ColumnInfo val phone : String,
    @ColumnInfo val isFavorite : Boolean,
    @ColumnInfo val photo : Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id : Int
)

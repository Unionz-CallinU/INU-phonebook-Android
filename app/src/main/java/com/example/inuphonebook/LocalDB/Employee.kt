package com.example.inuphonebook.LocalDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Employee")
data class Employee(
    @ColumnInfo val name : String,
    @ColumnInfo val role : String,
    @ColumnInfo val phone : String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id : Int
)

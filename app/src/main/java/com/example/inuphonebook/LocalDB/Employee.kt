package com.example.inuphonebook.LocalDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Employee")
data class Employee(
    @ColumnInfo val category : String?,
    @ColumnInfo val name : String,
    @ColumnInfo val role : String,
    @ColumnInfo val phoneNumber : String,
    @ColumnInfo val isFavorite : Boolean,
    @ColumnInfo val photo : String,
    @ColumnInfo val email : String,
    @ColumnInfo val college_name : String,
    @ColumnInfo val department_name : String,
    @PrimaryKey
    @ColumnInfo val id : Long
)

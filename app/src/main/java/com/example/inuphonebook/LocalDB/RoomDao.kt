package com.example.inuphonebook.LocalDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RoomDao {
  //즐겨찾기 list 받기
    @Query("SELECT * FROM employee")
    fun getAllEmployee() : List<Employee>

    //즐겨찾기 삭제
    @Query("DELETE FROM employee WHERE id = (:id)")
    fun deleteEmployee(id : Int)

    //즐겨찾기 추가
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertEmployee(employee : Employee)

    //즐겨찾기 수정
    @Query("UPDATE employee SET isFavorite = (:isFavorite) WHERE id = (:id)")
    fun updateEmployee(id : Int, isFavorite : Boolean)

}
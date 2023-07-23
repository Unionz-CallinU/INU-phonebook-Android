package com.example.inuphonebook.LocalDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RoomDao {

    //즐겨찾기 교수님들 list 받기
    @Query("SELECT * FROM Professor")
    fun getAllProfessor() : List<Professor>

    //즐겨찾기 삭제 by 교수님
    @Query("DELETE FROM professor WHERE id = (:id)")
    fun deleteProfessor(id : Int)

    //즐겨찾기 추가 by 교수님
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertProfessor(professor : Professor)

    //즐겨찾기 수정 by 교수님
    @Query("UPDATE professor SET isFavorite = (:isFavorite) WHERE id = (:id)")
    fun updateProfessor(id : Int, isFavorite : Boolean)

    //즐겨찾기 임직원 list 받기
    @Query("SELECT * FROM employee")
    fun getAllEmployee() : List<Employee>

    //즐겨찾기 삭제 by 임직원
    @Query("DELETE FROM employee WHERE id = (:id)")
    fun deleteEmployee(id : Int)

    //즐겨찾기 추가 by 임직원
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertEmployee(employee : Employee)

    //즐겨찾기 수정 by 임직원
    @Query("UPDATE employee SET isFavorite = (:isFavorite) WHERE id = (:id)")
    fun updateEmployee(id : Int, isFavorite : Boolean)

}
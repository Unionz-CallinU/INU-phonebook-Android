package com.example.inuphonebook.LocalDB

import android.content.Context

class RoomRepository(context : Context) {
    private var roomDB : RoomDB
    private var roomDao : RoomDao

    init{
        roomDB = RoomDB.getInstance(context)
        roomDao = roomDB.RoomDao()
    }
    fun getFavEmployee() : MutableList<Employee>{
        return roomDao.getAllEmployee()
    }
    fun insertEmployee(employee : Employee){
        roomDao.insertEmployee(employee)
    }
    fun deleteEmployee(id : Int){
        roomDao.deleteEmployee(id)
    }
    fun updateEmployee(id : Int, isFavorite : Boolean){
        roomDao.updateEmployee(id, isFavorite)
    }

    fun getAllCategory() : MutableList<FavCategory> {
        return roomDao.getAllCategory()
    }

    fun insertCategory(category : FavCategory){
        roomDao.insertCategory(category)
    }

    fun deleteCategory(id : Int){
        roomDao.deleteCategory(id)
    }

    companion object{
        private var INSTANCE : RoomRepository ?= null

        fun get(context : Context) : RoomRepository{
            return INSTANCE ?:
            RoomRepository(context)
        }
    }
}
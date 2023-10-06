package inuphonebook.LocalDB

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
    fun deleteEmployee(id : Long){
        roomDao.deleteEmployee(id)
    }
    fun updateEmployee(id : Long, isFavorite : Boolean){
        roomDao.updateEmployee(id, isFavorite)
    }

    fun getEmployeeById(id : Long) : Employee{
        return roomDao.getEmployeeById(id)
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

    fun getCategoryByName(category : String) : FavCategory?{
        return roomDao.getCategoryByName(category)
    }

    fun updateEmployeeCategory(id : Long, category : String) : Employee{
        roomDao.updateEmployeeCategory(id, category)
        return roomDao.getEmployeeById(id)
    }

    fun getEmployeesInCategory(category : String) : Int {
        return roomDao.getEmployeesInCategory(category)
    }

    fun checkDupCategory(category : String) : FavCategory? {
        return roomDao.getCategoryByName(category)
    }

    companion object{
        private var INSTANCE : RoomRepository ?= null

        fun get(context : Context) : RoomRepository{
            return INSTANCE ?:
            RoomRepository(context)
        }
    }
}
package inuphonebook.LocalDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import inuphonebook.LocalDB.Employee
import inuphonebook.LocalDB.FavCategory

@Dao
interface RoomDao {
    //즐겨찾기 list 받기
    @Query("SELECT * FROM employee")
    fun getAllEmployee() : MutableList<Employee>

    //즐겨찾기 삭제
    @Query("DELETE FROM employee WHERE id = (:id)")
    fun deleteEmployee(id : Long)

    //즐겨찾기 추가
    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertEmployee(employee : Employee)

    //즐겨찾기 수정
    @Query("UPDATE employee SET isFavorite = (:isFavorite) WHERE id = (:id)")
    fun updateEmployee(id : Long, isFavorite : Boolean)

    //즐겨찾기 데이터 받아오기
    @Query("SELECT * FROM Employee WHERE id = (:id)")
    fun getEmployeeById(id : Long) : Employee

    //즐겨찾기 카테고리 리스트
    @Query("SELECT * FROM FavCategory")
    fun getAllCategory() : MutableList<FavCategory>


    //카테고리 추가
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category : FavCategory)

    //카테고리 삭제
    @Query("DELETE FROM FavCategory WHERE id = (:id)")
    fun deleteCategory(id : Int)

    //카테고리 이름으로 불러오기
    @Query("SELECT * FROM FavCategory WHERE category = (:category)")
    fun getCategoryByName(category : String) : FavCategory?

    //Employee 카테고리 업데이트
    @Query("UPDATE Employee SET category = (:category) WHERE id = (:id)")
    fun updateEmployeeCategory(id : Long, category : String)

    //category 에 속한 데이터 개수
    @Query("SELECT COUNT(*) FROM Employee WHERE category = (:category)")
    fun getEmployeesInCategory(category : String) : Int
}


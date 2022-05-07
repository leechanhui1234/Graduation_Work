package com.example.aop.part4.graduation_work.Healths.dao

import androidx.room.*
import com.example.aop.part4.graduation_work.data.UserHealthInfo

@Dao
interface HealthDao{
    @Query("SELECT * FROM userhealthinfo")
    fun getAll(): List<UserHealthInfo>

    @Query("SELECT * FROM userhealthinfo WHERE userId=:userId")
    fun getData(userId: String): UserHealthInfo

    @Insert
    fun insertData(health: UserHealthInfo)

    @Query("DELETE FROM userhealthinfo")
    fun deleteAll()

    @Update
    fun updateData(health: UserHealthInfo)

    @Delete
    fun delete(health: UserHealthInfo)
}
package com.devsyncit.task1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserRecordDao {

    @Insert
    suspend fun insertRecord(answer: AnswerEntity)

    @Update
    suspend fun updateRecord(answer: AnswerEntity)

    @Delete
    suspend fun deleteRecord(answer: AnswerEntity)

    @Query("SELECT * FROM user_record")
    fun getAllRecords(): LiveData<List<AnswerEntity>>

}
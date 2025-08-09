package com.devsyncit.task1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserRecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecord(answer: AnswerEntity): Long

    @Query("UPDATE user_record SET answer = :answer WHERE question = :question")
    suspend fun updateRecord(question: String, answer: String)

    @Delete
    suspend fun deleteRecord(answer: AnswerEntity)

    @Query("DELETE FROM user_record WHERE id > (SELECT id FROM user_record WHERE question = :startQuestion)")
    suspend fun deleteFromSpecificField(startQuestion: String)

    @Query("DELETE FROM user_record")
    suspend fun clearAll()

    @Query("SELECT * FROM user_record")
    fun getAllRecords(): LiveData<List<AnswerEntity>>

}
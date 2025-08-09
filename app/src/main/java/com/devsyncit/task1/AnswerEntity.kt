package com.devsyncit.task1

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_record",
    indices = [Index(value = ["question"], unique = true)])
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    var question: String,
    var answer: String
)

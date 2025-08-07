package com.devsyncit.task1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_record")
data class AnswerEntity(
    @PrimaryKey
    var question: String,
    var answer: String
)

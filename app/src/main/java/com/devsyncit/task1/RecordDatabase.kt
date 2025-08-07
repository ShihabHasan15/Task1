package com.devsyncit.task1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AnswerEntity::class], version = 1)
abstract class RecordDatabase: RoomDatabase() {

    abstract fun recordDao(): UserRecordDao


    companion object{
        private var INSTANCE: RecordDatabase? = null

        fun getDbInstance(context: Context): RecordDatabase{

            synchronized(this){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RecordDatabase::class.java,
                        "user_db"
                    ).build()
                }
            }

            return INSTANCE!!
        }

    }


}
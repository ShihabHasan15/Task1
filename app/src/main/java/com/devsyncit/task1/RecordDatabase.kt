package com.devsyncit.task1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [AnswerEntity::class], version = 2)
abstract class RecordDatabase: RoomDatabase() {

    abstract fun recordDao(): UserRecordDao


    companion object{

        val migration_1_2 = object :Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE user_record ADD COLUMN id INTEGER")
            }
        }

        private var INSTANCE: RecordDatabase? = null

        fun getDbInstance(context: Context): RecordDatabase{

            synchronized(this){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RecordDatabase::class.java,
                        "user_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE!!
        }

    }


}
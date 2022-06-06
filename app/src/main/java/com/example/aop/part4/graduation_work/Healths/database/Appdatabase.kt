package com.example.aop.part4.graduation_work.Healths.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.aop.part4.graduation_work.Healths.dao.HealthDao
import com.example.aop.part4.graduation_work.data.UserHealthInfo

@Database(entities = [UserHealthInfo::class], version = 3)
abstract class Appdatabase: RoomDatabase() {
    abstract fun userHealthDao(): HealthDao
}

fun getDatabase(context: Context): Appdatabase{
    val MIGRATION_1_2 = object : Migration(2, 3){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE health_new(" +
                        "uid INTEGER, " +
                        "userId Text, " +
                        "pre_select Text, " +
                        "in_select Text, " +
                        "post_select Text, " +
                        "pre_url Text, " +
                        "in_url Text, " +
                        "post_url Text, " +
                        "date Text, " +
                        "record INTEGER, " +
                        "PRIMARY KEY(uid))"
            )

            database.execSQL("DROP TABLE userhealthinfo")
            database.execSQL("ALTER TABLE health_new RENAME TO userhealthinfo")
        }
    }

    val instance = Room.databaseBuilder(
        context.applicationContext,
        Appdatabase::class.java,
        "HealthDB"
    ).addMigrations(MIGRATION_1_2)
        .build()
    return instance
}
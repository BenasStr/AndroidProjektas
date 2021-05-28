package edu.ktu.ride.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Trick::class), version = 1)
abstract class TrickDatabase : RoomDatabase() {
    abstract fun trickDao(): TrickDAO

    companion object {
        @Volatile
        private var _instance: TrickDatabase? = null

        fun getInstance(context: Context): TrickDatabase {
            return _instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TrickDatabase::class.java,
                    "Trick"
                )
                    .createFromAsset("tricks-db.db")
                    .build()
                _instance = instance
                instance
            }
        }
    }
}
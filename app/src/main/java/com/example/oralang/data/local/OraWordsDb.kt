package com.example.oralang.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocalOraWord::class], version = 1, exportSchema = false)
@TypeConverters(UriConverters::class)
abstract class OraWordsDb: RoomDatabase() {
    abstract val dao: OraWordDao
}
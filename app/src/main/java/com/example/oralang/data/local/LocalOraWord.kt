package com.example.oralang.data.local

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "oraWord")
data class LocalOraWord(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val oraWord:String,
    val englishWord:String,
    val isFavoriteWord:Boolean=false,
    val wordAudio: Uri?,
)

class UriConverters {
    @TypeConverter
    fun fromString(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        return uri.toString()
    }
}
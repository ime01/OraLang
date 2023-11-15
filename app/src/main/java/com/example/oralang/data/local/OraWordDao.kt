package com.example.oralang.data.local

import androidx.room.*
import com.example.oralang.data.local.LocalOraWord
import com.example.oralang.domain.model.OraWord

@Dao
interface OraWordDao {
    @Query("SELECT * FROM oraWord")
    fun getAllOraWords(): List<LocalOraWord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllOraWord(oraWords: List<LocalOraWord>)

    @Update(entity = LocalOraWord::class)
    suspend fun updateOraWord(oraWord: LocalOraWord)

    @Update(entity = LocalOraWord::class)
    suspend fun updateAllOraWords(oraWord: List<LocalOraWord>)

    @Query("SELECT * FROM oraWord WHERE id = :id")
    suspend fun getOraWord(id: Int): LocalOraWord

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOraWord(oraWord: LocalOraWord): Long

    @Delete
    suspend fun deleteOraWord(oraWord:LocalOraWord)
}
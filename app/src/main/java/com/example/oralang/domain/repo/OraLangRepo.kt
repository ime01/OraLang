package com.example.oralang.domain.repo

import com.example.oralang.domain.model.OraWord

interface OraLangRepo {

    suspend fun getAllOraWords(): List<OraWord>
    suspend fun getAllOraWordsFromLocalCache(): List<OraWord>
    suspend fun addOraWord(oraWord: OraWord)
    suspend fun updateOraWord(oraWord: OraWord)
    suspend fun deleteOraWord(oraWord: OraWord)


}
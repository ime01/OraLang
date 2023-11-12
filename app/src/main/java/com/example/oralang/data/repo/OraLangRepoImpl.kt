package com.example.oralang.data.repo

import com.example.oralang.data.local.provideListOfOraWords
import com.example.oralang.domain.model.OraWord
import com.example.oralang.domain.repo.OraLangRepo

class OraLangRepoImpl():OraLangRepo {

    override suspend fun getAllOraWords(): List<OraWord> {
       return provideListOfOraWords()
    }

    override suspend fun getAllOraWordsFromLocalCache(): List<OraWord> {
        TODO("Not yet implemented")
    }

    override suspend fun addOraWord(oraWord: OraWord) {
        TODO("Not yet implemented")
    }

    override suspend fun updateOraWord(oraWord: OraWord) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOraWord(oraWord: OraWord) {
        TODO("Not yet implemented")
    }
}
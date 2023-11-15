package com.example.oralang.domain.usecase


import com.example.oralang.domain.model.OraWord
import com.example.oralang.domain.repo.OraLangRepo
import com.example.oralang.utils.INVALID_ORAWORD_EXCEPTION
import com.example.oralang.utils.InvalidOraWordException
import javax.inject.Inject


class GetOraWordsUseCase @Inject constructor(private val repo: OraLangRepo) {

     suspend fun getAllOraWords(): OraWordsUseCaseResult {

         var oraWords = repo.getAllOraWordsFromLocalCache()

         if(oraWords.isEmpty()){
             oraWords = repo.getAllOraWords()
         }

         return if (oraWords.isNotEmpty()){
             OraWordsUseCaseResult.Success(oraWords)
         }else OraWordsUseCaseResult.Error("No Ora Words Found")

    }


    suspend fun addOraWord(oraWord: OraWord){
        if(oraWord.englishWord.isBlank() || oraWord.oraWord.isBlank()){
            throw InvalidOraWordException(INVALID_ORAWORD_EXCEPTION)
        }
        repo.addOraWord(oraWord)
    }



    suspend fun updateOraWord(oraWord: OraWord){
        if(oraWord.englishWord.isBlank() || oraWord.oraWord.isBlank()){
            throw InvalidOraWordException(INVALID_ORAWORD_EXCEPTION)
        }
        repo.updateOraWord(oraWord)
    }


    suspend fun deleteOraWord(oraWord: OraWord){
        repo.deleteOraWord(oraWord)
    }

    suspend fun toggleIsFavoriteOraWord(oraWord: OraWord){
        repo.updateOraWord(oraWord.copy(isFavoriteWord = !oraWord.isFavoriteWord))
    }

    suspend fun getOraWordById(id: Int): OraWord? {
        return repo.getSingleOraWordById(id)
    }


}

sealed class OraWordsUseCaseResult {
    data class Success(val todoItems: List<OraWord>) : OraWordsUseCaseResult()
    data class Error(val message: String) : OraWordsUseCaseResult()
}
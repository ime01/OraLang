package com.example.oralang.domain.usecase

import com.example.oralang.data.local.provideListOfOraWords
import com.example.oralang.data.repo.OraLangRepoImpl
import com.example.oralang.domain.model.OraWord
import com.example.oralang.domain.repo.OraLangRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetOraWordsUseCase() {

    val oraRepo = OraLangRepoImpl()
     suspend fun getAllOraWords(): OraWordsUseCaseResult {

         return if (oraRepo.getAllOraWords().isNotEmpty()){
             OraWordsUseCaseResult.Success(oraRepo.getAllOraWords())
         }else OraWordsUseCaseResult.Error("No Ora Words Found")

    }


}

sealed class OraWordsUseCaseResult {
    data class Success(val todoItems: List<OraWord>) : OraWordsUseCaseResult()
    data class Error(val message: String) : OraWordsUseCaseResult()
}
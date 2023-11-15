package com.example.oralang.testutils

import com.example.oralang.presentation.orawordslist.OraWordsListState

fun getLoadingOraWordsState():OraWordsListState{
   return OraWordsListState(
       oraWords = listOf(),
       isLoading = true,
       error = null
   )
}
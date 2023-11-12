package com.example.oralang.presentation.orawordslist

import com.example.oralang.domain.model.OraWord

data class OraWordsListState(
    val oraWords: List<OraWord>,
    val isLoading: Boolean = true,
    val error : String? = null
)

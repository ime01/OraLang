package com.example.oralang.presentation.updateoraword

import com.example.oralang.domain.model.OraWord


data class OraWordUpdateState(
    val isEnglishHintVisible: Boolean = true,
    val isOraWordHintVisible: Boolean = true,
    val oraWord: OraWord = OraWord(
        id = 0,
        oraWord = "",
        englishWord = "",
        isFavoriteWord = false,
        wordAudio = null

    ),
    val isLoading: Boolean = true,
    val error: String? = null,
)

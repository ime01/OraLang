package com.example.oralang.domain.model

import android.net.Uri

data class OraWord(
    val oraWord:String,
    val englishWord:String,
    val isFavoriteWord:Boolean=false,
    val wordAudio:Uri?,

)

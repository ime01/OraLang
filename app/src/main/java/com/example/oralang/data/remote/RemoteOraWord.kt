package com.example.oralang.data.remote

import android.net.Uri

data class RemoteOraWord(
    val id: Int,
    val oraWord:String,
    val englishWord:String,
    val isFavoriteWord:Boolean=false,
    val wordAudio: Uri?,
    val wordAddedBy:String?=null,
)

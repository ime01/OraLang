package com.example.oralang.data.mapper

import com.example.oralang.data.local.LocalOraWord
import com.example.oralang.data.remote.RemoteOraWord
import com.example.oralang.domain.model.OraWord


fun OraWord.toLocalOraWord(): LocalOraWord {
    return LocalOraWord(
        id = id,
        oraWord = oraWord,
        englishWord = englishWord,
        isFavoriteWord = isFavoriteWord,
        wordAudio = wordAudio
    )
}

fun LocalOraWord.toOraWord(): OraWord {
    return OraWord(
        id = id,
        oraWord = oraWord,
        englishWord = englishWord,
        isFavoriteWord = isFavoriteWord,
        wordAudio = wordAudio
    )
}

fun LocalOraWord.toRemoteOraWord(): RemoteOraWord {
    return RemoteOraWord(
        id = id,
        oraWord = oraWord,
        englishWord = englishWord,
        isFavoriteWord = isFavoriteWord,
        wordAudio = wordAudio
    )
}

fun RemoteOraWord.LocalOraWord(): LocalOraWord {
    return LocalOraWord(
        id = id,
        oraWord = oraWord,
        englishWord = englishWord,
        isFavoriteWord = isFavoriteWord,
        wordAudio = wordAudio
    )
}

fun RemoteOraWord.toOraWord(): OraWord {
    return OraWord(
        id = id,
        oraWord = oraWord,
        englishWord = englishWord,
        isFavoriteWord = isFavoriteWord,
        wordAudio = wordAudio
    )
}

fun List<LocalOraWord>.toOraWordList(): List<OraWord>{
    return this.map{
        OraWord(
            id = it.id,
            oraWord = it.oraWord,
            englishWord = it.englishWord,
            isFavoriteWord = it.isFavoriteWord,
            wordAudio = it.wordAudio
        )
    }
}

fun List<RemoteOraWord>.toOraLocalWordList(): List<LocalOraWord>{
    return this.map{
        LocalOraWord(
            id = it.id,
            oraWord = it.oraWord,
            englishWord = it.englishWord,
            isFavoriteWord = it.isFavoriteWord,
            wordAudio = it.wordAudio
        )
    }
}


fun List<RemoteOraWord>.fromRemoteToOraWordList(): List<OraWord>{
    return this.map{
        OraWord(
            id = it.id,
            oraWord = it.oraWord,
            englishWord = it.englishWord,
            isFavoriteWord = it.isFavoriteWord,
            wordAudio = it.wordAudio
        )
    }
}



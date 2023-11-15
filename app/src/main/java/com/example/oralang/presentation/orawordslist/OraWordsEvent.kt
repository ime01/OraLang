package com.example.oralang.presentation.orawordslist

import com.example.oralang.domain.model.OraWord


sealed class OraWordsEvent {
    data class Delete(val oraWord: OraWord): OraWordsEvent()
    data class ToggleIsFavoriteOraWord(val oraWord: OraWord): OraWordsEvent()
    data class Edit(val oraWord: OraWord): OraWordsEvent()
    object UndoDelete: OraWordsEvent()
}

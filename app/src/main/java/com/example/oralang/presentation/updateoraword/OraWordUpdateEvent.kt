package com.example.oralang.presentation.updateoraword

import androidx.compose.ui.focus.FocusState

sealed class OraWordUpdateEvent {
    data class EnteredEnglishWord(val value: String) : OraWordUpdateEvent()
    data class ChangedTitleFocus(val focusState: FocusState) : OraWordUpdateEvent()
    data class EnteredOraWords(val value: String) : OraWordUpdateEvent()
    data class ChangedDescriptionFocus(val focusState: FocusState) : OraWordUpdateEvent()
    object Delete: OraWordUpdateEvent()
    object ToggleCompleted: OraWordUpdateEvent()
    object SaveTodo: OraWordUpdateEvent()
    object Back: OraWordUpdateEvent()
}

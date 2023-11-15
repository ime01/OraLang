package com.example.oralang.presentation.updateoraword

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oralang.di.IoDispatcher
import com.example.oralang.domain.usecase.GetOraWordsUseCase
import com.example.oralang.utils.ERROR_SAVING_ORAWORD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OraWordUpdateViewModel @Inject constructor(
    private val oraWordUseCases: GetOraWordsUseCase,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = mutableStateOf(OraWordUpdateState())
    val state: State<OraWordUpdateState> = _state

    private val errorHandler = CoroutineExceptionHandler {_, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    private var currentOraWordId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent{
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveOraWord: UiEvent()
        object Back: UiEvent()
    }

    init {
        savedStateHandle.get<Int>("oraWordId")?.let { id ->
            if(id != -1){
                viewModelScope.launch(dispatcher + errorHandler){
                    oraWordUseCases.getOraWordById(id)?.also { oraWord ->
                        currentOraWordId = id
                        _state.value = _state.value.copy(
                            oraWord = oraWord,
                            isLoading = false,
                            isEnglishHintVisible = oraWord.englishWord.isBlank(),
                            isOraWordHintVisible = oraWord.oraWord.isBlank()
                        )
                    }
                }
            }else{
                _state.value = _state.value.copy(
                    isLoading = false
                )
            }

        }
    }


    fun onEvent(event: OraWordUpdateEvent){
        when(event){
            OraWordUpdateEvent.Back -> {
                viewModelScope.launch (dispatcher + errorHandler){
                    _eventFlow.emit(UiEvent.Back)
                }
            }
            is OraWordUpdateEvent.ChangedDescriptionFocus -> {
                val shouldDescriptionHintBeVisible = !event.focusState.isFocused && _state.value.oraWord.englishWord.isBlank()
                _state.value = _state.value.copy(
                    isOraWordHintVisible = shouldDescriptionHintBeVisible
                )
            }
            is OraWordUpdateEvent.ChangedTitleFocus -> {
                val shouldTitleHintBeVisible = !event.focusState.isFocused && _state.value.oraWord.oraWord.isBlank()
                _state.value = _state.value.copy(
                    isEnglishHintVisible = shouldTitleHintBeVisible
                )
            }
            OraWordUpdateEvent.Delete -> {
                viewModelScope.launch(dispatcher + errorHandler){
                    if(currentOraWordId != null){
                        oraWordUseCases.deleteOraWord(_state.value.oraWord)
                    }
                    _eventFlow.emit(UiEvent.Back)
                }
            }
            is OraWordUpdateEvent.EnteredOraWords -> {
                _state.value = _state.value.copy(
                    oraWord = _state.value.oraWord.copy(
                        oraWord = event.value
                    )
                )
            }
            is OraWordUpdateEvent.EnteredEnglishWord -> {
                _state.value = _state.value.copy(
                    oraWord = _state.value.oraWord.copy(
                        englishWord = event.value
                    )
                )
            }
            OraWordUpdateEvent.SaveTodo -> {
                viewModelScope.launch (dispatcher + errorHandler){
                    try{
                        if(currentOraWordId != null){
                            oraWordUseCases.updateOraWord(_state.value.oraWord)
                        }else {
                            oraWordUseCases.addOraWord(_state.value.oraWord)
                        }
                        _eventFlow.emit(UiEvent.SaveOraWord)
                    }catch (e: Exception){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: ERROR_SAVING_ORAWORD
                            )
                        )
                    }
                }
            }
            OraWordUpdateEvent.ToggleCompleted -> {
                _state.value = _state.value.copy(
                    oraWord = _state.value.oraWord.copy(
                        isFavoriteWord = !_state.value.oraWord.isFavoriteWord
                    )
                )
            }
        }
    }


}













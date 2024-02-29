package com.example.oralang.presentation.orawordslist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oralang.di.IoDispatcher
import com.example.oralang.domain.model.OraWord
import com.example.oralang.domain.usecase.GetOraWordsUseCase
import com.example.oralang.domain.usecase.OraWordsUseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OraLangViewModel @Inject constructor(
    private val getOraWordsUseCase: GetOraWordsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _state = MutableStateFlow(OraWordsListState(listOf()))
    val state :StateFlow<OraWordsListState> = _state
    private var undoOraWordDeleted: OraWord? = null

    private val errorHandler = CoroutineExceptionHandler {_, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }


    fun getOraLangWords(){

        viewModelScope.launch(dispatcher + errorHandler) {

            val result = getOraWordsUseCase.getAllOraWords()

            when(result){

                is OraWordsUseCaseResult.Success->{

                 _state.value  =  _state.value.copy(
                        oraWords = result.todoItems,
                        isLoading = false)

                }

                is OraWordsUseCaseResult.Error->{

                 _state.value =  _state.value.copy(
                        error = result.message,
                        isLoading = false)
                    Log.d("Error","Error fetching Ora word")
                }
            }
        }
    }




    fun onEvent(event: OraWordsEvent){
        when(event){
            is OraWordsEvent.Delete -> {
                viewModelScope.launch (dispatcher + errorHandler){
                    getOraWordsUseCase.deleteOraWord(event.oraWord)
                    getOraLangWords()
                    undoOraWordDeleted = event.oraWord
                }
            }
            is OraWordsEvent.ToggleIsFavoriteOraWord -> {
                viewModelScope.launch (dispatcher + errorHandler){
                    getOraWordsUseCase.toggleIsFavoriteOraWord(oraWord = event.oraWord)
                    getOraLangWords()
                }
            }
            is OraWordsEvent.Edit -> {
                viewModelScope.launch (dispatcher + errorHandler){
                    getOraWordsUseCase.updateOraWord(oraWord = event.oraWord)
                    getOraLangWords()
                }
            }
            OraWordsEvent.UndoDelete -> {
                viewModelScope.launch (dispatcher + errorHandler){
                    getOraWordsUseCase.addOraWord(undoOraWordDeleted ?: return@launch)
                    undoOraWordDeleted = null
                    getOraLangWords()
                }
            }
        }
    }


}
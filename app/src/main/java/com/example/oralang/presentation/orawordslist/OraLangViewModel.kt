package com.example.oralang.presentation.orawordslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oralang.domain.usecase.GetOraWordsUseCase
import com.example.oralang.domain.usecase.OraWordsUseCaseResult
import kotlinx.coroutines.launch

class OraLangViewModel():ViewModel() {

    val getOraWordUseCase = GetOraWordsUseCase()

    private val _state = mutableStateOf(OraWordsListState(listOf()))
    val state :State<OraWordsListState> = _state




    fun getOraLangWords(){

        viewModelScope.launch {
            val result = getOraWordUseCase.getAllOraWords()

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

                }
            }
        }
    }


}
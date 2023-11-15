package com.example.oralang.utils

sealed class Screens(val route: String){
    object OraWordsListScreen: Screens(ORAWORDS_LIST_SCREEN)
    object OraWordUpdateScreen: Screens(ORAWORD_UPDATE_SCREEN)
}

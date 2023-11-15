package com.example.oralang.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.oralang.domain.model.OraWord

data class OraWordsItemColors(
    val backgroundColor: Color,
    val textColor: Color,
    val archiveIconColor: Color,
//    val checkColor: Color
)

@Composable
fun getOraWordsColors(oraWord: OraWord): OraWordsItemColors {
    return if(oraWord.isFavoriteWord){
        OraWordsItemColors(
            backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
            textColor = MaterialTheme.colorScheme.onSecondary,
            archiveIconColor = MaterialTheme.colorScheme.onSecondary,
//            checkColor = if(oraWord.isFavoriteWord) MaterialTheme.colorScheme.tertiaryContainer
//                else MaterialTheme.colorScheme.onSecondary
        )
    }else{
        OraWordsItemColors(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
            archiveIconColor = MaterialTheme.colorScheme.secondary,
            //checkColor = if(oraWord.isFavoriteWord) MaterialTheme.colorScheme.tertiaryContainer
//            else MaterialTheme.colorScheme.secondary
        )
    }
}
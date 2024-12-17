package com.example.oralang.presentation.orawordslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oralang.domain.model.OraWord
import com.example.oralang.presentation.components.ArchiveButton
import com.example.oralang.presentation.components.CompleteButton
import com.example.oralang.presentation.components.DeleteButton
import com.example.oralang.presentation.components.getOraWordsColors
import com.example.oralang.ui.theme.OraLangTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OraWordCard(
    oraWord: OraWord,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onCardClick: () -> Unit,
){
    val oraWordColors = getOraWordsColors(oraWord = oraWord)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        onClick = onCardClick,
        colors = CardDefaults.cardColors(containerColor = oraWordColors.backgroundColor)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
           // CompleteButton(onCompleteClick, oraWordColors.textColor, oraWord.isFavoriteWord)
            Text(
                text = oraWord.oraWord,
                modifier =Modifier
                    //.wrapContentSize()
                    .padding(8.dp)
                    .weight(0.8f),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = Bold,
                color = oraWordColors.textColor,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            //ArchiveButton(onArchiveClick, oraWordColors.archiveIconColor)
        }
        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
                Text(
                    text = oraWord.englishWord,
                    modifier =Modifier.padding(8.dp)
                        .weight(0.8f),
                    style = MaterialTheme.typography.bodyLarge,
                    color = oraWordColors.textColor,
                    fontSize = 14.sp,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )

                 DeleteButton(onDeleteClick)
            }

    }
}

@Preview
@Composable
fun TodoItemCardPreview(){
    OraLangTheme {
        OraWordCard(
            OraWord(
                id = 0,
                oraWord = "Ogbe",
                englishWord = "Hello",
                isFavoriteWord = false,
                wordAudio = null

            ),
            onDeleteClick = {},
            onCardClick = {},
            onArchiveClick = {},
            onCompleteClick = {}
        )
    }
}










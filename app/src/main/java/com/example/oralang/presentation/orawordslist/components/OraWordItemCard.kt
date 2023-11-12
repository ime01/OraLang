package com.example.oralang.presentation.orawordslist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oralang.domain.model.OraWord

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OraItemCard(
    oraWord: OraWord,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit,
){

    Card(
        modifier = modifier.fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onCardClick,
        colors = CardDefaults.cardColors(containerColor = Color.Gray)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
            //CompleteButton(onCompleteClick, todoColors.checkColor, todo.completed)
            Text(
                text = oraWord.englishWord,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = Bold,
                color = Color.Black,
                fontSize = 32.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row (
            verticalAlignment = Alignment.Top
        ){
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Top
            ){
                Text(
                    text = oraWord.oraWord,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Blue,
                    fontSize = 24.sp,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f)
                    .padding(end = 4.dp)
            ) {
//                ArchiveButton(onArchiveClick, todoColors.archiveIconColor)
//                DeleteButton(onDeleteClick)
            }
        }
    }
}

//@Preview
//@Composable
//fun TodoItemCardPreview(){
//    TodoTheme {
//        TodoItemCard(
//            TodoItem(
//                title = "Subscribe to my channel & like this video ",
//                description = "Keep learning Kotlin so that you can learn how to make really cool apps",
//                timestamp = 11234565,
//                completed = true,
//                archived = false,
//                id = 0
//            ),
//            onDeleteClick = {},
//            onCardClick = {},
//            onArchiveClick = {},
//            onCompleteClick = {}
//        )
//    }
//}










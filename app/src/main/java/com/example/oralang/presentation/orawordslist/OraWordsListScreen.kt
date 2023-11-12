package com.example.oralang.presentation.orawordslist

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.oralang.presentation.orawordslist.components.OraItemCard
import com.example.oralang.utils.toast


@Composable
fun OraWordsListScreen(viewModel: OraLangViewModel){

    val context = LocalContext.current

    val state = viewModel.state.value
    Log.d("LOCALDATA", "${state.oraWords}")


    LaunchedEffect(key1 = true){
        viewModel.getOraLangWords()
    }


    Column(
        modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .padding(top = 24.dp)
        ){

            items(state.oraWords){ oraWord->

                OraItemCard(oraWord){
                    toast(context, "${oraWord.oraWord} Clicked")
                }

            }

        }
    }




}
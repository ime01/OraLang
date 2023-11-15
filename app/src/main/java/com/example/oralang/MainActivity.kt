package com.example.oralang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oralang.presentation.orawordslist.OraLangViewModel
import com.example.oralang.presentation.orawordslist.OraWordsListScreen
import com.example.oralang.ui.theme.OraLangTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OraLangTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val oraLangViewModel:OraLangViewModel = hiltViewModel()
                    OraLangApp(oraLangViewModel)
                }
            }
        }
    }
}


@Composable
private fun OraLangApp(oraLangViewModel: OraLangViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "books"){
        composable(route = "books"){

            OraWordsListScreen(oraLangViewModel)
        }

    }
}

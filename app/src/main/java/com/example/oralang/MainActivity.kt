package com.example.oralang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.oralang.presentation.orawordslist.OraLangViewModel
import com.example.oralang.presentation.orawordslist.OraWordsListScreen
import com.example.oralang.presentation.updateoraword.OraWordUpdateScreen
import com.example.oralang.ui.theme.OraLangTheme
import com.example.oralang.utils.Screens
import com.example.oralang.utils.UPDATE_SCREEN_NAV_ARGUMENT_ID
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalMaterial3Api
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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OraLangApp(oraLangViewModel: OraLangViewModel) {
    val navController = rememberNavController()

    NavHost(navController, Screens.OraWordsListScreen.route){

        composable(route = Screens.OraWordsListScreen.route){

            OraWordsListScreen(navController,oraLangViewModel)
        }


        composable(route = Screens.OraWordUpdateScreen.route + "?$UPDATE_SCREEN_NAV_ARGUMENT_ID={$UPDATE_SCREEN_NAV_ARGUMENT_ID}",  arguments = listOf(
            navArgument(
                name = UPDATE_SCREEN_NAV_ARGUMENT_ID
            ){
                type = NavType.IntType
                defaultValue = -1
            }
        )){

            OraWordUpdateScreen(navController = navController)
        }

    }
}

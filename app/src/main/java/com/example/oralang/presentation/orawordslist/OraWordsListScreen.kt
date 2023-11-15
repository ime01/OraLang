package com.example.oralang.presentation.orawordslist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.oralang.R
import com.example.oralang.presentation.components.OraItemCard
import com.example.oralang.utils.Screens
import com.example.oralang.utils.toast
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OraWordsListScreen(navController: NavController, viewModel: OraLangViewModel = hiltViewModel()){

    val context = LocalContext.current

    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    Log.d("LOCALDATA", "${state.oraWords}")


    LaunchedEffect(key1 = true){
        viewModel.getOraLangWords()
    }


        Scaffold(
            floatingActionButton = {
                androidx.compose.material3.FloatingActionButton(
                    onClick = {
                        navController.navigate(Screens.OraWordUpdateScreen.route)
                    },
                    shape = androidx.compose.foundation.shape.CircleShape,
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Add,
                        contentDescription = "Add Ora Word",
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
                    )
                }
            },

            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            Box(
                contentAlignment = androidx.compose.ui.Alignment.TopStart,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = androidx.compose.material3.MaterialTheme.colorScheme.background)
            ) {

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = androidx.compose.ui.Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        items(state.oraWords) { oraWord ->

                            OraItemCard(oraWord){
                                toast(context, "${oraWord.oraWord} Clicked")
                            }
                        }
                    }
                }
                if (state.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            Modifier.semantics {
                                this.contentDescription = "Loading Ora Words"
                            }
                        )
                    }
                }
                if (state.error != null) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.error,
                            fontSize = 30.sp,
                            lineHeight = 36.sp
                        )
                    }
                }
            }
        }

}
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.oralang.utils.FLOATING_ACTION_BUTTON
import com.example.oralang.utils.LOADING_ORA_WORDS
import com.example.oralang.utils.LOADING_SCREEN_TAG
import com.example.oralang.utils.Screens
import com.example.oralang.utils.UPDATE_SCREEN_NAV_ARGUMENT_ID
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OraWordsListScreen(navController: NavController, viewModel: OraLangViewModel = hiltViewModel()){

    val context = LocalContext.current

    val state = viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    Log.d("LOCALDATA", "${state.value.oraWords}")


    LaunchedEffect(key1 = true){
        viewModel.getOraLangWords()
    }


        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier
                        .testTag(FLOATING_ACTION_BUTTON),
                    onClick = {
                        navController.navigate(Screens.OraWordUpdateScreen.route)
                    },
                    shape = androidx.compose.foundation.shape.CircleShape,
                    containerColor = colorScheme.primary
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Add,
                        contentDescription = "Add Ora Word",
                        tint = colorScheme.onPrimary
                    )
                }
            },

            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorScheme.background)
            ) {

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        items(state.value.oraWords) { oraWord ->

                            OraWordCard(
                                oraWord = oraWord,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                onDeleteClick = {
                                    viewModel.onEvent(OraWordsEvent.Delete(oraWord))
                                    scope.launch {
                                        val undo = snackbarHostState.showSnackbar(
                                            message = "Ora Word Deleted",
                                            actionLabel = "Undo"
                                        )
                                        if(undo == SnackbarResult.ActionPerformed){
                                            viewModel.onEvent(OraWordsEvent.UndoDelete)
                                        }
                                    }
                                },
                                onCompleteClick = {
                                    viewModel.onEvent(OraWordsEvent.ToggleIsFavoriteOraWord(oraWord))
                                },
                                onArchiveClick = {
                                    //viewModel.onEvent(OraWordsEvent.ToggleArchived(oraWord))
                                },
                                onCardClick = {
                                    navController.navigate(
                                        Screens.OraWordUpdateScreen.route + "?$UPDATE_SCREEN_NAV_ARGUMENT_ID=${oraWord.id}"
                                    )
                                }
                            )
                        }
                    }
                }
                if (state.value.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .testTag(LOADING_SCREEN_TAG),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            Modifier.semantics {
                                this.contentDescription = LOADING_ORA_WORDS
                            }
                        )
                    }
                }
                if (state.value.error != null) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.value.error.orEmpty(),
                            fontSize = 30.sp,
                            lineHeight = 36.sp
                        )
                    }
                }
            }
        }

}
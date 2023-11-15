package com.example.oralang.presentation.updateoraword

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.oralang.R
import com.example.oralang.presentation.components.ArchiveButton
import com.example.oralang.presentation.components.CompleteButton
import com.example.oralang.presentation.components.DeleteButton
import com.example.oralang.presentation.components.HintTextField
import com.example.oralang.presentation.components.getOraWordsColors
import com.example.oralang.utils.toast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OraWordUpdateScreen(
    navController: NavController,
    viewModel: OraWordUpdateViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val todoColors = getOraWordsColors(oraWord = state.oraWord)

    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    val horizontalPadding = 16.dp
    val verticalPadding = if(isPortrait) 16.dp else 2.dp

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                OraWordUpdateViewModel.UiEvent.Back -> {
                    navController.navigateUp()
                }

                OraWordUpdateViewModel.UiEvent.SaveOraWord -> {
                    navController.navigateUp()
                }

                is OraWordUpdateViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            if (!isPortrait) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(OraWordUpdateEvent.SaveTodo)
                    },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save Ora Word",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },

        bottomBar = {
            if (isPortrait) {
                BottomAppBar(
                    actions = {
                        CompleteButton(
                            onCompleteClick = {
                                viewModel.onEvent(OraWordUpdateEvent.ToggleCompleted)
                            },
                            color = todoColors.textColor,
                            completed = state.oraWord.englishWord.isNotEmpty()
                        )
                        ArchiveButton(
                            onArchiveClick = {
                                toast(context, "Arcive Button Clicked")
                                //viewModel.onEvent(OraWordUpdateEvent.ToggleArchived)
                            }
                        )
                        DeleteButton(
                            onDeleteClick = {
                                scope.launch {
                                    val confirm = snackbarHostState.showSnackbar(
                                        message = "Are you sure?",
                                        actionLabel = "Yes"
                                    )
                                    if (confirm == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(OraWordUpdateEvent.Delete)
                                    }
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                viewModel.onEvent(OraWordUpdateEvent.SaveTodo)
                            },
                            shape = CircleShape,
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = stringResource(R.string.save_oraword),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ) { padding ->

        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .background(color = todoColors.backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxSize()
            ){
                HintTextField(
                    text = state.oraWord.englishWord,
                    hint = stringResource(R.string.title),
                    textColor = todoColors.textColor,
                    onValueChange = {
                         viewModel.onEvent(OraWordUpdateEvent.EnteredEnglishWord(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(OraWordUpdateEvent.ChangedTitleFocus(it))
                    },
                    isHintVisible = state.isEnglishHintVisible,
                    singleLine = true,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(
                        horizontal = horizontalPadding,
                        vertical = verticalPadding
                    )
                )
                Spacer(modifier = Modifier.height(verticalPadding))
                HintTextField(
                    text = state.oraWord.oraWord,
                    hint = stringResource(R.string.description),
                    textColor = todoColors.textColor,
                    onValueChange = {
                        viewModel.onEvent(OraWordUpdateEvent.EnteredOraWords(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(OraWordUpdateEvent.ChangedDescriptionFocus(it))
                    },
                    isHintVisible = state.isOraWordHintVisible,
                    singleLine = false,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            horizontal = horizontalPadding,
                            vertical = verticalPadding
                        )
                )
            }
        }

    }
}










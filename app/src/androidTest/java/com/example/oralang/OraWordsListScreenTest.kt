package com.example.oralang

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.oralang.presentation.orawordslist.OraLangViewModel
import com.example.oralang.presentation.orawordslist.OraWordsListScreen
import com.example.oralang.presentation.orawordslist.OraWordsListState
import com.example.oralang.testutils.getLoadingOraWordsState
import com.example.oralang.ui.theme.OraLangTheme
import com.example.oralang.utils.LOADING_ORA_WORDS
import com.example.oralang.utils.LOADING_SCREEN_TAG
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OraWordsListScreenTest {

    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    private val mockNavigationController = mockk<NavHostController>()
    //private val oraLangViewModel = mockk<OraLangViewModel>()

    @MockK
    private lateinit var oraLangViewModel :OraLangViewModel



    @Before
    fun setUp(){

        MockKAnnotations.init(this)
        oraLangViewModel = mockk()

        every { oraLangViewModel.getOraLangWords() } just runs
        every { oraLangViewModel.state } returns MutableStateFlow(OraWordsListState(emptyList()))


    }


    @Test
    fun givenInOraWordsListScreen_whenListIsEmpty_showLoadingIndicator(){

        testRule.setContent {
            OraWordsListScreen(navController = mockNavigationController, oraLangViewModel)
        }

        testRule.onNodeWithTag(LOADING_SCREEN_TAG).assertIsDisplayed()

    }






}
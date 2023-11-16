package com.example.oralang

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.oralang.data.local.provideListOfRemoteOraWords
import com.example.oralang.data.mapper.fromRemoteToOraWordList
import com.example.oralang.presentation.orawordslist.OraLangViewModel
import com.example.oralang.presentation.orawordslist.OraWordsListScreen
import com.example.oralang.presentation.orawordslist.OraWordsListState
import com.example.oralang.presentation.updateoraword.OraWordUpdateScreen
import com.example.oralang.utils.FLOATING_ACTION_BUTTON
import com.example.oralang.utils.LOADING_SCREEN_TAG
import com.example.oralang.utils.Screens
import com.example.oralang.utils.UPDATE_ORA_WORD_SCREEN_TAG
import com.example.oralang.utils.UPDATE_SCREEN_NAV_ARGUMENT_ID
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OraWordsListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockNavigationController = mockk<NavHostController>()
    private val oraLangViewModel = mockk<OraLangViewModel>()


//    @MockK()
//    private lateinit var oraLangViewModel :OraLangViewModel



    @Before
    fun setUp(){

//        MockKAnnotations.init(this)
//        oraLangViewModel = mockk()

        every { oraLangViewModel.getOraLangWords() } just runs
        every { oraLangViewModel.state } returns MutableStateFlow(OraWordsListState(emptyList()))

    }


    @Test
    fun givenInOraWordsListScreen_whenListIsEmpty_showLoadingIndicator(){

        composeTestRule.setContent {
            OraWordsListScreen(navController = mockNavigationController, oraLangViewModel)
        }

        composeTestRule.onNodeWithTag(LOADING_SCREEN_TAG).assertIsDisplayed()

    }


    @Test
    fun givenInOraWordsListScreen_whenStateHasValue_contentIsRendered(){

        every { oraLangViewModel.state } returns MutableStateFlow(
            OraWordsListState(oraWords = provideListOfRemoteOraWords().fromRemoteToOraWordList(),
                isLoading = false)
        )

        composeTestRule.setContent {
            OraWordsListScreen(navController = mockNavigationController, oraLangViewModel)
        }


        composeTestRule.onNodeWithText(provideListOfRemoteOraWords().first().oraWord).assertIsDisplayed()
        composeTestRule.onNodeWithText(provideListOfRemoteOraWords()[5].oraWord).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LOADING_SCREEN_TAG).assertDoesNotExist()
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun givenInOraWordsListScreen_whenFabIsClicked_OpenUpdateWordScreen(){

        val oraWord = provideListOfRemoteOraWords()[3]

        every { oraLangViewModel.state } returns MutableStateFlow(
            OraWordsListState(oraWords = provideListOfRemoteOraWords().fromRemoteToOraWordList(),
                isLoading = false)
        )
        every { mockNavigationController.navigate(Screens.OraWordUpdateScreen.route + "?$UPDATE_SCREEN_NAV_ARGUMENT_ID=${oraWord.id}") } just runs

        composeTestRule.setContent {
            OraWordsListScreen(navController = mockNavigationController, oraLangViewModel)
        }


        composeTestRule.onNodeWithText(oraWord.oraWord).performClick()
        verify {mockNavigationController.navigate(Screens.OraWordUpdateScreen.route + "?$UPDATE_SCREEN_NAV_ARGUMENT_ID=${oraWord.id}") }
    }






}
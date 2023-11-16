package com.example.oralang

import android.content.Context
import com.example.oralang.data.local.LocalOraWord
import com.example.oralang.data.local.OraWordDao
import com.example.oralang.data.local.OraWordsDb
import com.example.oralang.data.local.provideListOfRemoteOraWords
import com.example.oralang.data.mapper.fromRemoteToOraWordList
import com.example.oralang.data.remote.OraWordsApi
import com.example.oralang.data.remote.RemoteOraWord
import com.example.oralang.data.repo.OraLangRepoImpl
import com.example.oralang.domain.model.OraWord
import com.example.oralang.domain.repo.OraLangRepo
import com.example.oralang.domain.usecase.GetOraWordsUseCase
import com.example.oralang.domain.usecase.OraWordsUseCaseResult
import com.example.oralang.presentation.orawordslist.OraLangViewModel
import com.example.oralang.presentation.orawordslist.OraWordsEvent
import com.example.oralang.presentation.orawordslist.OraWordsListState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.runs
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner::class)
class OraLanViewModelTest {


    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private val oraWordService = mockkClass(OraWordsApi::class)
    private val oraWordDao = spyk<OraWordDao>()
    // private val oraLangRepo = mockkClass(OraLangRepoImpl::class)
    private val oraLangRepo = spyk(OraLangRepoImpl(oraWordDao, oraWordService, coroutineRule.testDispatcher))
    private val getOraWordsUseCase = spyk(GetOraWordsUseCase(oraLangRepo))




    @Test
    fun givenInInitialState_whenNoData_confirmNoDataStateIsProduced() = runTest(coroutineRule.testDispatcher){

        val oraLangViewModel = OraLangViewModel(getOraWordsUseCase, coroutineRule.testDispatcher)
        val initialState = oraLangViewModel.state.value
        assert(
            initialState == OraWordsListState(
                oraWords = emptyList(),
                isLoading = true,
                error = null
            )
        )
    }




    @Test
    fun givenInInitialState_whenEdit_shouldEdit() = runTest(coroutineRule.testDispatcher) {
        //Given
        val word = OraWord(
            id = 7,
            oraWord = "Owha",
            englishWord = "House",
            isFavoriteWord = false,
            wordAudio = null

        )
        val oraEvent = OraWordsEvent.Edit(word)

        coEvery { oraLangRepo.updateOraWord(word) } just runs

        //When
        val oraLangViewModel = OraLangViewModel(getOraWordsUseCase, coroutineRule.testDispatcher)
        oraLangViewModel.onEvent(oraEvent)

        //Then
        coVerify { oraLangRepo.updateOraWord(word) }
    }


    @Test
    fun givenInInitialState_whenDelete_shouldDelete() = runTest(coroutineRule.testDispatcher) {
        //Given
        val word = OraWord(
                id = 3,
                oraWord = "",
                englishWord = "",
                isFavoriteWord = false,
                wordAudio = null

            )
        val oraEvent = OraWordsEvent.Delete(word)

        coEvery { oraLangRepo.deleteOraWord(word) } just runs

        //When
        val oraLangViewModel = OraLangViewModel(getOraWordsUseCase, coroutineRule.testDispatcher)
        oraLangViewModel.onEvent(oraEvent)

        //Then
        coVerify { oraLangRepo.deleteOraWord(word) }
    }


    @Test
    fun givenInInitialState_WhenDataIsReturned_ConfirmStateWithDataIsProduced() = runTest(coroutineRule.testDispatcher) {
        //advanceUntilIdle()
        val oraLangViewModel = OraLangViewModel(getOraWordsUseCase, coroutineRule.testDispatcher)

        coEvery { oraLangViewModel.getOraLangWords() } just runs

        coEvery { oraLangViewModel.state } returns MutableStateFlow(OraWordsListState(provideListOfRemoteOraWords().fromRemoteToOraWordList()))


        val currentState = oraLangViewModel.state.value
        assert(
            currentState == OraWordsListState(
                oraWords = provideListOfRemoteOraWords().fromRemoteToOraWordList(),
                isLoading = false,
                error = null
            )
        )

    }



    @Test
    fun givenInInitialState_whenErrorReturn_shouldEmit_Error () = runTest(coroutineRule.testDispatcher) {

//
        //val mockResponseState = mockk<OraWordsUseCaseResult>()
        //Given
        coEvery { getOraWordsUseCase.getAllOraWords() } returns OraWordsUseCaseResult.Error("Failed to get OraWords")

        //When
        val oraLangViewModel = OraLangViewModel(getOraWordsUseCase, coroutineRule.testDispatcher)
        oraLangViewModel.getOraLangWords()
        val result = oraLangViewModel.state.value

        val oraWordsListState = OraWordsListState(
            oraWords = listOf(),
            isLoading = false,
            error = "Failed to get OraWords"
        )
        //Then
        assertEquals(oraWordsListState.error, result.error)
    }


}
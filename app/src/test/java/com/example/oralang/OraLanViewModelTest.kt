package com.example.oralang

import android.content.Context
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
import com.example.oralang.presentation.orawordslist.OraLangViewModel
import com.example.oralang.presentation.orawordslist.OraWordsEvent
import com.example.oralang.presentation.orawordslist.OraWordsListState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

//@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner::class)
class OraLanViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)


    @MockK
    lateinit var context: Context


    @MockK
    lateinit var oraWordDb: OraWordsDb
    lateinit var oraWordDao: OraWordDao
    lateinit var apiService: OraWordsApi
    lateinit var repository: OraLangRepoImpl
    lateinit var getOraWordsUseCase: GetOraWordsUseCase
    lateinit var oraLangViewModel: OraLangViewModel



    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        context = mockk(relaxed = true)
        oraWordDb = mockk()
        oraWordDao = mockk()
        apiService = mockk()
        repository = mockk()
        //oraWordDb = spyk(OraWordsDb())
        //oraWordDao = spyk(oraWordDao())
        repository = spyk(OraLangRepoImpl(oraWordDao, apiService, dispatcher))
        getOraWordsUseCase = spyk(GetOraWordsUseCase(repository))
        oraLangViewModel = spyk(OraLangViewModel(getOraWordsUseCase, dispatcher))
    }




    @Test
    fun givenInInitialState_whenNoData_confirmNoDataStateIsProduced() = scope.runTest{

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
    fun givenInInitialState_WhenDataIsReturned_ConfirmStateWithDataIsProduced() = scope.runTest {
        //advanceUntilIdle()

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
    fun givenAnOraWord_whenOnDeleteEvent_thenDeleteOraWordIsCalled() = runBlocking {

            val oraWord = OraWord(oraWord = "owha", englishWord = "house", isFavoriteWord = false, wordAudio = null, id = 5)
            val deleteOraWord = OraWordsEvent.Delete(oraWord)

            coEvery { repository.deleteOraWord(any()) } just runs
            oraLangViewModel.onEvent(deleteOraWord)

            coVerify { repository.deleteOraWord(oraWord) }
        }


}
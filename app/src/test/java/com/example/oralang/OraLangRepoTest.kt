package com.example.oralang

import com.example.oralang.data.local.LocalOraWord
import com.example.oralang.data.local.OraWordDao
import com.example.oralang.data.local.provideListOfRemoteOraWords
import com.example.oralang.data.mapper.LocalOraWord
import com.example.oralang.data.mapper.fromOraWordsToLocalOraWordList
import com.example.oralang.data.mapper.toLocalOraWord
import com.example.oralang.data.mapper.toOraLocalWordList
import com.example.oralang.data.mapper.toOraWord
import com.example.oralang.data.remote.OraWordsApi
import com.example.oralang.data.remote.RemoteOraWord
import com.example.oralang.data.repo.OraLangRepoImpl
import com.example.oralang.domain.model.OraWord
import com.example.oralang.domain.repo.OraLangRepo
import com.example.oralang.presentation.orawordslist.OraWordsListState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockkClass
import io.mockk.runs
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OraLangRepoTest {

    private val oraWordService = mockkClass(OraWordsApi::class)
    private val oraWordDao = spyk<OraWordDao>()


    @get:Rule
    val coroutineRule = MainDispatcherRule()


    @Test
    fun givenInInitialState_WhenOraWordDelete_ConfirmDeleted() = runTest(coroutineRule.testDispatcher) {
        //Given
        val oraWords = listOf(OraWord(oraWord = "owha", englishWord = "house", isFavoriteWord = false, wordAudio = null, id = 5),
        OraWord(oraWord = "owha", englishWord = "house", isFavoriteWord = false, wordAudio = null, id = 6),
        OraWord(oraWord = "owha", englishWord = "house", isFavoriteWord = false, wordAudio = null, id = 7))

        coEvery { oraWordDao.addAllOraWord(oraWords.fromOraWordsToLocalOraWordList()) } returns Unit
        coEvery { oraWordDao.deleteOraWord(oraWords[1].toLocalOraWord()) } returns Unit
        coEvery { oraWordDao.getAllOraWords() } returns oraWords.fromOraWordsToLocalOraWordList().drop(1)

        //When
        val oraLangRepoImpl = OraLangRepoImpl(oraWordDao, oraWordService, coroutineRule.testDispatcher)
        oraLangRepoImpl.deleteOraWord(oraWords.first())

        //Then
        assertEquals(2, oraWordDao.getAllOraWords().size)
    }



    @Test
    fun givenInInitialState_WhenNewWordAdded_ConfirmWordReturnedMatches() = runTest(coroutineRule.testDispatcher) {
        //Given
        val oraWords = listOf(OraWord(oraWord = "owha", englishWord = "house", isFavoriteWord = false, wordAudio = null, id = 5),
            OraWord(oraWord = "owha", englishWord = "house", isFavoriteWord = false, wordAudio = null, id = 5))

        coEvery { oraWordDao.addAllOraWord(oraWords.fromOraWordsToLocalOraWordList()) } just runs
        coEvery { oraWordDao.getAllOraWords() } returns provideListOfRemoteOraWords().toOraLocalWordList()

        //When
        val oraLangRepoImpl = OraLangRepoImpl(oraWordDao, oraWordService, coroutineRule.testDispatcher)
        val result = oraLangRepoImpl.getAllOraWords()

        //Then
        assertEquals(result[4], oraWords.first())
    }



    @Test
    fun givenInInitialState_WhenOraWordUpdate_ConfirmUpdate() = runTest(coroutineRule.testDispatcher) {
        //Given
        val oraWords = listOf(OraWord(oraWord = "owha", englishWord = "house", isFavoriteWord = false, wordAudio = null, id = 5),
            OraWord(oraWord = "owha", englishWord = "house", isFavoriteWord = false, wordAudio = null, id = 5))

        val updatedOraWord = OraWord(oraWord = "owha2", englishWord = "house2", isFavoriteWord = false, wordAudio = null, id = 5)

        coEvery { oraWordDao.addAllOraWord(oraWords.fromOraWordsToLocalOraWordList()) } returns Unit
        coEvery { oraWordDao.updateOraWord(updatedOraWord.toLocalOraWord()) } returns Unit
        coEvery { oraWordDao.getAllOraWords() } returns listOf(oraWords.fromOraWordsToLocalOraWordList()[1].copy(
            oraWord = "owha2",
            englishWord = "house2",
            isFavoriteWord = false,
            wordAudio = null,
            id = 5))

        //When
        val oraLangRepoImpl = OraLangRepoImpl(oraWordDao, oraWordService, coroutineRule.testDispatcher)
        oraLangRepoImpl.updateOraWord(updatedOraWord)

        //Then
        assertEquals(updatedOraWord.oraWord, oraWordDao.getAllOraWords()[0].oraWord)
    }



    //    @Test
//    fun givenInInitialState_WhenServiceAndDatabaseReturnsData_ConfirmStateWithDataIsProduced() =
//        runTest(coroutineRule.testDispatcher) {
//            //Given
//
//            coEvery { oraWordService.getOraWords () } returns provideListOfRemoteOraWords()
//
//            coEvery { oraWordDao.addAllOraWord (provideListOfRemoteOraWords().toOraLocalWordList())
//            } returns flowOf(provideListOfRemoteOraWords.toOraLocalWordList())
//
//            //When
//            val oraLangRepo = OraLangRepoImpl(oraWordDao, oraWordService, dispatcher)
//            val result = marsRoverPhotoRepo.getMarsRoverPhoto("perseverance", "0").toList()
//
//            //Then
//            val expectedResult = OraWordsListState(
//                roverPhotoUiModelList = listOf(
//                    RoverPhotoUiModel(
//                        id = 2,
//                        roverName = "Perseverance",
//                        imgSrc = "https://example.com/photo1",
//                        sol = "20",
//                        earthDate = "2022-07-02",
//                        cameraFullName = "Camera One",
//                        isSaved = true
//                    ),
//                    RoverPhotoUiModel(
//                        id = 4,
//                        roverName = "Perseverance",
//                        imgSrc = "https://example.com/photo2",
//                        sol = "20",
//                        earthDate = "2022-07-02",
//                        cameraFullName = "Camera Two",
//                        isSaved = false
//                    )
//                )
//            )
//            assertEquals(1, result.size)
//            assertEquals(expectedResult, result[0])
//        }







}
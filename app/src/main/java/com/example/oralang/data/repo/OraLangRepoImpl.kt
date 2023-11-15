package com.example.oralang.data.repo

import android.util.Log
import com.example.oralang.data.local.OraWordDao
import com.example.oralang.data.local.provideListOfRemoteOraWords
import com.example.oralang.data.mapper.toLocalOraWord
import com.example.oralang.data.mapper.toOraLocalWordList
import com.example.oralang.data.mapper.toOraWord
import com.example.oralang.data.mapper.toOraWordList
import com.example.oralang.data.remote.OraWordsApi
import com.example.oralang.di.IoDispatcher
import com.example.oralang.domain.model.OraWord
import com.example.oralang.domain.repo.OraLangRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class OraLangRepoImpl ( private val dao: OraWordDao,  private val api: OraWordsApi, @IoDispatcher private val dispatcher: CoroutineDispatcher):OraLangRepo {

    override suspend fun getAllOraWords(): List<OraWord> {
        getAllOraWordsFromRemote()
       return dao.getAllOraWords().toOraWordList()
    }


    override suspend fun getAllOraWordsFromLocalCache(): List<OraWord> {
        return dao.getAllOraWords().toOraWordList()
    }

    override suspend fun getAllOraWordsFromRemote(){
        return withContext(dispatcher){
            try {
                refreshRoomCache()
            }catch (e: Exception){
                when(e){
                    is UnknownHostException, is ConnectException, is HttpException -> {
                        Log.e("HTTP","Error: No data from Remote")
                        if(isCacheEmpty()){
                            Log.e("Cache","Error: No data from local Room cache")
                            throw Exception("Error: Device offline and\nno data from local Room cache")
                        }
                    }else -> throw e
                }
            }
        }

    }

    override suspend fun addOraWord(oraWord: OraWord) {
        val newId = dao.addOraWord(oraWord.toLocalOraWord())
        //val id = newId.toInt()
//        val url = "todo/$id.json"
//        api.addTodo(url, todo.toRemoteTodoItem().copy(id = id))
    }

    override suspend fun updateOraWord(oraWord: OraWord) {
        dao.addOraWord(oraWord.toLocalOraWord())
        //api.updateTodoItem(todo.id, todo.toRemoteTodoItem())
    }

    override suspend fun deleteOraWord(oraWord: OraWord) {
//        try{
//            val response = api.deleteTodo(todo.id)
//            if(response.isSuccessful){
//                Log.i("API_DELETE","Response Successful")
//            }else{
//                Log.i("API_DELETE","Response Unsuccessful")
//                Log.i("API_DELETE",response.message())
//            }
//        }catch (e: Exception){
//            when(e){
//                is UnknownHostException, is ConnectException, is HttpException -> {
//                    Log.e("HTTP", "Error: Could not delete")
//                }else -> throw e
//            }
//        }
        dao.deleteOraWord(oraWord.toLocalOraWord())
    }


    override suspend fun getSingleOraWordById(id: Int): OraWord? {
        return dao.getOraWord(id).toOraWord()
    }






    private suspend fun refreshRoomCache(){
        //val remoteBooks = api.getOraWords()
        val remoteBooks = provideListOfRemoteOraWords()
        dao.addAllOraWord(remoteBooks.toOraLocalWordList())
    }

    private fun isCacheEmpty(): Boolean {
        var empty = true
        if(dao.getAllOraWords().isNotEmpty()) empty = false
        return empty
    }
}
package com.example.oralang.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface OraWordsApi {
    @GET("books.json")
    suspend fun getOraWords(): List<RemoteOraWord>

    @GET("books.json?orderBy=\"r_id\"")
    suspend fun getOraWord(@Query("equalTo") id: Int): Map<String, RemoteOraWord>
}
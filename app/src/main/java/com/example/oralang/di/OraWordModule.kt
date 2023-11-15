package com.example.oralang.di

import android.content.Context
import androidx.room.Room
import com.example.oralang.data.local.OraWordDao
import com.example.oralang.data.local.OraWordsDb
import com.example.oralang.data.remote.OraWordsApi
import com.example.oralang.data.repo.OraLangRepoImpl
import com.example.oralang.domain.repo.OraLangRepo
import com.example.oralang.utils.ORAWORDS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OraWordModule {
    @Provides
    @Singleton
    fun providesRoomDao(database: OraWordsDb): OraWordDao {
        return database.dao
    }

    @Singleton
    @Provides
    fun providesRoomDb(
        @ApplicationContext appContext: Context
    ): OraWordsDb {
        return Room.databaseBuilder(
            appContext.applicationContext,
            OraWordsDb::class.java,
            ORAWORDS_DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://booklist-6d5b2-default-rtdb.firebaseio.com/")
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofitApi(retrofit: Retrofit): OraWordsApi {
        return retrofit.create(OraWordsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesOraWordRepo(db: OraWordsDb, api:OraWordsApi, @IoDispatcher dispatcher: CoroutineDispatcher): OraLangRepo {
        return OraLangRepoImpl(db.dao, api, dispatcher)
    }
}
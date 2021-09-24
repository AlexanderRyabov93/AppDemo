package ru.alexapps.personaldictionary.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alexapps.personaldictionary.BuildConfig
import ru.alexapps.personaldictionary.data.datasources.BaseDataSource
import ru.alexapps.personaldictionary.data.datasources.RapidApiWordsDataSource
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.data.network.RapidApiWordsService
import ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi.RapidApiWord
import ru.alexapps.personaldictionary.utils.Mapper
import ru.alexapps.personaldictionary.utils.RapidApiWordToWords
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSourceRapidApi(
        rapidApiWordsService: RapidApiWordsService,
        mapper: Mapper<RapidApiWord, List<Word>>
    ): BaseDataSource {
        return RapidApiWordsDataSource(rapidApiWordsService, mapper)
    }

    @Singleton
    @Provides
    fun provideRapidApiService(retrofit: Retrofit): RapidApiWordsService {
        return retrofit.create(RapidApiWordsService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitForRapidApi(): Retrofit {
        val baseUrl = "https://${BuildConfig.RAPID_API_HOST}/"
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {
    @Binds
    abstract fun bindMapper(mapper: RapidApiWordToWords): Mapper<RapidApiWord, List<@JvmSuppressWildcards Word>>
}
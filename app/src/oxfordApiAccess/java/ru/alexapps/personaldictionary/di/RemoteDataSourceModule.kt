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
import ru.alexapps.personaldictionary.data.datasources.OxfordDictionaryDataSource
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.data.network.OxfordApiService
import ru.alexapps.personaldictionary.data.network.retrofitmodels.OxfordWord
import ru.alexapps.personaldictionary.utils.Mapper
import ru.alexapps.personaldictionary.utils.OxfordWordToWordMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSourceOxford(oxfordApiService: OxfordApiService, mapper: Mapper<OxfordWord, List<Word>>): BaseDataSource {
        return OxfordDictionaryDataSource(oxfordApiService, mapper)
    }



    @Singleton
    @Provides
    fun provideOxfordService(retrofit: Retrofit): OxfordApiService {
        return retrofit.create(OxfordApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitForOxfordApi(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {
    @Binds
    abstract fun bindMapper(mapper: OxfordWordToWordMapper): Mapper<OxfordWord, List<@JvmSuppressWildcards Word>>
}
package ru.alexapps.personaldictionary.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alexapps.personaldictionary.data.datasources.BaseDataSource
import ru.alexapps.personaldictionary.data.datasources.BncWordDataSource
import ru.alexapps.personaldictionary.data.datasources.LocalDataSource
import ru.alexapps.personaldictionary.data.repository.BncWordRepository
import ru.alexapps.personaldictionary.data.repository.IBncWordRepository
import ru.alexapps.personaldictionary.data.repository.IWordRepository
import ru.alexapps.personaldictionary.data.repository.WordRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWordRepository(
        remoteDataSource: BaseDataSource,
        localDataSource: LocalDataSource
    ): IWordRepository {
        return WordRepository(remoteDataSource, localDataSource)
    }

    @Singleton
    @Provides
    fun provideBncWordRepository(bncWordDataSource: BncWordDataSource): IBncWordRepository {
        return BncWordRepository(bncWordDataSource)
    }
}
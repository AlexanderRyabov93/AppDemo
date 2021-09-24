package ru.alexapps.personaldictionary.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alexapps.personaldictionary.data.datasources.BaseDataSource
import ru.alexapps.personaldictionary.data.datasources.FakeDataSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideFakeDataSource(): BaseDataSource {
        return FakeDataSource()
    }

}
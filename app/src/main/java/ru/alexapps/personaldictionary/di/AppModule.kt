package ru.alexapps.personaldictionary.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.alexapps.personaldictionary.data.datasources.BncWordDataSource
import ru.alexapps.personaldictionary.data.datasources.LocalDataSource
import ru.alexapps.personaldictionary.data.datasources.RoomBncDataSource
import ru.alexapps.personaldictionary.data.datasources.RoomDbDataSource
import ru.alexapps.personaldictionary.data.db.DbConstants
import ru.alexapps.personaldictionary.data.db.WordDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDbDataSource(database: WordDatabase): LocalDataSource {
        return RoomDbDataSource(database)
    }

    @Singleton
    @Provides
    fun provideBncWordDataSource(database: WordDatabase): BncWordDataSource {
        return RoomBncDataSource(database.bncWordDao())
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): WordDatabase {
        val dbFile = context.getDatabasePath(DbConstants.DATABASE_NAME)
        val builder = Room.databaseBuilder(
            context.applicationContext,
            WordDatabase::class.java,
            DbConstants.DATABASE_NAME
        )
        if(!dbFile.exists()) {
            builder.createFromAsset("words.db")
        }
        return builder.build()
    }

}

package ru.alexapps.personaldictionary.data.repository

import android.util.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.alexapps.personaldictionary.data.datasources.BaseDataSource
import ru.alexapps.personaldictionary.data.datasources.LocalDataSource
import ru.alexapps.personaldictionary.data.models.SemanticCategory
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.utils.Result

class WordRepository(
    private val remoteDataSource: BaseDataSource,
    private val localDataSource: LocalDataSource
) : IWordRepository {
    private val limit = 10
    private val substringCache = LruCache<String, List<String>>(200)
    private val wordCache = LruCache<String, List<Word>>(200)

    override suspend fun fetchWord(wordToFind: String): List<Word> {
        val fromCache = wordCache.get(wordToFind)
        if (fromCache != null) {
            return fromCache
        }
        return withContext(Dispatchers.IO) {

            val words = mutableListOf<Word>()
            when (val localResult = localDataSource.getWords(wordToFind)) {
                is Result.Success -> words.addAll(localResult.data)
                is Result.Error -> handleLoadingException(localResult.exception)
            }
            when (val localResult = remoteDataSource.getWords(wordToFind)) {
                is Result.Success -> words.addAll(localResult.data.filter { !words.contains(it) })
                is Result.Error -> handleLoadingException(localResult.exception)
            }
            wordCache.put(wordToFind, words)
            words
        }

    }

    override suspend fun getWordsBySubstring(substring: String): List<String> {
        return withContext(Dispatchers.IO) {
            val words = mutableListOf<String>()
            when (val localResult = localDataSource.getWordsBySubstring(substring, limit)) {
                is Result.Success -> words.addAll(localResult.data)
                is Result.Error -> handleLoadingException(localResult.exception)
            }
            when (val localResult = remoteDataSource.getWordsBySubstring(substring, limit)) {
                is Result.Success -> words.addAll(localResult.data.filter { !words.contains(it) })
                is Result.Error -> handleLoadingException(localResult.exception)
            }

            substringCache.put(substring, words)
            words
        }

    }

    override suspend fun saveWord(word: Word): Long =
        withContext(Dispatchers.IO) { localDataSource.saveWord(word) }


    override suspend fun saveSemanticCategory(semanticCategory: SemanticCategory): Long =
        withContext(Dispatchers.IO) { localDataSource.saveSemanticCategory(semanticCategory) }


    override suspend fun updateWord(word: Word) {
        withContext(Dispatchers.IO) { localDataSource.updateWord(word) }
    }

    private fun handleLoadingException(exception: Exception) {
        // TODO: 21.02.2021 handle exception
        exception.printStackTrace()
    }
}
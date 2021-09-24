package ru.alexapps.personaldictionary.data.datasources

import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.data.network.RapidApiWordsService
import ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi.RapidApiWord
import ru.alexapps.personaldictionary.exceptions.ApiCallException
import ru.alexapps.personaldictionary.utils.Mapper
import ru.alexapps.personaldictionary.utils.Result

class RapidApiWordsDataSource(
    private val rapidApiService: RapidApiWordsService,
    private val mapper: Mapper<RapidApiWord, List<Word>>
) : BaseDataSource {


    override suspend fun getWords(wordToFind: String): Result<List<Word>> {
        try {
            val response = rapidApiService.getWords(wordToFind)
            if (response.isSuccessful && response.body() != null) {
                return Result.Success(mapper.map(response.body()!!))
            }
            throw ApiCallException("getWords call failed. Code = ${response.code()}")
        } catch (exception: Exception) {
            val error = if (exception is ApiCallException) exception else ApiCallException(
                "getWords call failed",
                exception
            )
            return Result.Error(error)
        }
    }

    override suspend fun getWordsBySubstring(substring: String, limit: Int): Result<List<String>> {
        TODO("Not yet implemented")
    }


}
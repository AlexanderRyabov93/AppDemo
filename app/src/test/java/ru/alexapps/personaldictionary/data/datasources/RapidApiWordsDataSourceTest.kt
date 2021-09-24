package ru.alexapps.personaldictionary.data.datasources

import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import retrofit2.Response
import retrofit2.Retrofit
import ru.alexapps.personaldictionary.data.models.LexicalCategory
import ru.alexapps.personaldictionary.data.models.Sense
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.data.network.RapidApiWordsService
import ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi.RapidApiWord
import ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi.RapidApiWordResult
import ru.alexapps.personaldictionary.exceptions.ApiCallException
import ru.alexapps.personaldictionary.testHelpers.RapidApiWordsData
import ru.alexapps.personaldictionary.utils.Mapper
import ru.alexapps.personaldictionary.utils.Result

class RapidApiWordsDataSourceTest {
    private lateinit var dataSource: RapidApiWordsDataSource
    private lateinit var mockApiService: RapidApiWordsService
    private lateinit var mockMapper: Mapper<RapidApiWord, List< Word>>
    private val rapidApiWord = RapidApiWordsData.rapidApiWord
    private val words = RapidApiWordsData.words

    @Before
    fun setup() {
        mockApiService = mock()
        mockMapper = mock()
        dataSource = spy(RapidApiWordsDataSource(mockApiService, mockMapper))
    }



    @Test
    fun `getWords should return error cause response code is 500`() {
        runBlocking {
            `when`(mockApiService.getWords(any())).thenReturn(
                Response.error(
                    500, ResponseBody.create(
                        MediaType.parse("application/json"), "test"
                    )
                )
            )
            val result = dataSource.getWords("test")
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is ApiCallException)
        }
    }

    @Test
    fun `getWords should return error cause response failed with exception`() {
        val word = "test"
        val expectException = RuntimeException("Test exception")
        runBlocking {
            `when`(mockApiService.getWords(any())).thenThrow(expectException)
            val result = dataSource.getWords(word)
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is ApiCallException)
            assertEquals(expectException, result.exception.cause)
        }
    }

    @Test
    fun `getWords should return correct value`() {
        val word = "test"
        runBlocking {
            `when`(mockApiService.getWords(any())).thenReturn(Response.success(rapidApiWord))
            `when`(mockMapper.map(any())).thenReturn(words.toList())
            val result = dataSource.getWords(word)
            verify(mockApiService, times(1)).getWords(word)
            verify(mockMapper, times(1)).map(rapidApiWord)
            assertTrue(result is Result.Success)
            assertArrayEquals(
                words,
                (result as Result.Success).data.toTypedArray()
            )
        }
    }

}
package ru.alexapps.personaldictionary.data.datasorces

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Mockito.`when`
import retrofit2.Response
import ru.alexapps.personaldictionary.data.datasources.OxfordDictionaryDataSource
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.data.network.OxfordApiService
import ru.alexapps.personaldictionary.data.network.retrofitmodels.OxfordSearchWord
import ru.alexapps.personaldictionary.data.network.retrofitmodels.OxfordSearchWordResult
import ru.alexapps.personaldictionary.data.network.retrofitmodels.OxfordWord
import ru.alexapps.personaldictionary.exceptions.ApiCallException
import ru.alexapps.personaldictionary.testHelpers.OxfordWordData
import ru.alexapps.personaldictionary.utils.Mapper
import ru.alexapps.personaldictionary.utils.Result

class OxfordDictionaryDataSourceTest {

    private lateinit var dataSource: OxfordDictionaryDataSource
    private lateinit var mockApiService: OxfordApiService
    private lateinit var mockMapper: Mapper<OxfordWord, List<Word>>
    private val oxfordWord: OxfordWord = OxfordWordData.oxfordWord
    private val words: Array<Word> = OxfordWordData.words

    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        mockApiService = mock()
        mockMapper = mock()
        dataSource = OxfordDictionaryDataSource(mockApiService, mockMapper)

    }
    @Test
    fun `getWords should throw exception cause response code 500`() {
        val word = "test"
        runBlocking {
            `when`(mockApiService.getWords(any(), any())).thenReturn(
                Response.error(
                    500, ResponseBody.create(
                        MediaType.parse("application/json"), "test"
                    )
                )
            )
            val result = dataSource.getWords(word)
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is ApiCallException)
        }
    }

    @Test
    fun `getWords should throw exception cause response failed with exception`() {
        val word = "test"
        val expectException = RuntimeException("Test exception")
        runBlocking {
            `when`(mockApiService.getWords(any(), any())).thenThrow(expectException)
            val result = dataSource.getWords(word)
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is ApiCallException)
            assertEquals(expectException, result.exception.cause )
        }
    }

    @Test
    fun `getWords should return correct value`() {
        val word = "test"
        runBlocking {
            `when`(mockApiService.getWords(any(), any())).thenReturn(Response.success(oxfordWord))
            `when`(mockMapper.map(oxfordWord)).thenReturn(words.toList())
            val result = dataSource.getWords(word)
            verify(mockApiService, times(1)).getWords(word)
            verify(mockMapper, times(1)).map(oxfordWord)
            assertTrue(result is Result.Success)
            assertArrayEquals(
                words,
                (result as Result.Success).data.toTypedArray()
            )
        }

    }

    @Test
    fun `getWordsBySubstring should throw exception cause response code 500`() {
        val word = "test"
        runBlocking {
            `when`(mockApiService.searchWords(any(), any(), any())).thenReturn(
                Response.error(
                    500, ResponseBody.create(
                        MediaType.parse("application/json"), "test"
                    )
                )
            )
            val result = dataSource.getWordsBySubstring(word, 30)
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is ApiCallException)
        }
    }

    @Test
    fun `getWordsBySubstring should throw exception cause response failed with exception`() {
        val word = "test"
        val expectException = RuntimeException("Test exception")
        runBlocking {
            `when`(mockApiService.searchWords(any(), any(), any())).thenThrow(expectException)
            val result = dataSource.getWordsBySubstring(word, 30)
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is ApiCallException)
            assertEquals(expectException, result.exception.cause )
        }
    }

    @Test
    fun `getWordsBySubstring should return correct value`() {
        val word = "test"
        val limit = 20
        val searchWordResults = listOf(
            OxfordSearchWordResult("1", word, "type", "${word}1"),
            OxfordSearchWordResult("2", word, "type", "${word}2"),
            OxfordSearchWordResult("3", word, "type", "${word}3")
        )
        runBlocking {
            `when`(mockApiService.searchWords(any(), any(), any()))
                .thenReturn(
                    Response.success(
                        OxfordSearchWord(
                            searchWordResults
                        )
                    )
                )
            val result = dataSource.getWordsBySubstring(word, limit)
            verify(mockApiService, times(1)).searchWords(word, limit)
            assertTrue(result is Result.Success)
            assertArrayEquals(
                searchWordResults.map { it.word }.toTypedArray(),
                (result as Result.Success).data.toTypedArray()
            )
        }
    }
}
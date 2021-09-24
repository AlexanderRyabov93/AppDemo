package ru.alexapps.personaldictionary.data.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alexapps.personaldictionary.BuildConfig
import ru.alexapps.personaldictionary.data.network.OxfordApiService
import ru.alexapps.personaldictionary.data.network.retrofitmodels.OxfordSearchWordResult

@RunWith(AndroidJUnit4::class)
class OxfordApiServiceTest {
    private lateinit var service: OxfordApiService

    @Before
    fun init() {
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        service = retrofit.create(OxfordApiService::class.java)
    }
    @Test
    fun searchWords_test() = runBlocking {
        val result = service.searchWords("applic")
        Assert.assertEquals(200, result.code())
        assertArrayEquals(arrayOf(
            OxfordSearchWordResult("applicable", "applic", "headword", "applicable"),
            OxfordSearchWordResult("applicant", "applic", "headword", "applicant"),
            OxfordSearchWordResult("application", "applic", "headword", "application")
        ), result.body()!!.results.toTypedArray())
    }
    @Test
    fun getWords_test() = runBlocking {
        val result = service.getWords("programme")
        assertEquals(1, 1)
    }
}
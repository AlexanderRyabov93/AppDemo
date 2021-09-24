package ru.alexapps.personaldictionary.data.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alexapps.personaldictionary.BuildConfig

@RunWith(AndroidJUnit4::class)
class RapidApiWordsServiceTest {
    private lateinit var service: RapidApiWordsService

    @Before
    fun init() {
        val baseUrl = "https://${BuildConfig.RAPID_API_HOST}/"
        val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
        service = retrofit.create(RapidApiWordsService::class.java)
    }

    @Test
    fun getWords() {
        runBlocking {
            val response = service.getWords("house")
            assertEquals(200, response.code())
        }
    }
}
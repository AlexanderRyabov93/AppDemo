package ru.alexapps.personaldictionary.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.alexapps.personaldictionary.BuildConfig
import ru.alexapps.personaldictionary.data.network.retrofitmodels.OxfordSearchWord
import ru.alexapps.personaldictionary.data.network.retrofitmodels.OxfordWord

interface OxfordApiService {
    @Headers(
        "Accept: application/json",
        "app_id: ${BuildConfig.API_APP_ID}",
        "app_key: ${BuildConfig.API_KEY}"
    )
    @GET("search/thesaurus/en")
    suspend fun searchWords(
        @Query("q") substring: String,
        @Query("limit") limit: Int = 10,
        @Query("prefix") prefix: Boolean = true
    ): Response<OxfordSearchWord>

    @Headers(
        "Accept: application/json",
        "app_id: ${BuildConfig.API_APP_ID}",
        "app_key: ${BuildConfig.API_KEY}"
    )
    @GET("words/en-gb")
    suspend fun getWords(
        @Query("q") word: String,
        @Query("fields") fields: String = "definitions domains examples"
    ): Response<OxfordWord>
}
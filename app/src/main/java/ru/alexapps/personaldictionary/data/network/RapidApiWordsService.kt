package ru.alexapps.personaldictionary.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import ru.alexapps.personaldictionary.BuildConfig
import ru.alexapps.personaldictionary.data.network.retrofitmodels.wordsapi.RapidApiWord

interface RapidApiWordsService {
    @Headers(
        "x-rapidapi-key: ${BuildConfig.RAPID_API_KEY}",
        "x-rapidapi-host: ${BuildConfig.RAPID_API_HOST}"
    )
    @GET("words/{word}")
    suspend fun getWords(
        @Path(value = "word", encoded = true) word: String
    ): Response<RapidApiWord>
}
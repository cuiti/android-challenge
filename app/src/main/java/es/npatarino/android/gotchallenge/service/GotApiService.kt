package es.npatarino.android.gotchallenge.service

import es.npatarino.android.gotchallenge.model.Character
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GotApiService {

    @GET("characters.json")
    fun getCharacters(@Query("print") print: String? = "pretty"): Single<List<Character>>
}
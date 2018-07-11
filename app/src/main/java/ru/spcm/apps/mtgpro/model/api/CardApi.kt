package ru.spcm.apps.mtgpro.model.api

import android.arch.lifecycle.LiveData

import retrofit2.http.GET
import retrofit2.http.Query
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.tools.ApiResponse

/**
 *
 * Created by gen on 28.06.2018.
 */

interface CardApi {

    @GET("/v1/cards")
    fun getCardsByNumber(@Query("set") set: String, @Query("number") number: String): LiveData<ApiResponse<List<Card>>>

    @GET("/v1/cards")
    fun getCardsByName(@Query("set") set: String, @Query("name") name: String): LiveData<ApiResponse<List<Card>>>

    @GET("/v1/cards")
    fun getCardsByLanguage(@Query("name") name: String, @Query("language") language: String?): LiveData<ApiResponse<List<Card>>>

    @GET("/v1/cards")
    fun getCardsBySet(@Query("set") set: String, @Query("page") page: Int, @Query("pageSize") pages: Int): LiveData<ApiResponse<List<Card>>>

    @GET("/v1/cards")
    fun getCardsByMid(@Query("multiverseid") set: String): LiveData<ApiResponse<List<Card>>>

}
package ru.spcm.apps.mtgpro.model.api

import android.arch.lifecycle.LiveData

import retrofit2.http.GET
import ru.spcm.apps.mtgpro.model.tools.ApiResponse
import ru.spcm.apps.mtgpro.model.dto.Set

/**
 *
 * Created by gen on 28.06.2018.
 */

interface SetsApi {

    @GET("/v1/sets")
    fun getSets(): LiveData<ApiResponse<List<Set>>>

}
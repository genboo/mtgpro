package ru.spcm.apps.mtgpro.model.api

import android.arch.lifecycle.LiveData

import retrofit2.http.GET
import retrofit2.http.Path
import ru.spcm.apps.mtgpro.model.dto.ScryCard
import ru.spcm.apps.mtgpro.model.tools.ApiResponse

/**
 *
 * Created by gen on 28.06.2018.
 */

interface ScryCardApi {

    @GET("/cards/{set}/{number}")
    fun getCardByNumber(@Path("set") set: String, @Path("number") number: String): LiveData<ApiResponse<ScryCard>>

}
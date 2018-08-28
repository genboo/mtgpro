package ru.spcm.apps.mtgpro.model.api

import retrofit2.Call
import retrofit2.http.GET
import ru.spcm.apps.mtgpro.model.dto.ValuteList

/**
 *
 * Created by gen on 28.08.2018.
 */

interface ValuteApi {

    @GET("/daily_json.js")
    fun getValutes(): Call<ValuteList>
}
package ru.spcm.apps.mtgpro.di.modules

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.spcm.apps.mtgpro.BuildConfig
import ru.spcm.apps.mtgpro.model.tools.LiveDataCallAdapterFactory
import ru.spcm.apps.mtgpro.tools.Logger
import java.util.concurrent.TimeUnit

open class RetrofitBase {

    fun getRetrofit(api: String, builder: GsonBuilder): Retrofit {
        val httpClient = OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        setRetryOptions(httpClient)
        setDebugOptions(httpClient)

        return Retrofit.Builder()
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(httpClient.build())
                .build()
    }

    private fun setRetryOptions(httpClient: OkHttpClient.Builder) {
        httpClient.addInterceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryCount = 0
            while (!response.isSuccessful && tryCount < TRY_LIMIT) {
                Logger.e("Retry request")
                tryCount++
                response = chain.proceed(request)
            }

            return@addInterceptor response
        }
    }


    /**
     * Устанавливает опции для отладки
     * @param httpClient Клиент
     */
    private fun setDebugOptions(httpClient: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG_MODE) {
            //Логгер в режиме отладки для всех запросов
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
    }

    companion object {
        const val READ_TIMEOUT: Long = 15
        const val CONNECT_TIMEOUT: Long = 15
        const val TRY_LIMIT: Int = 3
        const val MTG_API_URL = "https://api.magicthegathering.io"
        const val SCRY_API_URL = "https://api.scryfall.com"
        const val CBR_API_URL = "https://www.cbr-xml-daily.ru"
    }
}
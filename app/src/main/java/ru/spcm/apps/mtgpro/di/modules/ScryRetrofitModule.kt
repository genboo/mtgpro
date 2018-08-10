package ru.spcm.apps.mtgpro.di.modules


import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.spcm.apps.mtgpro.BuildConfig
import ru.spcm.apps.mtgpro.model.api.ScryCardApi
import ru.spcm.apps.mtgpro.model.tools.LiveDataCallAdapterFactory
import javax.inject.Named

/**
 * Инициализация Retrofit
 * Created by gen on 28.06.2018.
 */

@Module
class ScryRetrofitModule {

    /**
     * Базовый провайдер Retrofit
     * @return Retrofit
     */
    @Singleton
    @Provides
    @Named("scry")
    internal fun provideRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)

        setDebugOptions(httpClient)

        val builder = GsonBuilder()

        return Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(httpClient.build())
                .build()
    }

    /**
     * API получения карт
     * @param retrofit Retrofit
     * @return API
     */
    @Singleton
    @Provides
    internal fun provideCardApi(@Named("scry") retrofit: Retrofit): ScryCardApi {
        return retrofit.create(ScryCardApi::class.java)
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
        private const val READ_TIMEOUT: Long = 15
        private const val CONNECT_TIMEOUT: Long = 15
        private const val API_URL = "https://api.scryfall.com"
    }
}

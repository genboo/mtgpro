package ru.spcm.apps.mtgpro.di.modules


import com.google.gson.GsonBuilder
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.spcm.apps.mtgpro.di.NullStringTypeAdapter
import ru.spcm.apps.mtgpro.model.api.CardApi
import ru.spcm.apps.mtgpro.model.api.SetsApi
import ru.spcm.apps.mtgpro.model.tools.ResultTypeAdapterFactory

/**
 * Инициализация Retrofit
 * Created by gen on 28.06.2018.
 */
@Suppress("unused")
@Module
class RetrofitModule : RetrofitBase() {

    /**
     * Базовый провайдер Retrofit
     * @return Retrofit
     */
    @Singleton
    @Provides
    internal fun provideRetrofit(): Retrofit {
        val builder = GsonBuilder()
                .registerTypeAdapterFactory(ResultTypeAdapterFactory())
                .registerTypeAdapter(String::class.java, NullStringTypeAdapter())
        return getRetrofit(MTG_API_URL, builder)
    }

    /**
     * API получения карт
     * @param retrofit Retrofit
     * @return API
     */
    @Singleton
    @Provides
    internal fun provideCardApi(retrofit: Retrofit): CardApi {
        return retrofit.create(CardApi::class.java)
    }

    /**
     * API получения сетов
     * @param retrofit Retrofit
     * @return API
     */
    @Singleton
    @Provides
    internal fun provideSetsApi(retrofit: Retrofit): SetsApi {
        return retrofit.create(SetsApi::class.java)
    }

}

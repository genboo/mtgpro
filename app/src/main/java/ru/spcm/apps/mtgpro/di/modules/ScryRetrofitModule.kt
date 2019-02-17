package ru.spcm.apps.mtgpro.di.modules


import com.google.gson.GsonBuilder
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.spcm.apps.mtgpro.di.NullStringTypeAdapter
import ru.spcm.apps.mtgpro.model.api.ScryCardApi
import javax.inject.Named

/**
 * Инициализация Retrofit
 * Created by gen on 28.06.2018.
 */
@Suppress("unused")
@Module
class ScryRetrofitModule : RetrofitBase() {

    /**
     * Базовый провайдер Retrofit
     * @return Retrofit
     */
    @Singleton
    @Provides
    @Named("scry")
    internal fun provideRetrofit(): Retrofit {
        val builder = GsonBuilder()
                .registerTypeAdapter(String::class.java, NullStringTypeAdapter())
        return getRetrofit(SCRY_API_URL, builder)
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

}

package ru.spcm.apps.mtgpro.di.modules


import com.google.gson.GsonBuilder
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.spcm.apps.mtgpro.model.api.ValuteApi
import ru.spcm.apps.mtgpro.model.tools.ValuteTypeAdapterFactory
import javax.inject.Named

/**
 * Инициализация Retrofit
 * Created by gen on 28.08.2018.
 */
@Suppress("unused")
@Module
class CbrRetrofitModule : RetrofitBase() {

    /**
     * Базовый провайдер Retrofit
     * @return Retrofit
     */
    @Singleton
    @Provides
    @Named("cbr")
    internal fun provideRetrofit(): Retrofit {
        val builder = GsonBuilder()
        builder.registerTypeAdapterFactory(ValuteTypeAdapterFactory())
        return getRetrofit(CBR_API_URL, builder)
    }

    /**
     * API получения карт
     * @param retrofit Retrofit
     * @return API
     */
    @Singleton
    @Provides
    internal fun provideValuteApi(@Named("cbr") retrofit: Retrofit): ValuteApi {
        return retrofit.create(ValuteApi::class.java)
    }

}

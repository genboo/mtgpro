package ru.spcm.apps.mtgpro.di.components


import android.content.Context

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import ru.spcm.apps.mtgpro.di.ViewModelModule
import ru.spcm.apps.mtgpro.di.modules.*
import ru.spcm.apps.mtgpro.view.activities.MainActivity
import ru.spcm.apps.mtgpro.services.AlarmReceiver
import ru.spcm.apps.mtgpro.view.fragments.*

/**
 * Компонент di
 * Created by gen on 28.06.2018.
 */
@Component(modules = [
    ViewModelModule::class,
    RetrofitModule::class,
    ScryRetrofitModule::class,
    CbrRetrofitModule::class,
    DbModule::class,
    NavigationModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: BaseFragment)

    fun inject(service: AlarmReceiver)

}
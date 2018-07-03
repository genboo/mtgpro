package ru.spcm.apps.mtgpro.di.components


import android.content.Context

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import ru.spcm.apps.mtgpro.di.ViewModelModule
import ru.spcm.apps.mtgpro.view.activities.MainActivity
import ru.spcm.apps.mtgpro.di.modules.DbModule
import ru.spcm.apps.mtgpro.di.modules.NavigationModule
import ru.spcm.apps.mtgpro.di.modules.RetrofitModule
import ru.spcm.apps.mtgpro.view.fragments.CardFragment
import ru.spcm.apps.mtgpro.view.fragments.CollectionFragment
import ru.spcm.apps.mtgpro.view.fragments.SetsFragment
import ru.spcm.apps.mtgpro.view.fragments.SpoilersFragment

/**
 * Компонент di
 * Created by gen on 28.06.2018.
 */
@Component(modules = [ViewModelModule::class, RetrofitModule::class, DbModule::class, NavigationModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: SetsFragment)

    fun inject(fragment: SpoilersFragment)

    fun inject(fragment: CardFragment)

    fun inject(fragment: CollectionFragment)

}
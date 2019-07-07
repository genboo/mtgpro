package ru.spcm.apps.mtgpro.di.modules

import dagger.Module
import dagger.Provides
import ru.spcm.apps.mtgpro.navigation.Cicerone
import ru.spcm.apps.mtgpro.navigation.NavigatorHolder
import ru.spcm.apps.mtgpro.navigation.Router
import javax.inject.Singleton

@Module
class NavigationModule(private var cicerone: Cicerone<Router> = Cicerone.create()) {

    @Provides
    @Singleton
    fun provideRouter(): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }

}
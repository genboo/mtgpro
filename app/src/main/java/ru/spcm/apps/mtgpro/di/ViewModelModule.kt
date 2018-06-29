package ru.spcm.apps.mtgpro.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.spcm.apps.mtgpro.viewmodel.CardViewModel
import ru.spcm.apps.mtgpro.viewmodel.SetsViewModel
import ru.spcm.apps.mtgpro.viewmodel.SpoilersViewModel

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SetsViewModel::class)
    internal abstract fun bindSetsViewModel(viewModel: SetsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CardViewModel::class)
    internal abstract fun bindCardViewModel(viewModel: CardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SpoilersViewModel::class)
    internal abstract fun bindSpoilersViewModel(viewModel: SpoilersViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
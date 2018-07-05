package ru.spcm.apps.mtgpro.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.spcm.apps.mtgpro.viewmodel.*

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
    @IntoMap
    @ViewModelKey(CollectionViewModel::class)
    internal abstract fun bindCollectionViewModel(viewModel: CollectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WishViewModel::class)
    internal abstract fun bindWishViewModel(viewModel: WishViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LibrariesViewModel::class)
    internal abstract fun bindLibrariesViewModel(viewModel: LibrariesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LibraryViewModel::class)
    internal abstract fun bindLibraryViewModel(viewModel: LibraryViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
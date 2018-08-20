package ru.spcm.apps.mtgpro.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.spcm.apps.mtgpro.viewmodel.*

@Suppress("unused")
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
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PriceVolatilityViewModel::class)
    internal abstract fun bindPriceVolatilityViewModel(viewModel: PriceVolatilityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReportViewModel::class)
    internal abstract fun bindReportViewModel(viewModel: ReportViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WatchViewModel::class)
    internal abstract fun bindWatchViewModel(viewModel: WatchViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
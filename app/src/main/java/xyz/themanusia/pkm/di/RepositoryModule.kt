package xyz.themanusia.pkm.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.themanusia.pkm.ui.homepage.repository.HomepageDbRepository
import xyz.themanusia.pkm.ui.homepage.repository.HomepageRepository
import xyz.themanusia.pkm.ui.main.repository.MainDbRepository
import xyz.themanusia.pkm.ui.main.repository.MainRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesHomepageRepository(
        repository: HomepageDbRepository
    ): HomepageRepository

    @Binds
    abstract fun providesMainRepository(
        repository: MainDbRepository
    ): MainRepository

}
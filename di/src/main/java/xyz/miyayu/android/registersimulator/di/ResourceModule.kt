package xyz.miyayu.android.registersimulator.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.miyayu.android.registersimulator.utils.ResourceService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ResourceModule {
    @Singleton
    @Binds
    abstract fun bindResourceService(
        resourceServiceImpl: ResourceService.Companion.ResourceServiceImpl
    ): ResourceService
}

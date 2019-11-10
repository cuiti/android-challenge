package es.npatarino.android.gotchallenge.dagger

import dagger.Module
import dagger.Provides
import es.npatarino.android.gotchallenge.home.HomePresenter
import es.npatarino.android.gotchallenge.service.GotApiService
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun providesHomePresenter(gotApiService: GotApiService): HomePresenter = HomePresenter(gotApiService)
}
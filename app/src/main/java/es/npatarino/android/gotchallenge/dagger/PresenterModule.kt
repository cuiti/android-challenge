package es.npatarino.android.gotchallenge.dagger

import dagger.Module
import dagger.Provides
import es.npatarino.android.gotchallenge.home.HomePresenter
import es.npatarino.android.gotchallenge.housemembers.HouseMembersPresenter
import es.npatarino.android.gotchallenge.service.Repository
import javax.inject.Singleton

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun providesHomePresenter(repository: Repository): HomePresenter = HomePresenter(repository)

    @Provides
    @Singleton
    fun providesHouseListPresenter(repository: Repository) = HouseMembersPresenter(repository)
}
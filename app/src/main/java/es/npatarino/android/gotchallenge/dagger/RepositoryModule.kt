package es.npatarino.android.gotchallenge.dagger

import dagger.Module
import dagger.Provides
import es.npatarino.android.gotchallenge.service.GoTDatabase
import es.npatarino.android.gotchallenge.service.GotApiService
import es.npatarino.android.gotchallenge.service.Repository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesRepository(gotApiService: GotApiService, database: GoTDatabase): Repository =
            Repository(gotApiService, database)
}
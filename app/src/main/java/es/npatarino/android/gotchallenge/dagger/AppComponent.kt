package es.npatarino.android.gotchallenge.dagger

import dagger.Component
import es.npatarino.android.gotchallenge.home.GoTCharacterListFragment
import es.npatarino.android.gotchallenge.home.GoTHousesListFragment
import es.npatarino.android.gotchallenge.home.HomeActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PresenterModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(target: HomeActivity)
    fun inject(target: GoTCharacterListFragment)
    fun inject(target: GoTHousesListFragment)
}
package es.npatarino.android.gotchallenge.dagger

import dagger.Component
import es.npatarino.android.gotchallenge.home.characters.GoTCharacterListFragment
import es.npatarino.android.gotchallenge.home.houses.GoTHousesListFragment
import es.npatarino.android.gotchallenge.home.HomeActivity
import es.npatarino.android.gotchallenge.housemembers.HouseMembersActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    PresenterModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    DatabaseModule::class])
interface AppComponent {
    fun inject(target: HomeActivity)
    fun inject(target: HouseMembersActivity)
    fun inject(target: GoTCharacterListFragment)
    fun inject(target: GoTHousesListFragment)
}
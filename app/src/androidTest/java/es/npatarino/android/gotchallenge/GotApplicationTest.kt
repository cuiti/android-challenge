package es.npatarino.android.gotchallenge

import es.npatarino.android.gotchallenge.dagger.*
import es.npatarino.android.gotchallenge.model.Character
import es.npatarino.android.gotchallenge.model.MockData
import es.npatarino.android.gotchallenge.service.GotApiService
import io.reactivex.Single
import retrofit2.Retrofit

class GotApplicationTest : GoTApplication(){

    override val gotComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(MockNetworkModule())
                .build()
    }

    private class MockNetworkModule : NetworkModule() {
        override fun provideGotApi(retrofit: Retrofit): GotApiService =
                object : GotApiService {
                    override fun getCharacters(print: String?): Single<List<Character>> {
                        return Single.just(MockData.characters)
                    }
                }
    }
}
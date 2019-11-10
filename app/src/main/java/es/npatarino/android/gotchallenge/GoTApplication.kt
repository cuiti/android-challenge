package es.npatarino.android.gotchallenge

import android.app.Application
import es.npatarino.android.gotchallenge.dagger.AppComponent
import es.npatarino.android.gotchallenge.dagger.AppModule
import es.npatarino.android.gotchallenge.dagger.DaggerAppComponent

class GoTApplication : Application() {

    lateinit var gotComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        gotComponent = initDagger(this)
    }

    private fun initDagger(app: GoTApplication): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()
}
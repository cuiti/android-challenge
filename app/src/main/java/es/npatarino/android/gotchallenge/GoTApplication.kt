package es.npatarino.android.gotchallenge

import android.app.Application
import es.npatarino.android.gotchallenge.dagger.AppComponent
import es.npatarino.android.gotchallenge.dagger.AppModule
import es.npatarino.android.gotchallenge.dagger.DaggerAppComponent

class GoTApplication : Application() {

    val gotComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

}
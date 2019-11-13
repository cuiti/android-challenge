package es.npatarino.android.gotchallenge

import android.app.Application
import es.npatarino.android.gotchallenge.dagger.AppComponent
import es.npatarino.android.gotchallenge.dagger.AppModule
import es.npatarino.android.gotchallenge.dagger.DaggerAppComponent

open class GoTApplication : Application() {

    open val gotComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

}
package es.npatarino.android.gotchallenge.dagger

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import es.npatarino.android.gotchallenge.service.GoTDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context) =
            Room.databaseBuilder(context, GoTDatabase::class.java, "got_database")
            .build()
}
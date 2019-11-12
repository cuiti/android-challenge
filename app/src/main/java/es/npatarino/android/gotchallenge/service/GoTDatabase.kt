package es.npatarino.android.gotchallenge.service

import androidx.room.Database
import androidx.room.RoomDatabase
import es.npatarino.android.gotchallenge.model.Character

@Database(entities = [Character::class], version = 1)
abstract class GoTDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
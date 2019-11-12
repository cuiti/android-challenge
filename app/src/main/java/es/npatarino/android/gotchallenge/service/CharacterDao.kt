package es.npatarino.android.gotchallenge.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.npatarino.android.gotchallenge.model.Character
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    fun getAll(): Single<List<Character>>

    @Insert
    fun insertAll(characters: List<Character>): Completable

}
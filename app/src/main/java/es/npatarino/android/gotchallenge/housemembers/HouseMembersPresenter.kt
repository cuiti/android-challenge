package es.npatarino.android.gotchallenge.housemembers

import es.npatarino.android.gotchallenge.model.Character
import es.npatarino.android.gotchallenge.model.House
import es.npatarino.android.gotchallenge.service.Repository
import io.reactivex.Observable
import io.reactivex.Single

class HouseMembersPresenter(val repository: Repository) {

    fun getCharactersByHouse(house: House): Single<List<Character>> =
            repository.getCharacters()
                    .flatMapObservable { Observable.fromIterable(it) }
                    .filter { character -> character.belongsTo(house) }
                    .toList()
}
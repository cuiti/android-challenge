package es.npatarino.android.gotchallenge.home

import es.npatarino.android.gotchallenge.model.Character
import es.npatarino.android.gotchallenge.model.House
import es.npatarino.android.gotchallenge.service.GotApiService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class HomePresenter(val gotApiService: GotApiService) {

    fun getCharacters(): Single<List<Character>>{
        return gotApiService.getCharacters()
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getHouses(): Single<List<House>> =
            getCharacters()
                    .flatMapObservable { Observable.fromIterable(it) }
                    .map { it.house }
                    .filter { it.name.isNotEmpty() }
                    .distinct()
                    .toList()
}
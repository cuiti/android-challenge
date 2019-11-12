package es.npatarino.android.gotchallenge.service

import es.npatarino.android.gotchallenge.model.Character
import es.npatarino.android.gotchallenge.model.House
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Repository(val apiService: GotApiService) {

    fun getCharacters(): Single<List<Character>> {
        return apiService.getCharacters()
                .subscribeOn(Schedulers.io())
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
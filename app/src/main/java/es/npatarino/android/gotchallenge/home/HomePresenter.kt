package es.npatarino.android.gotchallenge.home

import es.npatarino.android.gotchallenge.model.Character
import es.npatarino.android.gotchallenge.service.GotApiService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class HomePresenter(val gotApiService: GotApiService) {

    fun getCharacters(): Single<List<Character>>{
        return gotApiService.getCharacters()
                .flatMapObservable { c -> Observable.fromIterable(c) }
                .filter { it.name.toLowerCase().contains("t") }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
    }
}
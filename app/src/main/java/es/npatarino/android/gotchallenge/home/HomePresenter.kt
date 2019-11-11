package es.npatarino.android.gotchallenge.home

import android.util.Log
import es.npatarino.android.gotchallenge.model.Character
import es.npatarino.android.gotchallenge.model.House
import es.npatarino.android.gotchallenge.service.GotApiService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class HomePresenter(val gotApiService: GotApiService) {

    companion object {
        private const val SEARCH_DELAY = 700L
    }

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

    fun observeSearch(source: SearchView): Observable<List<Character>>{
        return source
                .getSearchObservable()
                .skip(1)
                .debounce(SEARCH_DELAY, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .flatMap{ getCharacters(it).toObservable() }
                .subscribeOn(Schedulers.io())
    }

    fun getCharacters(query: String) =
            if (query.isEmpty())
                getCharacters()
            else
                getCharacters()
                .flatMapObservable { Observable.fromIterable(it) }
                .filter { it.name.toLowerCase().contains(query.toLowerCase()) }
                .toList()
}
package es.npatarino.android.gotchallenge.home

import es.npatarino.android.gotchallenge.model.Character
import es.npatarino.android.gotchallenge.model.House
import es.npatarino.android.gotchallenge.service.Repository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class HomePresenter(val repository: Repository) {

    companion object {
        private const val SEARCH_DELAY = 700L
    }

    fun getCharacters(): Single<List<Character>> = repository.getCharacters()

    fun getHouses(): Single<List<House>> = repository.getHouses()

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
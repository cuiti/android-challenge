package es.npatarino.android.gotchallenge.home

import io.reactivex.Observable

interface SearchView {
    fun getSearchObservable(): Observable<CharSequence>
}
package es.npatarino.android.gotchallenge

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import es.npatarino.android.gotchallenge.home.HomePresenter
import es.npatarino.android.gotchallenge.home.SearchView
import es.npatarino.android.gotchallenge.model.MockData
import es.npatarino.android.gotchallenge.service.Repository
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTest {

    private val testScheduler = TestScheduler()
    lateinit var presenter: HomePresenter
    lateinit var repository: Repository
    lateinit var searchView: SearchView

    @Before
    fun onSetup() {
        searchView = mock()
        repository = mock()
        presenter = HomePresenter(repository)
        whenever(repository.getCharacters())
                .then { Single.just(MockData.characters) }
        whenever(repository.getHouses())
                .then { Single.just(MockData.houses) }
    }

    @Test
    fun getCharacters_returnsAllCharacters(){
        presenter.getCharacters()
                .test()
                .assertValue(MockData.characters)
                .dispose()
    }

    @Test
    fun search_yieldsMultipleResults(){
        presenter.getCharacters(MockData.multipleResultSearchInput)
                .test()
                .assertValue(MockData.multipleResultSearchResult)
                .dispose()
    }

    @Test
    fun search_yieldsSingleResult(){
        presenter.getCharacters(MockData.singleResultSearchInput)
                .test()
                .assertValue(MockData.singleResultSearchResult)
                .dispose()
    }

    @Test
    fun search_yieldsZeroResults(){
        presenter.getCharacters(MockData.zeroResultSearchInput)
                .test()
                .assertValue(listOf())
                .assertComplete()
                .assertNoErrors()
                .dispose()
    }

    @Test
    fun getHouses_returnsHouses(){
        presenter.getHouses()
                .test()
                .assertValue(MockData.houses)
                .assertSubscribed()
                .assertComplete()
    }

    /* @Test
    fun testPerformSearch(){
        val subject = ReplaySubject.create<CharSequence>()
        whenever(searchView.getSearchObservable())
                .then{subject}

        subject.onNext("a")
        subject.onNext("ab")
        testScheduler.triggerActions()
        val testObserver = presenter.observeSearch(searchView).test()
        testScheduler.triggerActions()
        testScheduler.advanceTimeBy(1000L, TimeUnit.MILLISECONDS)
        testScheduler.triggerActions()


        testObserver.assertValue(listOf(characterB))
    }*/
}